package org.mule.debugger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.actionSystem.Presentation;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.events.*;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ResumeAction extends AnAction {

    private DebuggerClient client;
    private boolean messageArrived = false;
    private EventBus eventBus;

    public ResumeAction(EventBus eventBus) {
        super();
        this.eventBus = eventBus;

        this.setClient(client);

        getTemplatePresentation().setEnabled(false);
        eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(NewMuleMessageArrivedEvent event) {
                        messageArrived = true;
                    }

                });

        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
                new IEventHandler<ClientInitializedEvent>() {

                    @Override
                    public void onEvent(ClientInitializedEvent event) {
                        setClient(event.getClient());
                    }
                });



        this.eventBus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>() {

            @Override
            public void onEvent(DisconnectedEvent event) {
                messageArrived = false;
            }
        });

        ImageIcon imageIcon = new ImageIcon(ConnectAction.class.getResource("/org/mule/icons/resume.png"));
        getTemplatePresentation().setIcon(imageIcon);
        setShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0)));
        getTemplatePresentation().setDescription("Resume");
        getTemplatePresentation().setText("Resume");

    }

    public void setClient(DebuggerClient client) {
        this.client = client;
    }

    public DebuggerClient getClient() {
        return client;
    }


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        messageArrived = false;
        client.resume();
        this.eventBus.fireEvent(new ResumeEvent());

    }

    @Override
    public void update(AnActionEvent e) {
        if (messageArrived) {
            e.getPresentation().setEnabled(true);
        } else {
            e.getPresentation().setEnabled(false);
        }
    }
}
