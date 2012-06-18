package org.mule.debugger.remote;


import org.mule.debugger.response.ErrorResponse;
import org.mule.debugger.server.DebuggerHandler;
import org.mule.debugger.server.DebuggerServerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RemoteDebuggerSession implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    private final DebuggerHandler handler;
    private static Logger log = Logger.getLogger(RemoteDebuggerSession.class.getName());


    public RemoteDebuggerSession(Socket clientSocket, String serverText, DebuggerHandler handler) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.handler = handler;
    }

    public void run() {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
            final DebuggerServerFactory debuggerFactory = new DebuggerServerFactory(input, output);
            if (!handler.isClientConnected()) {
                handler.setDebuggerFactory(debuggerFactory);
                handler.connectClient();
                handler.disconnectClient();
            } else {
                debuggerFactory.createProtocol().sendResponse(new ErrorResponse("I can not attend you right now someone else is already connected. Try later!"));
            }


        } catch (IOException e) {
            log.log(Level.WARNING, "Exception while connecting to client", e);
        } finally {

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.log(Level.WARNING, "Exception while closing stream", e);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.log(Level.WARNING, "Exception while closing stream", e);
                }
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                log.log(Level.WARNING, "Exception while closing socket", e);
            }
        }
    }


}
