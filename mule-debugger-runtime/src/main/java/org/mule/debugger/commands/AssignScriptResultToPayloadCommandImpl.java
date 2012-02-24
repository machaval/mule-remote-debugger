package org.mule.debugger.commands;

import org.mule.api.MuleMessage;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.ExceptionResponse;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.server.MuleMessageInfoBuilder;

public class AssignScriptResultToPayloadCommandImpl extends AbstractCommand {

    public static final String SCRIPT = "execute";

    private String script;

    public AssignScriptResultToPayloadCommandImpl(String script) {
        this.script = script;
    }

    public IDebuggerResponse execute() {
        MuleDebuggingContext debuggingMessage = getMuleDebuggingMessage();
        MuleMessage message = debuggingMessage.getMessage();
        ClassLoader oldContext = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(debuggingMessage.getContextClassLoader());

            Object result = debuggingMessage.getExpressionManager().evaluate(script, message);
            debuggingMessage.getMessage().setPayload(result);
            return new MuleMessageArrivedResponse(MuleMessageInfoBuilder.createFromMuleMessage(getMuleDebuggingMessage()));
        }catch (Exception e){
            return new ExceptionResponse(new RemoteDebugException(e.getMessage()));
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldContext);
        }
    }


    public String getId() {
        return SCRIPT;
    }
}
