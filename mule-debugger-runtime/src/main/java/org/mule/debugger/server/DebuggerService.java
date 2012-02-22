package org.mule.debugger.server;

import com.sun.org.apache.regexp.internal.RE;
import org.mule.api.MuleMessage;
import org.mule.debugger.MuleDebuggingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DebuggerService {


    private volatile boolean keepOnRunning = false;
    //Todo Replace this with a countdown latch
    private final Object messageProcessed = new Object();
    private final Object messageResume = new Object();
    private final Lock singleMessage = new ReentrantLock();


    private DebuggerServerFactory debuggerFactory;
    private DebuggerCommunicationService connectionService;
    private MuleMessageDebuggerRequestHandler requestHandler;
    private List<IDebuggerServiceListener> listeners;

    private MuleDebuggingContext currentMessage = null;

    public DebuggerService() {
        listeners = new ArrayList<IDebuggerServiceListener>();
    }

    public DebuggerServerFactory getDebuggerFactory() {
        return debuggerFactory;
    }

    public void setDebuggerFactory(DebuggerServerFactory debuggerFactory) {
        this.debuggerFactory = debuggerFactory;
    }


    public void onBreakPoint(final MuleDebuggingContext message) {
        if (isRunning()) {
            MuleMessage newMuleMessage = message.getMessage();
            synchronized (messageResume) {
                boolean isCurrentMessage = isDebuggingThisMessage(newMuleMessage);
                if (!isCurrentMessage) {
                    while (currentMessage != null) {
                        try {
                            messageResume.wait();
                        } catch (InterruptedException e) {
                            onResume();
                        }
                    }
                    this.currentMessage = message;
                }
            }

        }
    }

    public void onProcessMessage(MuleDebuggingContext message) {
        if (isRunning() && isDebuggingThisMessage(message.getMessage())) {
            try {
                //only one message can be processed at the same time
                singleMessage.lock();
                connectionService.sendResponse(debuggerFactory.createNewMessageResponse(message));
                requestHandler.setMuleDebuggingContext(message);
                synchronized (messageProcessed) {
                    try {
                        messageProcessed.wait();
                    } catch (InterruptedException e) {
                        //resume this message
                        onResume();
                    }
                }
            } finally {
                singleMessage.unlock();
            }
        }
    }

    public boolean isDebuggingThisMessage(MuleMessage newMuleMessage) {

        return this.currentMessage != null && this.currentMessage.getMessage().getMessageRootId().equals(newMuleMessage.getMessageRootId());
    }

    public void addListener(IDebuggerServiceListener listener){
        this.listeners.add(listener);
    }


    public void startDebugging() {
        try {
            for (IDebuggerServiceListener listener : listeners) {
                listener.onStart();
            }
            connectionService = debuggerFactory.createDebuggerConnectionService();
            requestHandler = debuggerFactory.createMuleMessageRequestHandler(this);
            connectionService.setRequestHandler(requestHandler);
            if (isRunning()) {
                connectionService.start();
            }

        } finally {
            if (isRunning()) {
                stopDebugging();
            }
        }

    }


    public boolean isRunning() {
        return keepOnRunning;
    }

    public synchronized boolean lockForStart() {
        if (!isRunning()) {
            setRunning();
            return true;
        } else {
            return false;
        }

    }

    public void stopDebugging() {
        for (IDebuggerServiceListener listener : listeners) {
            listener.onStop();
        }
        keepOnRunning = false;
        connectionService.stop();
        //notify all payloads waiting to be debug
        onResume();

    }

    public void onResume() {
        synchronized (messageResume) {
            this.currentMessage = null;
            this.messageResume.notify();
        }
        onMessageProcessed();
    }

    public void onMessageProcessed() {
        synchronized (messageProcessed) {
            messageProcessed.notifyAll();
        }
    }

    private void setRunning() {
        keepOnRunning = true;
    }
}
