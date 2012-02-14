package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class DebuggerService {


    private volatile boolean keepOnRunning = false;

    private final Object messageLock = new Object();
    private CountDownLatch resumeLatch;

    private final List<MuleDebuggingContext> muleMessages = new ArrayList<MuleDebuggingContext>();
    private final Set<String> messagesToListen = new HashSet<String>();

    private DebuggerServerFactory debuggerFactory;
    private DebuggerCommunicationService connectionService;

    public DebuggerServerFactory getDebuggerFactory() {
        return debuggerFactory;
    }

    public void setDebuggerFactory(DebuggerServerFactory debuggerFactory) {
        this.debuggerFactory = debuggerFactory;
    }

    public void onMessagedMoved(final MuleDebuggingContext payload){

    }

    public void onBreakPoint(final MuleDebuggingContext payload) {
        if (isRunning()) {
            synchronized (muleMessages) {
                this.muleMessages.add(payload);
                this.muleMessages.notifyAll();
            }

            do {
                //wait till this message is being processed
                synchronized (messageLock) {
                    try {
                        messageLock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } while (isMessageProcessed(payload) && isRunning());
        }
    }


    public void startDebugging() {
        try {
            connectionService = debuggerFactory.createDebuggerConnectionService();
            MuleMessageDebuggerRequestHandler requestHandler = debuggerFactory.createMuleMessageRequestHandler(this);
            connectionService.setRequestHandler(requestHandler);
            debuggerFactory.startConnectionService(connectionService);
            while (isRunning()) {
                final MuleDebuggingContext message = popMessage();
                if (message != null && isRunning()) {
                    resumeLatch = new CountDownLatch(1);
                    connectionService.sendResponse(debuggerFactory.createNewMessageResponse(message.getMessage()));
                    requestHandler.setMuleDebuggingContext(message);
                    holdTillResume(requestHandler);

                }

            }
        } finally {
            if (isRunning()) {
                stopDebugging();
            }
        }

    }

    private void holdTillResume(IDebuggerRequestHandler requestHandler) {
        try {
            resumeLatch.await();
            requestHandler.setMuleDebuggingContext(null);

        } catch (InterruptedException e) {
            stopDebugging();
        }
    }

    private boolean isMessageProcessed(MuleDebuggingContext payload) {
        return muleMessages.contains(payload);
    }

    private MuleDebuggingContext popMessage() {

        synchronized (muleMessages) {

            while (muleMessages.isEmpty() && isRunning()) {
                try {
                    muleMessages.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return muleMessages.isEmpty() ? null : muleMessages.remove(0);

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
        keepOnRunning = false;
        connectionService.stop();
        //notify all payloads waiting to be debug
        if (resumeLatch != null) {
            onResume();
        }

        synchronized (muleMessages) {
            muleMessages.clear();
            muleMessages.notifyAll();
        }
        notifyMuleMessageProcessed();
    }

    public void onResume() {
        resumeLatch.countDown();
        notifyMuleMessageProcessed();
    }

    public void notifyMuleMessageProcessed() {
        synchronized (messageLock) {
            messageLock.notifyAll();
        }
    }

    private void setRunning() {
        keepOnRunning = true;
    }
}
