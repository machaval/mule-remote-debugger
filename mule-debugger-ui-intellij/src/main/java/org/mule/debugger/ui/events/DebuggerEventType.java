
package org.mule.debugger.ui.events;

import org.mule.debugger.ui.event.IEventType;

public enum DebuggerEventType implements IEventType
{
    CLIENT_INITIALIZED,CONNECTED, DISCONNECTED, ERROR, EXCEPTION, MULE_MESSAGE_ARRIVED, SCRIPT_EVALUATED, WAITING;
}
