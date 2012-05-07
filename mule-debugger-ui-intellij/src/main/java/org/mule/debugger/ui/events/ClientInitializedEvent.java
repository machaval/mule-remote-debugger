package org.mule.debugger.ui.events;

import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventType;

public class ClientInitializedEvent implements IEvent {

    private DebuggerClient client;

    public ClientInitializedEvent(DebuggerClient client) {
        super();
        this.setClient(client);
    }

    @Override
    public IEventType getAssociatedType() {

        return DebuggerEventType.CLIENT_INITIALIZED;
    }

    public void setClient(DebuggerClient client) {
        this.client = client;
    }

    public DebuggerClient getClient() {
        return client;
    }

}
