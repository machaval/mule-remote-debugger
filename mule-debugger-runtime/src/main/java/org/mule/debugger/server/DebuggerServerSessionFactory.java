/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingMessage;
import org.mule.debugger.server.DebuggerServerSession;

import java.io.InputStream;
import java.io.OutputStream;

public class DebuggerServerSessionFactory
{
    private OutputStream output;
    private InputStream input;

    public DebuggerServerSessionFactory(OutputStream output, InputStream input)
    {
        this.output = output;
        this.input = input;
    }

    public DebuggerServerSession createNewSession(MuleDebuggingMessage message){
        return new DebuggerServerSession(input,output,message);
    }
}
