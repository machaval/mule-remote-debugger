package org.mule.debugger.commands;

import org.mule.api.MuleMessage;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.debugger.response.ExecuteScriptResponse;

public class ExecuteScriptCommandImpl extends AbstractCommand {

    public static final String SCRIPT = "execute";

    private String script;

    public ExecuteScriptCommandImpl(String script) {
        this.script = script;
    }

    public IDebuggerResponse execute() {
        MuleDebuggingContext debuggingMessage = getCurrentSession().getMuleDebuggingMessage();
        MuleMessage message = debuggingMessage.getMessage();
        ClassLoader oldContext = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(debuggingMessage.getContextClassLoader());
            Object result = debuggingMessage.getExpressionManager().evaluate(script, message);
            ScriptResultInfo info = new ScriptResultInfo(objectToString(result),
                    String.valueOf(result.getClass()),
                    String.valueOf(result));
            return new ExecuteScriptResponse(info);
        } finally {
            Thread.currentThread().setContextClassLoader(oldContext);
        }
    }


    public String getId() {
        return SCRIPT;
    }
}
