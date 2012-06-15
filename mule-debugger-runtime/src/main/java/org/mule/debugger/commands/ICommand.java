package org.mule.debugger.commands;

import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.server.DebuggerHandler;

public interface ICommand {
    IDebuggerResponse execute();


    void setHandler(DebuggerHandler handler);

    String getId();


    void setDebuggingContext(MuleDebuggingContext muleDebuggingMessage);
}
