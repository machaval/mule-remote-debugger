
package org.mule.tooling.ui.contribution.debugger.controller;

import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.ConnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DebuggerErrorEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DebuggerExceptionEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DisconnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.ScriptEvaluatedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.ResumeEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;

public class DebuggerResponseCallback implements IDebuggerResponseCallback
{

    private EventBus eventBus;
    

    public DebuggerResponseCallback(EventBus eventBus)
    {
        super();
        this.eventBus = eventBus;
       
    }

    @Override
    public void onConnected()
    {
        this.eventBus.fireEvent(new ConnectedEvent());
    }

    @Override
    public void onError(String errorMessage)
    {
        this.eventBus.fireEvent(new DebuggerErrorEvent(errorMessage));
    }

    @Override
    public void onException(RemoteDebugException exception)
    {
        this.eventBus.fireEvent(new DebuggerExceptionEvent(exception));
    }

    @Override
    public void onExit()
    {
        this.eventBus.fireEvent(new DisconnectedEvent());
        
    }

    @Override
    public void onMuleMessageArrived(MuleMessageInfo muleMessageInfo)
    {
        this.eventBus.fireEvent(new NewMuleMessageArrivedEvent(muleMessageInfo));
    }

    @Override
    public void onScriptEvaluation(ScriptResultInfo scriptResult)
    {
        this.eventBus.fireEvent(new ScriptEvaluatedEvent(scriptResult));
    }

    @Override
    public void onResume()
    {
        this.eventBus.fireEvent(new ResumeEvent());
    }

}
