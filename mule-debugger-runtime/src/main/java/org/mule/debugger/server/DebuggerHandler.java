package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingContext;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DebuggerHandler {

    private final Object semaphore = new Object();
    private final ReentrantLock lock = new ReentrantLock();
    private volatile boolean keepOnRunning = false;
    private final LinkedBlockingQueue<MuleDebuggingContext> payloads = new LinkedBlockingQueue<MuleDebuggingContext>();

    private DebuggerServerSessionFactory sessionFactory;

    public DebuggerServerSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(DebuggerServerSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void debug(final MuleDebuggingContext payload) {
        if (isRunning()) {
            this.payloads.add(payload);

            do {
                synchronized (semaphore) {
                    try {
                        semaphore.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            } while (isMessageProcessed(payload) && isRunning());
        }
    }


    public void start() {
        while (isRunning()) {
            final MuleDebuggingContext message = popMessage();
            if (isRunning()) {
                sessionFactory.createNewSession(message).debugMessage(this);
            }
            synchronized (semaphore) {
                semaphore.notifyAll();
            }
        }

    }

    private boolean isMessageProcessed(MuleDebuggingContext payload) {
        return payloads.contains(payload);
    }

    private MuleDebuggingContext popMessage() {
        try {
            return payloads.take();
        } catch (InterruptedException e) {
            stop();
            return null;
        }

    }


    public boolean isRunning() {
        return keepOnRunning;
    }

    public boolean lockForStart() {
        lock.lock();
        try {
            if (!isRunning()) {
                setRunning();
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public void stop() {
        keepOnRunning = false;
        //notify all payloads waiting to be debug
        synchronized (semaphore) {
            semaphore.notifyAll();
        }


    }

    private void setRunning() {
        keepOnRunning = true;
    }
}
