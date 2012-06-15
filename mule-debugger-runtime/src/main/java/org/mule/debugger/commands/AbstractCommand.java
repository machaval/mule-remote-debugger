/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.commands;


import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.server.DebuggerHandler;

public abstract class AbstractCommand implements ICommand {

    private DebuggerHandler handler;
    private MuleDebuggingContext muleDebuggingMessage;


    public DebuggerHandler getHandler() {
        return handler;
    }


    public void setHandler(DebuggerHandler handler) {
        this.handler = handler;
    }

    public void setDebuggingContext(MuleDebuggingContext muleDebuggingMessage) {

        this.muleDebuggingMessage = muleDebuggingMessage;
    }


    public MuleDebuggingContext getMuleDebuggingMessage() {
        return muleDebuggingMessage;
    }
}
