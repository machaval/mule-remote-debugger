/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.IDebuggerServerEvent;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.transport.IServerProtocolFactory;

public class ClientRequestService implements Runnable {

    private IServerProtocolFactory protocolFactory;
    private IDebuggerRequestHandler requestHandler;
    private volatile boolean keepRunning = true;
    private IServerDebuggerProtocol protocol;


    public ClientRequestService(IServerProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public void run() {
        initProtocol();
        while (keepRunning) {
            IDebuggerRequest request = protocol.getRequest();
            if (requestHandler != null) {
                IDebuggerServerEvent response = requestHandler.handleRequest(request);
                protocol.sendResponse(response);
            }
        }
    }

    private void initProtocol() {
        if (protocol == null) {
            protocol = this.protocolFactory.createProtocol();
        }
    }

    public void sendEvent(IDebuggerServerEvent response) {
        initProtocol();
        protocol.sendResponse(response);
    }

    public void setRequestHandler(IDebuggerRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void stop() {
        this.keepRunning = false;
    }
}
