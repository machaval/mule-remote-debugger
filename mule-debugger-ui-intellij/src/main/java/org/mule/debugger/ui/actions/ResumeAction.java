package org.mule.debugger.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.actionSystem.Presentation;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.ClientInitializedEvent;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ResumeAction extends AnAction {

    private DebuggerClient client;

    public ResumeAction(EventBus eventBus) {
        super();

        this.setClient(client);

        getTemplatePresentation().setEnabled(false);
        eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(NewMuleMessageArrivedEvent event) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                getTemplatePresentation().setEnabled(true);
                            }
                        });

                    }

                });

        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
                new IEventHandler<ClientInitializedEvent>() {

                    @Override
                    public void onEvent(ClientInitializedEvent event) {
                        setClient(event.getClient());
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
        getTemplatePresentation().setEnabled(false);
        client.resume();
    }
}
