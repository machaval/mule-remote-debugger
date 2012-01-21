package org.mule.debugger.remote;


import org.mule.debugger.server.DebuggerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteDebuggerService extends Thread
{

    private int serverPort = 8080;
    private DebuggerHandler handler;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private static Logger log = Logger.getLogger(RemoteDebuggerService.class.getName());

    public RemoteDebuggerService(int port, DebuggerHandler handler) {
        this.serverPort = port;
        this.handler = handler;
    }

    public void run() {
        log.log(Level.INFO, "Server starting at " + serverPort);
        openServerSocket();
        try {
            while (!isStopped()) {
                Socket clientSocket;
                try {
                    clientSocket = this.serverSocket.accept();
                    this.threadPool.execute(new RemoteDebuggerSession(clientSocket, "Console Session",handler));
                } catch (IOException e) {
                    if (!isStopped()) {
                        throw new RuntimeException("Error accepting client connection", e);
                    }
                }
            }
        } finally {
            this.threadPool.shutdown();
            if (!serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing server", e);
                }
            }

        }
        log.log(Level.INFO, "Server Stopped.");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void startService(){
        start();
    }

    public synchronized void stopService() {
        this.isStopped = true;
        try {
            this.threadPool.shutdownNow();
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}