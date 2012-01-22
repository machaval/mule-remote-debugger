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
import org.mule.debugger.request.NextMessageDebuggerRequest;

import java.io.IOException;

public class DebuggerClient {

    private DebuggerConnection connection;

    public DebuggerClient(DebuggerConnection connection) {
        this.connection = connection;
    }

    public void start(final IDebuggerResponseCallback callback) throws IOException {
        this.connection.connect();
        new Thread() {
            @Override
            public void run() {
                while (connection.isConnected()) {
                    connection.getProtocol().getResponse().callCallback(callback);
                }
            }
        }.start();
    }

    public void exit() {
        this.connection.getProtocol().sendRequest(new ExitDebuggerRequest());

    }

    public void waitForNextMessage() {
        this.connection.getProtocol().sendRequest(new NextMessageDebuggerRequest());
    }

    public void executeScript(String script) {
        this.connection.getProtocol().sendRequest(new ExecuteScriptDebuggerRequest(script));
    }

    public void disconnect() {
        this.connection.disconnect();
    }


}
