/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.transport.IServerDebuggerProtocol;

public class DebuggerCommunicationService implements Runnable {

    private IServerDebuggerProtocol protocol;
    private IDebuggerRequestHandler requestHandler;
    private volatile boolean keepRunning = true;


    public DebuggerCommunicationService(IServerDebuggerProtocol protocol) {
        this.protocol = protocol;
    }

    public void start() {
        run();
    }

    public void run() {
        while (keepRunning) {
            IDebuggerRequest request = this.protocol.getRequest();
            if (requestHandler != null) {
                IDebuggerResponse response = requestHandler.handleRequest(request);
                this.protocol.sendResponse(response);
            }
        }

    }

    public void sendResponse(IDebuggerResponse response) {
        protocol.sendResponse(response);
    }

    public void setRequestHandler(IDebuggerRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void stop() {
        this.keepRunning = false;
    }
}
