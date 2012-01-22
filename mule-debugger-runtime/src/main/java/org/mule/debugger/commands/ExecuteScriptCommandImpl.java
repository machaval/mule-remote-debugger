package org.mule.debugger.commands;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
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

        GroovyShell shell = new GroovyShell();
        Script gscript = shell.parse(script);
        gscript.setProperty("payload", getCurrentSession().getPayload().getPayload());
        gscript.setProperty("inboundheaders", getCurrentSession().getPayload().getInboundHeaders());

        Object result = gscript.run();
        ScriptResultInfo info = new ScriptResultInfo(objectToString(result),
                String.valueOf(result.getClass()),
                String.valueOf(result));
        return new ExecuteScriptResponse(info);

    }


    public String getId() {
        return SCRIPT;
    }
}
