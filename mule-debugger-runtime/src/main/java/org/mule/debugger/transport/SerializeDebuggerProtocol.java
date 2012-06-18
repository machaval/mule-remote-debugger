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
import org.mule.debugger.response.IDebuggerServerEvent;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializeDebuggerProtocol implements IClientDebuggerProtocol, IServerDebuggerProtocol {

    private static Logger logger = Logger.getLogger(SerializeDebuggerProtocol.class.getName());

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final Lock singleSend = new ReentrantLock();

    public SerializeDebuggerProtocol(InputStream input, OutputStream output) {

        try {
            this.output = new ObjectOutputStream(output);
            this.input = new ObjectInputStream(input);
        } catch (IOException e) {
            logger.log(Level.INFO, "Error on creating ", e);
        }

    }

    public void sendRequest(IDebuggerRequest request) {
        System.out.println("SerializeDebuggerProtocol.sendRequest" + request);
        try {
            singleSend.lock();
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to send a request", e);
        } finally {
            singleSend.unlock();
        }


    }

    public IDebuggerServerEvent getResponse() {
        try {
            IDebuggerServerEvent iDebuggerResponse = (IDebuggerServerEvent) input.readObject();
            System.out.println("iDebuggerResponse = " + iDebuggerResponse);
            return iDebuggerResponse;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to get a response", e);
            return new ExitDebuggerResponse();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while trying to get a request", e);
            return new ExitDebuggerResponse();
        }

    }

    public IDebuggerRequest getRequest() {
        try {
            IDebuggerRequest iDebuggerRequest = (IDebuggerRequest) input.readObject();
            System.out.println("iDebuggerRequest = " + iDebuggerRequest);
            return iDebuggerRequest;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to get a request", e);
            return new ExitDebuggerRequest();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while trying to get a request", e);
            return new ExitDebuggerRequest();
        }

    }

    public void sendResponse(IDebuggerServerEvent response) {
        System.out.println("response = " + response);
        try {
            singleSend.lock();
            output.writeObject(response);
            output.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error while trying to send a response", e);
        } finally {
            singleSend.unlock();
        }

    }

    public void close() {
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
