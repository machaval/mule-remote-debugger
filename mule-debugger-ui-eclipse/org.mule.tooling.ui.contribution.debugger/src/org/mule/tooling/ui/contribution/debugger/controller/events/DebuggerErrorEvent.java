
package org.mule.tooling.ui.contribution.debugger.controller.events;

import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.event.IEvent;
import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public class DebuggerErrorEvent implements IEvent
{
    private String errorMessage;

    public DebuggerErrorEvent(String errorMessage)
    {
        super();
        this.setErrorMessage(errorMessage);
    }

    @Override
    public IEventType getAssociatedType()
    {

        return DebuggerEventType.ERROR;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
