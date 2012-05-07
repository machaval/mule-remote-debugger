package org.mule.debugger.ui.events;

import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventType;

public class DisconnectedEvent implements IEvent {

    @Override
    public IEventType getAssociatedType() {
        return DebuggerEventType.DISCONNECTED;
    }

}
