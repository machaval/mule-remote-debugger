package org.mule.debugger.commands;

import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.server.DebuggerHandler;
import org.mule.debugger.server.DebuggerServerSession;

public interface ICommand
{
    IDebuggerResponse execute();

    void setCurrentSession(DebuggerServerSession currentSession);

    void setHandler(DebuggerHandler handler);

    String getId();


}
