package org.mule.debugger.ui.events;

import org.mule.debugger.response.ScriptResultInfo;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventType;

public class ScriptEvaluatedEvent implements IEvent {
    private ScriptResultInfo scriptResult;

    public ScriptEvaluatedEvent(ScriptResultInfo scriptResult) {
        super();
        this.setScriptResult(scriptResult);
    }

    @Override
    public IEventType getAssociatedType() {

        return DebuggerEventType.SCRIPT_EVALUATED;
    }

    public void setScriptResult(ScriptResultInfo scriptResult) {
        this.scriptResult = scriptResult;
    }

    public ScriptResultInfo getScriptResult() {
        return scriptResult;
    }

}
