/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.client;

import org.mule.debugger.request.*;
import org.mule.debugger.response.IDebuggerServerEvent;
import org.mule.debugger.transport.IClientDebuggerProtocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to connect to the debugger
 */
public class DebuggerClient {

    private DebuggerConnection connection;
    private Map<IDebuggerRequest, IDebuggerResponseCallback> handler;


    public DebuggerClient(DebuggerConnection connection) {
        this.connection = connection;
        this.handler = new HashMap<IDebuggerRequest, IDebuggerResponseCallback>();
    }

    /**
     * Method used to connect to the server
     *
     * @param defaultHandler The default response handler
     * @throws IOException If any connection issue
     */
    public void start(final IDebuggerResponseCallback defaultHandler) throws IOException {
        this.connection.connect();
        final Runnable runnable = new ServerResponseService(defaultHandler);
        new Thread(runnable).start();
    }

    private void unRegisterRequest(IDebuggerRequest request) {
        handler.remove(request);
    }

    /**
     * Exit the connection
     */
    public void exit() {
        this.connection.getProtocol().sendRequest(new ExitDebuggerRequest());

    }

    /**
     * Resume current mule message
     */
    public void resume() {
        this.connection.getProtocol().sendRequest(new ResumeDebuggerRequest());
    }

    /**
     * Move till next message processor
     */
    public void nextStep() {
        this.connection.getProtocol().sendRequest(new NextStepDebuggerRequest());
    }


    /**
     * Execute a mule script and execute the result to the mule payload
     *
     * @param script   The script to be executed
     * @param callback The callback for this request
     */
    public void executeScriptAssignResultToPayload(String script, IDebuggerResponseCallback callback) {
        AssignScriptResultToPayloadDebuggerRequest request = new AssignScriptResultToPayloadDebuggerRequest(script);
        if (callback != null) {
            registerCallback(callback, request);
        }
        this.connection.getProtocol().sendRequest(request);
    }

    /**
     * Execute a mule script
     *
     * @param script   The script to be executed
     * @param callback The callback for this request
     */
    public void executeScript(String script, IDebuggerResponseCallback callback) {
        IDebuggerRequest request = new ExecuteScriptDebuggerRequest(script);
        if (callback != null) {
            registerCallback(callback, request);
        }
        this.connection.getProtocol().sendRequest(request);
    }

    protected void registerCallback(IDebuggerResponseCallback callback, IDebuggerRequest request) {
        handler.put(request, callback);
    }

    /**
     * Disconnect from the server
     */
    public void disconnect() {
        this.connection.disconnect();
    }


    private class ServerResponseService implements Runnable {

        private IClientDebuggerProtocol protocol;
        private final IDebuggerResponseCallback defaultHandler;

        public ServerResponseService(IDebuggerResponseCallback defaultHandler) {
            this.defaultHandler = defaultHandler;
        }

        public void run() {
            protocol = connection.getProtocol();
            while (connection.isConnected()) {
                IDebuggerServerEvent response = protocol.getResponse();
                IDebuggerRequest request = response.getRequest();
                if (request != null && handler.containsKey(request)) {
                    response.callCallback(handler.get(request));
                    unRegisterRequest(request);
                } else {
                    response.callCallback(defaultHandler);
                }
            }
        }
    }
}
