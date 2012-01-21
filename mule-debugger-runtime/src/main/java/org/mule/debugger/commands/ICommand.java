package org.mule.debugger.commands;

import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.DebuggerResponse;
import org.mule.debugger.server.DebuggerHandler;
import org.mule.debugger.server.DebuggerServerSession;

public interface ICommand
{
    DebuggerResponse execute();

    void setCurrentSession(DebuggerServerSession currentSession);

    void setHandler(DebuggerHandler handler);

    String getId();


}
