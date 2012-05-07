
package org.mule.debugger.ui.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus
{
    private Map<IEventType, List<IEventHandler>> listeners;

    public EventBus()
    {
        listeners = new HashMap<IEventType, List<IEventHandler>>();
    }

    public <T extends IEventHandler> void registerListener(IEventType type, T listener)
    {
        List<IEventHandler> handlers = listeners.get(type);
        if (handlers == null)
        {
            handlers = new ArrayList<IEventHandler>();
            listeners.put(type, handlers);
        }
        handlers.add(listener);
    }

    public void fireEvent(IEvent event)
    {
        IEventType associatedType = event.getAssociatedType();
        List<? extends IEventHandler> handlers = listeners.get(associatedType);
        if (handlers != null)
        {
            for (IEventHandler iEventHandler : handlers)
            {
                iEventHandler.onEvent(event);
            }
        }
    }

    public void cleanAllListeners()
    {
        listeners.clear();
    }
}
