package org.mule.debugger.commands;

import org.mule.api.MuleMessage;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.*;
import org.mule.debugger.server.MuleMessageInfoBuilder;

public class ExecuteScriptCommandImpl extends AbstractCommand {

    public static final String SCRIPT = "execute";

    private String script;

    public ExecuteScriptCommandImpl(String script) {
        this.script = script;
    }

    public IDebuggerResponse execute() {
        MuleDebuggingContext debuggingMessage = getMuleDebuggingMessage();
        MuleMessage message = debuggingMessage.getMessage();

        ClassLoader oldContext = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(debuggingMessage.getContextClassLoader());

            Object result = debuggingMessage.getExpressionManager().evaluate(script, message);
            final ScriptResultInfo info = new ScriptResultInfo(MuleMessageInfoBuilder.createFromMuleMessage(debuggingMessage), ObjectFieldDefinition.createFromObject(result, "result"),
                    String.valueOf(result.getClass()),
                    String.valueOf(result));
            return new ExecuteScriptResponse(info);
        } catch (Exception e) {
            return new ExceptionResponse(new RemoteDebugException(e.getMessage(), e));
        } finally {
            Thread.currentThread().setContextClassLoader(oldContext);
        }
    }


    public String getId() {
        return SCRIPT;
    }
}
