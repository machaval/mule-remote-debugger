package org.mule.debugger.ui.events;

import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventType;

public class DebuggerErrorEvent implements IEvent {
    private String errorMessage;

    public DebuggerErrorEvent(String errorMessage) {
        super();
        this.setErrorMessage(errorMessage);
    }

    @Override
    public IEventType getAssociatedType() {

        return DebuggerEventType.ERROR;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
