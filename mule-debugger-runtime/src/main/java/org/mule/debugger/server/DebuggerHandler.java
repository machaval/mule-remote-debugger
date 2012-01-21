package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingMessage;
import org.mule.debugger.server.DebuggerServerSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DebuggerHandler {

    private final Object lock = new Object();
    private volatile boolean keepOnRunning = false;
    private final List<MuleDebuggingMessage> payloads = new ArrayList<MuleDebuggingMessage>();
    private DebuggerServerSessionFactory sessionFactory;

    public DebuggerServerSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(DebuggerServerSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void debug(final MuleDebuggingMessage payload) {
        synchronized (payloads) {

            this.payloads.add(payload);
            this.payloads.notifyAll();
        }

        while (isMessageProcessed(payload)) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {

                }
            }
        }

    }


    public void start() {
        setRunning();

        while (isRunning()) {

            while (hasMorePayloads()) {
                final MuleDebuggingMessage message = popMessage();
                sessionFactory.createNewSession(message).start(this);
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
            if (isRunning()) {
                waitForPayload();
            }

        }

    }

    private boolean isMessageProcessed(MuleDebuggingMessage payload) {
        synchronized (payloads) {
            return payloads.contains(payload);
        }
    }

    private MuleDebuggingMessage popMessage() {
        synchronized (payloads) {
            return payloads.remove(payloads.size() - 1);
        }
    }

    private boolean hasMorePayloads() {
        synchronized (payloads) {
            return !payloads.isEmpty();
        }
    }


    private void waitForPayload() {
        synchronized (payloads) {
            try {
                while (!hasMorePayloads()) {
                    payloads.wait();
                }
            } catch (InterruptedException e) {

            }
        }
    }

    public synchronized boolean isRunning() {
        return keepOnRunning;
    }

    public void stop() {
        keepOnRunning = false;
    }

    private void setRunning() {
        keepOnRunning = true;
    }
}
