/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.client;

import org.mule.debugger.request.ExecuteScriptDebuggerRequest;
import org.mule.debugger.request.ExitDebuggerRequest;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.request.ResumeDebuggerRequest;
import org.mule.debugger.response.IDebuggerResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DebuggerClient {

    private DebuggerConnection connection;
    private Map<IDebuggerRequest, IDebuggerResponseCallback> handler;


    public DebuggerClient(DebuggerConnection connection) {
        this.connection = connection;
        this.handler = new HashMap<IDebuggerRequest, IDebuggerResponseCallback>();
    }

    public void start(final IDebuggerResponseCallback defaultHandler) throws IOException {

        this.connection.connect();
        new Thread() {
            @Override
            public void run() {
                while (connection.isConnected()) {
                    IDebuggerResponse response = connection.getProtocol().getResponse();
                    IDebuggerRequest request = response.getRequest();
                    if (request != null && handler.containsKey(request)) {
                        response.callCallback(handler.get(request));
                        unRegisterRequest(request);
                    } else {
                        response.callCallback(defaultHandler);
                    }
                }
            }
        }.start();
    }

    private void unRegisterRequest(IDebuggerRequest request) {
        handler.remove(request);
    }

    public void exit() {
        this.connection.getProtocol().sendRequest(new ExitDebuggerRequest());

    }

    public void resume() {
        this.connection.getProtocol().sendRequest(new ResumeDebuggerRequest());
    }

    public void executeScript(String script) {
        executeScript(script, null);
    }

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

    public void disconnect() {
        this.connection.disconnect();
    }


}
