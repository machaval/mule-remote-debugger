
package org.mule.tooling.ui.contribution.debugger.controller.events;

import org.mule.debugger.exception.RemoteDebugException;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.event.IEvent;
import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public class DebuggerExceptionEvent implements IEvent
{

    private RemoteDebugException exception;

    public DebuggerExceptionEvent(RemoteDebugException exception)
    {
        super();
        this.setException(exception);
    }

    @Override
    public IEventType getAssociatedType()
    {
        return DebuggerEventType.CONNECTED;
    }

    public void setException(RemoteDebugException exception)
    {
        this.exception = exception;
    }

    public RemoteDebugException getException()
    {
        return exception;
    }

}
