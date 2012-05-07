package org.mule.debugger.ui.events;

import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventType;

public class DebuggerExceptionEvent implements IEvent {

    private RemoteDebugException exception;

    public DebuggerExceptionEvent(RemoteDebugException exception) {
        super();
        this.setException(exception);
    }

    @Override
    public IEventType getAssociatedType() {
        return DebuggerEventType.EXCEPTION;
    }

    public void setException(RemoteDebugException exception) {
        this.exception = exception;
    }

    public RemoteDebugException getException() {
        return exception;
    }

}
