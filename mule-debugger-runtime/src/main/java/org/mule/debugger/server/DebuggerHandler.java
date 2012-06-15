package org.mule.debugger.server;

import org.mule.api.MuleMessage;
import org.mule.debugger.MuleDebuggingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Handles the message that is being debugged. And notifies the debugger client with the mule message.
 */
public class DebuggerHandler {


    private volatile boolean clientConnected = false;

    private Semaphore messageProcessed = new Semaphore(1);
    private Semaphore messageResume = new Semaphore(1);

    private final Lock singleMessage = new ReentrantLock();
    private final Lock singleClient = new ReentrantLock();


    private DebuggerServerFactory debuggerFactory;
    private DebuggerCommunicationService connectionService;
    private MuleMessageDebuggerRequestHandler requestHandler;
    private List<IDebuggerServiceListener> listeners;

    private MuleMessage currentMessage = null;

    public DebuggerHandler() {
        listeners = new ArrayList<IDebuggerServiceListener>();
    }

    public DebuggerServerFactory getDebuggerFactory() {
        return debuggerFactory;
    }

    public void setDebuggerFactory(DebuggerServerFactory debuggerFactory) {
        this.debuggerFactory = debuggerFactory;
    }


    public void breakPoint(final MuleMessage newMuleMessage) {
        if (isClientConnected()) {

            boolean isCurrentMessage = isDebuggingThisMessage(newMuleMessage);
            if (!isCurrentMessage) {

                try {
                    messageResume.acquire();
                } catch (InterruptedException e) {
                    resume();
                }

                this.currentMessage = newMuleMessage;
            }
        }
    }

    public void messageArrived(MuleDebuggingContext event) {
        if (isClientConnected() && isDebuggingThisMessage(event.getMessage())) {
            try {
                //only one event can be processed at the same time
                singleMessage.lock();
                connectionService.sendResponse(debuggerFactory.createNewMessageResponse(event));
                requestHandler.setCurrentMuleDebuggingEvent(event);

                try {
                    messageProcessed.acquire();
                } catch (InterruptedException e) {
                    //resume this event
                    resume();
                }

            } finally {
                singleMessage.unlock();
            }
        }
    }

    public boolean isDebuggingThisMessage(MuleMessage newMuleMessage) {
        return this.currentMessage != null && this.currentMessage.getMessageRootId().equals(newMuleMessage.getMessageRootId());
    }

    public void addListener(IDebuggerServiceListener listener) {
        this.listeners.add(listener);
    }


    public void connectClient() {
        if (singleClient.tryLock()) {
            try {
                clientConnected = true;
                for (IDebuggerServiceListener listener : listeners) {
                    listener.onStart();
                }
                connectionService = debuggerFactory.createDebuggerConnectionService();
                requestHandler = debuggerFactory.createMuleMessageRequestHandler(this);
                connectionService.setRequestHandler(requestHandler);
                connectionService.start();
            } finally {
                if (isClientConnected()) {
                    disconnectClient();
                }
                singleClient.unlock();
            }
        }
    }


    public boolean isClientConnected() {
        return clientConnected;
    }


    public void disconnectClient() {
        if (isClientConnected()) {
            try {
                for (IDebuggerServiceListener listener : listeners) {
                    listener.onStop();
                }
                connectionService.stop();
                clientConnected = false;
            } finally {
                resume();
            }
        }
    }

    public void resume() {
        this.currentMessage = null;
        this.messageResume.release();

        stepOver();
    }

    public void stepOver() {
        messageProcessed.release();
    }


}
