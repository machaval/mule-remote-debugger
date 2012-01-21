package org.mule.debugger.commands;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.mule.debugger.response.DebuggerResponse;
import org.mule.debugger.response.ScriptEvaluationInfo;
import org.mule.debugger.response.ScriptEvaluationResponse;

public class ExecuteScriptCommandImpl extends AbstractCommand {

    public static final String SCRIPT = "execute";

    private String script;

    public ExecuteScriptCommandImpl(String script) {
        this.script = script;
    }

    public DebuggerResponse execute() {

        GroovyShell shell = new GroovyShell();
        Script gscript = shell.parse(script);
        gscript.setProperty("payload", getCurrentSession().getPayload().getPayload());
        gscript.setProperty("inboundheaders", getCurrentSession().getPayload().getInboundHeaders());

        Object result = gscript.run();
        ScriptEvaluationInfo info = new ScriptEvaluationInfo(objectToString(result),
                String.valueOf(result.getClass()),
                String.valueOf(result));
        return new ScriptEvaluationResponse(info);

    }


    public String getId() {
        return SCRIPT;
    }
}
