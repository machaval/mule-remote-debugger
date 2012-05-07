
package org.mule.debugger.ui.event;

public interface IEventHandler<T extends IEvent>
{
    void onEvent(T event);
}
