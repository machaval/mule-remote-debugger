/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.client;

import org.mule.debugger.transport.IClientDebuggerProtocol;
import org.mule.debugger.transport.SerializeDebuggerProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Holds the properties for the connection
 */
public class DebuggerConnection {

    private String hostName;
    private int port;
    private Socket client;
    private SerializeDebuggerProtocol protocol;


    public DebuggerConnection(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;

    }

    public boolean connect() throws IOException {

        if (!isConnected()) {
            client = new Socket(hostName, port);

            protocol = new SerializeDebuggerProtocol(client.getInputStream(), client.getOutputStream());
            return true;
        }

        return false;

    }

    public IClientDebuggerProtocol getProtocol() {
        return protocol;
    }


    public void disconnect() {
        try {
            if (isConnected()) {
                protocol.close();
                client.close();

            }


        } catch (IOException e) {
        } finally {
            client = null;
        }
    }

    public boolean isConnected() {
        return client != null && !client.isClosed();
    }

}
