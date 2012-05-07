package org.mule.debugger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DebuggerConnection;
import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.ClientInitializedEvent;
import org.mule.debugger.ui.events.ConnectedEvent;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.DisconnectedEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ConnectAction extends AnAction {
    // private IConnectionPropertiesEditor propertiesProvider;
    private DebuggerClient client;
    private final EventBus bus;
    private final IDebuggerResponseCallback callback;
    private String host = "localhost";
    private int port = 6666;

    public ConnectAction(EventBus bus, IDebuggerResponseCallback callback) {
        super();
        this.bus = bus;

        this.callback = callback;

        this.bus.registerListener(DebuggerEventType.CONNECTED, new IEventHandler<ConnectedEvent>() {
            @Override
            public void onEvent(ConnectedEvent event) {
                setDisconnectStatus();

            }
        });

        this.bus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>() {

            @Override
            public void onEvent(DisconnectedEvent event) {
                setConnectStatus();
                client.disconnect();
                client = null;
            }
        });

        setConnectStatus();


    }

    private void setConnectStatus() {
        ImageIcon imageIcon = new ImageIcon(ConnectAction.class.getResource("/org/mule/icons/connect.png"));
        getTemplatePresentation().setIcon(imageIcon);
        getTemplatePresentation().setDescription("Connect");
        setShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke('c', InputEvent.CTRL_MASK)));
    }

    private void setDisconnectStatus() {
        ImageIcon imageIcon = new ImageIcon(ConnectAction.class.getResource("/org/mule/icons/disconnect.png"));
        getTemplatePresentation().setIcon(imageIcon);
        getTemplatePresentation().setDescription("Disconnect");
        setShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.CTRL_MASK)));

    }


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if (client == null) {

            client = new DebuggerClient(new DebuggerConnection(host, port));
            try {
                client.start(callback);
                bus.fireEvent(new ClientInitializedEvent(client));
                getTemplatePresentation().setEnabled(false);
            } catch (IOException ex) {
                client = null;
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            try {
                client.exit();

            } finally {
                client = null;
                setConnectStatus();
            }
        }
    }
}
