
package org.mule.tooling.ui.contribution.debugger.controller;

import org.mule.tooling.ui.contribution.debugger.event.IEventType;

public enum DebuggerEventType implements IEventType
{
    CLIENT_INITIALIZED,CONNECTED, DISCONNECTED, ERROR, EXCEPTION, MULE_MESSAGE_ARRIVED, SCRIPT_EVALUATED, WAITING;
}
