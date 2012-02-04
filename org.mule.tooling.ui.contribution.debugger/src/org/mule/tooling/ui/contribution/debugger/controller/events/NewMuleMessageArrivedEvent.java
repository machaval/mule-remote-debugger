
package org.mule.tooling.ui.contribution.debugger.controller.events;

import org.mule.debugger.response.MuleMessageInfo;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.event.IEvent;
import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public class NewMuleMessageArrivedEvent implements IEvent
{
    private MuleMessageInfo muleMessageInfo;

    public NewMuleMessageArrivedEvent(MuleMessageInfo muleMessageInfo)
    {
        super();
        this.setMuleMessageInfo(muleMessageInfo);
    }

    @Override
    public IEventType getAssociatedType()
    {

        return DebuggerEventType.MULE_MESSAGE_ARRIVED;
    }

    public void setMuleMessageInfo(MuleMessageInfo muleMessageInfo)
    {
        this.muleMessageInfo = muleMessageInfo;
    }

    public MuleMessageInfo getMuleMessageInfo()
    {
        return muleMessageInfo;
    }

}
