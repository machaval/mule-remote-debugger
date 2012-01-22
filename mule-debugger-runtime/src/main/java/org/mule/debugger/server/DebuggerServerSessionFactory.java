/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.MuleDebuggingMessage;

public class DebuggerServerSessionFactory {

    private IServerDebuggerProtocol protocolServer;

    public DebuggerServerSessionFactory(IServerDebuggerProtocol protocolServer) {

        this.protocolServer = protocolServer;
    }

    public DebuggerServerSession createNewSession(MuleDebuggingMessage message) {
        return new DebuggerServerSession(protocolServer, message);
    }
}
