package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class DebuggerHandler {

    private final Object semaphore = new Object();
    private final ReentrantLock lock = new ReentrantLock();
    private volatile boolean keepOnRunning = false;
    private final BlockingQueue<MuleDebuggingMessage> payloads = new LinkedBlockingQueue<MuleDebuggingMessage>();

    private DebuggerServerSessionFactory sessionFactory;

    public DebuggerServerSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(DebuggerServerSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void debug(final MuleDebuggingMessage payload) {

        this.payloads.add(payload);
        synchronized (payloads) {
            this.payloads.notifyAll();
        }

        while (isMessageProcessed(payload) && isRunning()) {
            synchronized (semaphore) {
                try {
                    semaphore.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }


    public void start() {
        while (isRunning()) {
            final MuleDebuggingMessage message = popMessage();
            if (isRunning()) {
                sessionFactory.createNewSession(message).debugMessage(this);
            }
            synchronized (semaphore) {
                semaphore.notifyAll();
            }
        }

    }

    private boolean isMessageProcessed(MuleDebuggingMessage payload) {
        return payloads.contains(payload);
    }

    private MuleDebuggingMessage popMessage() {
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

        //notify debugger thread if it is waiting for a message
        synchronized (payloads) {
            payloads.notifyAll();
        }
    }

    private void setRunning() {
        keepOnRunning = true;
    }
}
