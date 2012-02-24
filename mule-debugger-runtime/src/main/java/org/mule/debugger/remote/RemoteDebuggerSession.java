package org.mule.debugger.remote;


import org.mule.debugger.response.ConnectionEstablishedResponse;
import org.mule.debugger.response.ErrorResponse;
import org.mule.debugger.server.DebuggerServerFactory;
import org.mule.debugger.server.DebuggerService;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.transport.SerializeDebuggerProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RemoteDebuggerSession implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    private final DebuggerService handler;
    private static Logger log = Logger.getLogger(RemoteDebuggerSession.class.getName());


    public RemoteDebuggerSession(Socket clientSocket, String serverText, DebuggerService handler) {
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
            IServerDebuggerProtocol serverDebuggerProtocol = new SerializeDebuggerProtocol(input, output);

            if (handler.lockForStart()) {
                serverDebuggerProtocol.sendResponse(new ConnectionEstablishedResponse());
                handler.setDebuggerFactory(new DebuggerServerFactory(serverDebuggerProtocol));
                handler.startDebugging();
                handler.stopDebugging();
            } else {
                serverDebuggerProtocol.sendResponse(new ErrorResponse("I can not attend you right now someone else is already connected. Try later!"));
            }


        } catch (IOException e) {
            log.log(Level.WARNING, "Exception while connecting to client", e);
        } finally {

            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {

                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {

                }
            }
            try {
                clientSocket.close();
            } catch (IOException e) {

            }
        }
    }


}
