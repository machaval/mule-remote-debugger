package org.mule.debugger.ui.impl;

import com.intellij.ui.TabbedPaneImpl;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.table.JBTable;
import org.mule.debugger.ui.view.IMuleDebuggerProperties;

import javax.swing.*;
import java.awt.*;

public class MuleDebuggerPropertiesView extends JPanel implements IMuleDebuggerProperties {

    private static final String[] COLUMN_PROPERTIES = new String[]{"Name", "Value"};
    private JBTable inboundProperties;
    private JBTable invocationProperties;
    private JBTable sessionProperties;

    public MuleDebuggerPropertiesView() {

        JBTabbedPane debuggerTabs = new JBTabbedPane(SwingConstants.TOP);

        this.inboundProperties = new JBTable();
        this.invocationProperties = new JBTable();
        this.sessionProperties = new JBTable();

        debuggerTabs.add("Inbound", inboundProperties);
        debuggerTabs.add("Invocation", invocationProperties);
        debuggerTabs.add("Invocation", sessionProperties);
        this.setLayout(new GridLayout());
        this.add(debuggerTabs);
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
}
