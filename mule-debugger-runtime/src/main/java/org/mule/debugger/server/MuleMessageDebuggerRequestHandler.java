package org.mule.debugger.server;

import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.commands.ICommand;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.ExceptionResponse;
import org.mule.debugger.response.IDebuggerResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the request from the debugger client.
 */
public class MuleMessageDebuggerRequestHandler implements IDebuggerRequestHandler {


    private static Logger log = Logger.getLogger(MuleMessageDebuggerRequestHandler.class.getName());
    private MuleDebuggingContext debuggingContext;
    private DebuggerHandler debuggerHandler;

    public MuleMessageDebuggerRequestHandler(DebuggerHandler debuggerHandler) {
        this.debuggerHandler = debuggerHandler;
    }


    public IDebuggerResponse handleRequest(IDebuggerRequest request) {
        ICommand command = request.createCommand();
        command.setHandler(debuggerHandler);
        command.setDebuggingContext(debuggingContext);
        IDebuggerResponse response;
        try {
            response = command.execute();
        } catch (Exception e) {
            log.log(Level.WARNING, "Exception while executing command", e);
            response = new ExceptionResponse(new RemoteDebugException(e.getMessage(), e));
        }
        response.setRequest(request);
        return response;
    }

    public void setCurrentMuleDebuggingEvent(MuleDebuggingContext debuggingContext) {

        this.debuggingContext = debuggingContext;
    }
}
