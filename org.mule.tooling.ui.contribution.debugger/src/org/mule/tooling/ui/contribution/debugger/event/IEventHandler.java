
package org.mule.tooling.ui.contribution.debugger.event;

public interface IEventHandler<T extends IEvent>
{
    void onEvent(T event);
}
