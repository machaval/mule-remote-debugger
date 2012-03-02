
package org.mule.tooling.ui.contribution.debugger.controller.events;

import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.event.IEvent;
import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public class ConnectedEvent implements IEvent
{

    @Override
    public IEventType getAssociatedType()
    {
        return DebuggerEventType.CONNECTED;
    }

}
