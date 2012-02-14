package org.mule.debugger.commands;

import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.server.DebuggerService;

public interface ICommand
{
    IDebuggerResponse execute();


    void setHandler(DebuggerService handler);

    String getId();


    void setDebuggingContext(MuleDebuggingContext muleDebuggingMessage);
}
