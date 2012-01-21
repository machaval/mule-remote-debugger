/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.IDebuggerProtocol;
import org.mule.debugger.MuleDebuggingMessage;
import org.mule.debugger.server.DebuggerServerSession;

import java.io.InputStream;
import java.io.OutputStream;

public class DebuggerServerSessionFactory {

    private IDebuggerProtocol protocol;

    public DebuggerServerSessionFactory(IDebuggerProtocol protocol) {

        this.protocol = protocol;
    }

    public DebuggerServerSession createNewSession(MuleDebuggingMessage message) {
        return new DebuggerServerSession(protocol, message);
    }
}
