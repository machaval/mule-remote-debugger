/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.transport;

import org.mule.debugger.request.ExitDebuggerRequest;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.ExitDebuggerResponse;
import org.mule.debugger.response.IDebuggerResponse;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializeDebuggerProtocol implements IClientDebuggerProtocol, IServerDebuggerProtocol {

    private static Logger logger = Logger.getLogger(SerializeDebuggerProtocol.class.getName());

    private InputStream input;
    private OutputStream output;

    public SerializeDebuggerProtocol(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    public void sendRequest(IDebuggerRequest request) {
        System.out.println("SerializeDebuggerProtocol.sendRequest" + request);
        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(this.output);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to send a request", e);
        }


    }

    public IDebuggerResponse getResponse() {
        try {
            ObjectInputStream input = new ObjectInputStream(this.input);
            IDebuggerResponse iDebuggerResponse = (IDebuggerResponse) input.readObject();
            System.out.println("iDebuggerResponse = " + iDebuggerResponse);
            return iDebuggerResponse;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to get a response", e);
            return new ExitDebuggerResponse();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public IDebuggerRequest getRequest() {
        try {
            ObjectInputStream input = new ObjectInputStream(this.input);
            IDebuggerRequest iDebuggerRequest = (IDebuggerRequest) input.readObject();
            System.out.println("iDebuggerRequest = " + iDebuggerRequest);
            return iDebuggerRequest;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to get a request", e);
            return new ExitDebuggerRequest();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendResponse(IDebuggerResponse response) {
        System.out.println("response = " + response);
        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(this.output);
            output.writeObject(response);
            output.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to send a response", e);
        }

    }
}
