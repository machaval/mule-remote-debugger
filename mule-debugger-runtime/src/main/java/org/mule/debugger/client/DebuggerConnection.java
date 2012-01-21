/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DebuggerConnection {

    private String hostName;
    private int port;
    private Socket client;
    private InputStreamReader inputStream;
    private OutputStreamWriter outputStream;


    public DebuggerConnection(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;

    }

    public void connect() {


        try {
            if (!isConnected()) {
                client = new Socket(hostName, port);
                inputStream = new InputStreamReader(client.getInputStream());
                outputStream = new OutputStreamWriter(client.getOutputStream());
            }

        } catch (IOException e) {

        }


    }


    public void disconnect() {
        try {
            if (isConnected()) {
                client.close();
                inputStream.close();
                outputStream.close();
            }

        } catch (IOException e) {

        }
    }

    private boolean isConnected() {
        return !client.isClosed();
    }

}
