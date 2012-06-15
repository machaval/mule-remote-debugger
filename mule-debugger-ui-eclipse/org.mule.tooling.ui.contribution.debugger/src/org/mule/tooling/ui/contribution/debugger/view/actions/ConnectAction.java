
package org.mule.tooling.ui.contribution.debugger.view.actions;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
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
    // private IConnectionPropertiesEditor propertiesProvider;
    private DebuggerClient client;
    private final EventBus bus;
    private final IDebuggerResponseCallback callback;
    private static String DEFAULT_HOST = "localhost";
    private static int DEFAULT_PORT = 6666;
    private IConnectionPropertiesEditor connectionProperties;

    public ConnectAction(EventBus bus, IDebuggerResponseCallback callback)
    {
        super();
        this.bus = bus;

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
                client.disconnect();
                client = null;

            }
        });

    }

    protected void setConnectState()
    {
        setImageDescriptor(DebuggerImages.IMG_DISCONNECT);
        setToolTipText("Connect");
        setText("Connect");
        setEnabled(true);

    }

    protected void setDisconnectState()
    {
        setImageDescriptor(DebuggerImages.IMG_CONNECT);
        setToolTipText("Disconnect");
        setText("Disconnect");
        setEnabled(true);

    }

    @Override
    public void run()
    {
        if (client == null)
        {

            client = new DebuggerClient(new DebuggerConnection(getHost(), getPort()));
            try
            {
                client.start(callback);
                bus.fireEvent(new ClientInitializedEvent(client));
                setEnabled(false);
            }
            catch (IOException e)
            {
                
                client = null;
                MessageDialog.openError(Display.getCurrent().getActiveShell(),
                    "Error while trying to connect", e.getMessage() + ".\nVerify mule is up and running at '"
                                                     + getHost() + "' and debugger is running at port "
                                                     + getPort());

            }
        }
        else
        {

            client.exit();

        }
    }

    public String getHost()
    {
        return getConnectionProperties() != null ? getConnectionProperties().getURL() : DEFAULT_HOST;
    }

    public int getPort()
    {
        return getConnectionProperties() != null ? getConnectionProperties().getPort() : DEFAULT_PORT;
    }

    public void setConnectionProperties(IConnectionPropertiesEditor connectionProperties)
    {
        this.connectionProperties = connectionProperties;
    }

    public IConnectionPropertiesEditor getConnectionProperties()
    {
        return connectionProperties;
    }
}
