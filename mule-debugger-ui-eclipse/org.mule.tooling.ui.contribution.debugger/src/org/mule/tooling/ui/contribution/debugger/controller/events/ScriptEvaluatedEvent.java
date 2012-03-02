
package org.mule.tooling.ui.contribution.debugger.controller.events;

import org.mule.debugger.response.ScriptResultInfo;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.event.IEvent;
import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public class ScriptEvaluatedEvent implements IEvent
{
    private ScriptResultInfo scriptResult;

    public ScriptEvaluatedEvent(ScriptResultInfo scriptResult)
    {
        super();
        this.setScriptResult(scriptResult);
    }

    @Override
    public IEventType getAssociatedType()
    {

        return DebuggerEventType.SCRIPT_EVALUATED;
    }

    public void setScriptResult(ScriptResultInfo scriptResult)
    {
        this.scriptResult = scriptResult;
    }

    public ScriptResultInfo getScriptResult()
    {
        return scriptResult;
    }

}
