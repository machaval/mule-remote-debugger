package org.mule.debugger.ui.impl;

import com.intellij.ui.*;
import com.intellij.ui.border.IdeaTitledBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.table.JBTable;
import org.mule.debugger.ui.view.IMuleDebuggerProperties;

import javax.swing.*;
import java.awt.*;

public class MuleDebuggerPropertiesView extends JPanel implements IMuleDebuggerProperties {


    private JBTable inboundProperties;
    private JBTable invocationProperties;
    private JBTable sessionProperties;
    private JBTable outboundProperties;
    private AnActionButtonRunnable addInvocation;
    private AnActionButtonRunnable removeInvocation;
    private AnActionButtonRunnable addSession;
    private AnActionButtonRunnable removeSession;
    private AnActionButtonRunnable addOutbound;
    private AnActionButtonRunnable removeOutbound;

    public void setAddInvocation(AnActionButtonRunnable addInvocation) {
        this.addInvocation = addInvocation;
    }

    public void setRemoveInvocation(AnActionButtonRunnable removeInvocation) {
        this.removeInvocation = removeInvocation;
    }

    public void setAddSession(AnActionButtonRunnable addSession) {
        this.addSession = addSession;
    }

    public void setRemoveSession(AnActionButtonRunnable removeSession) {
        this.removeSession = removeSession;
    }

    public void setAddOutbound(AnActionButtonRunnable addOutbound) {
        this.addOutbound = addOutbound;
    }

    public void setRemoveOutbound(AnActionButtonRunnable removeOutbound) {
        this.removeOutbound = removeOutbound;
    }

    public MuleDebuggerPropertiesView() {

        JBTabbedPane debuggerTabs = new JBTabbedPane(SwingConstants.TOP);

        this.inboundProperties = new JBTable();
        this.invocationProperties = new JBTable();
        this.sessionProperties = new JBTable();
        this.outboundProperties = new JBTable();
        final ToolbarDecorator inboundPropertiesDecorated = ToolbarDecorator.createDecorator(inboundProperties);


        final ToolbarDecorator invocationPropertiesDecorated = ToolbarDecorator.createDecorator(invocationProperties);
        invocationPropertiesDecorated.setAddAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                addInvocation.run(anActionButton);
            }
        }).setRemoveAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                removeInvocation.run(anActionButton);
            }
        });

        final ToolbarDecorator sessionPropertiesDecorated = ToolbarDecorator.createDecorator(sessionProperties);
        sessionPropertiesDecorated.setAddAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                addSession.run(anActionButton);
            }
        }).setRemoveAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                removeSession.run(anActionButton);
            }
        });

        final ToolbarDecorator outboundPropertiesDecorated = ToolbarDecorator.createDecorator(outboundProperties);
        outboundPropertiesDecorated.setAddAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                addOutbound.run(anActionButton);
            }
        }).setRemoveAction(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                removeOutbound.run(anActionButton);
            }
        });


        debuggerTabs.add("Inbound", inboundPropertiesDecorated.createPanel());
        debuggerTabs.add("Flow", invocationPropertiesDecorated.createPanel());
        debuggerTabs.add("Session", sessionPropertiesDecorated.createPanel());
        debuggerTabs.add("Outbound", outboundPropertiesDecorated.createPanel());
        this.setLayout(new GridLayout());

        this.add(debuggerTabs);
        // this.setBorder(IdeBorderFactory.createTitledBorder("Properties"));
    }


    @Override
    public JTable getInvocationProperties() {
        return invocationProperties;
    }

    @Override
    public JTable getSessionProperties() {
        return sessionProperties;
    }

    @Override
    public JTable getInboundProperties() {
        return inboundProperties;
    }

    @Override
    public JTable getOutboundProperties() {
        return outboundProperties;
    }
}
