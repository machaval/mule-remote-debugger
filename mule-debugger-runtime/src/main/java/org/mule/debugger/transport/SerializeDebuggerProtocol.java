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

public class SerializeDebuggerProtocol implements IClientDebuggerProtocol, IServerDebuggerProtocol {

    private InputStream input;
    private OutputStream output;

    public SerializeDebuggerProtocol(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    public void sendRequest(IDebuggerRequest request) {

        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(this.output);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
          //  e.printStackTrace();
        }


    }

    public IDebuggerResponse getResponse() {
        try {
            ObjectInputStream input = new ObjectInputStream(this.input);
            IDebuggerResponse iDebuggerResponse = (IDebuggerResponse) input.readObject();
            System.out.println("iDebuggerResponse = " + iDebuggerResponse);
            return iDebuggerResponse;
        } catch (IOException e) {
            //Server disconnect unfriendly
            //e.printStackTrace();
            return new ExitDebuggerResponse();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public IDebuggerRequest getRequest() {
        try {
            ObjectInputStream input = new ObjectInputStream(this.input);
            return (IDebuggerRequest) input.readObject();
        } catch (IOException e) {
            //Client disconnect unfriendly
            //e.printStackTrace();
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
       //     e.printStackTrace();
        }

    }
}
