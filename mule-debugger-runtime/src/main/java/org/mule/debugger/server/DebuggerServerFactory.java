/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.response.ConnectionEstablishedEvent;
import org.mule.debugger.response.MuleMessageArrivedEvent;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.transport.IServerProtocolFactory;
import org.mule.debugger.transport.SerializeDebuggerProtocol;

import java.io.InputStream;
import java.io.OutputStream;

public class DebuggerServerFactory implements IServerProtocolFactory {


    private final InputStream input;
    private final OutputStream output;

    public DebuggerServerFactory(InputStream input, OutputStream output) {


        this.input = input;
        this.output = output;
    }

    public ClientRequestService createDebuggerRequestService() {
        return new ClientRequestService(this);
    }

    public MuleMessageArrivedEvent createNewMessageEvent(MuleDebuggingContext message) {
        return new MuleMessageArrivedEvent(MuleMessageInfoBuilder.createFromMuleMessage(message));
    }

    public ConnectionEstablishedEvent createConnectionEstablishedEvent() {
        return new ConnectionEstablishedEvent();
    }


    public MuleMessageDebuggerRequestHandler createMuleMessageRequestHandler(DebuggerHandler debuggerService) {
        return new MuleMessageDebuggerRequestHandler(debuggerService);
    }

    public IServerDebuggerProtocol createProtocol() {
        return new SerializeDebuggerProtocol(input, output);
    }
}
