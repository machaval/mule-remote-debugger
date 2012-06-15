package org.mule.debugger.remote;



import org.mule.debugger.server.DebuggerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class RemoteDebuggerService extends Thread {

    private int serverPort = 8080;
    private DebuggerHandler handler;
    private ServerSocket serverSocket = null;
    private volatile boolean isStopped = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private transient static Logger logger = Logger.getLogger(RemoteDebuggerService.class.getName());

    public RemoteDebuggerService(int port, DebuggerHandler handler) {
        this.serverPort = port;
        this.handler = handler;
    }

    public void run() {
        logger.info("Server starting at " + serverPort);
        createServerSocket();
        try {
            while (!isStopped()) {
                Socket clientSocket;
                try {
                    clientSocket = this.serverSocket.accept();
                    this.threadPool.execute(new RemoteDebuggerSession(clientSocket, "Console Session", handler));
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
        logger.info("Server stopped");

    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }



    public void startService() {
        start();
    }

    public void stopService() {
        try {
            this.isStopped = true;
            this.threadPool.shutdownNow();
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void createServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}
