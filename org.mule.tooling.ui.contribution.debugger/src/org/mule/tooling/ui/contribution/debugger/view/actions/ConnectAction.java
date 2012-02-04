
package org.mule.tooling.ui.contribution.debugger.view.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DebuggerConnection;
import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.controller.events.ClientInitializedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.ConnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DisconnectedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IConnectionPropertiesEditor;
import org.mule.tooling.ui.contribution.debugger.view.impl.DebuggerImages;

public class ConnectAction extends Action
{
    private IConnectionPropertiesEditor propertiesProvider;
    private DebuggerClient client;
    private final EventBus bus;
    private final IDebuggerResponseCallback callback;

    public ConnectAction(IConnectionPropertiesEditor propertiesProvider,
                         EventBus bus,
                         IDebuggerResponseCallback callback)
    {
        super();
        this.bus = bus;
        this.propertiesProvider = propertiesProvider;
        this.callback = callback;
        setConnectState();
        this.bus.registerListener(DebuggerEventType.CONNECTED, new IEventHandler<ConnectedEvent>()
        {

            @Override
            public void onEvent(ConnectedEvent event)
            {
                setDisconnectState();
            }
        });

        this.bus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>()
        {

            @Override
            public void onEvent(DisconnectedEvent event)
            {
                setConnectState();
            }
        });

    }

    protected void setConnectState()
    {
        setImageDescriptor(DebuggerImages.IMG_DISCONNECT);
        setToolTipText("Connect");
        setText("Connect");
        this.setEnabled(true);
    }

    protected void setDisconnectState()
    {
        setImageDescriptor(DebuggerImages.IMG_CONNECT);
        setToolTipText("Disconnect");
        setText("Disconnect");
        this.setEnabled(true);
    }

    @Override
    public void run()
    {
        if (client == null)
        {
            client = new DebuggerClient(new DebuggerConnection(propertiesProvider.getURL(),
                Integer.parseInt(propertiesProvider.getPort())));
            try
            {
                client.start(callback);
                bus.fireEvent(new ClientInitializedEvent(client));
                this.setEnabled(false);
            }
            catch (IOException e)
            {

            }
        }
        else
        {
            client.disconnect();
            client = null;
        }
    }

}
