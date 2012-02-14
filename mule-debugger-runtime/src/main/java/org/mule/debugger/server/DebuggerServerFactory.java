/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.api.MuleMessage;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.transport.IServerDebuggerProtocol;

public class DebuggerServerFactory {

    private IServerDebuggerProtocol protocolServer;

    public DebuggerServerFactory(IServerDebuggerProtocol protocolServer) {

        this.protocolServer = protocolServer;
    }

    public DebuggerCommunicationService createDebuggerConnectionService(){
        return new DebuggerCommunicationService(protocolServer);
    }

    public MuleMessageArrivedResponse createNewMessageResponse(MuleMessage message){
         return new MuleMessageArrivedResponse(MuleMessageInfoBuilder.createFromMuleMessage(message));
    }

    public void startConnectionService(DebuggerCommunicationService service){
        new Thread(service).start();
    }

    public MuleMessageDebuggerRequestHandler createMuleMessageRequestHandler(DebuggerService debuggerService) {
        return new MuleMessageDebuggerRequestHandler( debuggerService);
    }
}
