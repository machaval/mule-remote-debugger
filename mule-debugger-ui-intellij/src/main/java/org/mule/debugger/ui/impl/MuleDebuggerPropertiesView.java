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


    private ObjectFieldDefinitionComposite inboudProperties;
    private ObjectFieldDefinitionComposite invocationProperties;
    private ObjectFieldDefinitionComposite sessionProperties;
    private ObjectFieldDefinitionComposite outboundProperties;

    public MuleDebuggerPropertiesView() {
        createControl();

    }

    protected void createControl() {
        this.setLayout(new BorderLayout(1, 3));

        setInboudProperties(createTreeTable());
        setInvocationProperties(createTreeTable());
        setSessionProperties(createTreeTable());
        setOutboundProperties(createTreeTable());

        JBTabbedPane debuggerTabs = new JBTabbedPane(SwingConstants.TOP);
        debuggerTabs.add("Inbound", getInboudProperties());
        debuggerTabs.add("Flow", getInvocationProperties());
        debuggerTabs.add("Session", getSessionProperties());
        debuggerTabs.add("Outbound", getOutboundProperties());
        this.add(debuggerTabs, BorderLayout.CENTER);

    }


    /**
     * Create the Table
     */
    private ObjectFieldDefinitionComposite createTreeTable() {
        return new ObjectFieldDefinitionComposite();
    }

    public void setInboudProperties(ObjectFieldDefinitionComposite inboudProperties) {
        this.inboudProperties = inboudProperties;
    }

    public void setInvocationProperties(ObjectFieldDefinitionComposite invocationProperties) {
        this.invocationProperties = invocationProperties;
    }

    public void setSessionProperties(ObjectFieldDefinitionComposite sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    public void setOutboundProperties(ObjectFieldDefinitionComposite outboundProperties) {
        this.outboundProperties = outboundProperties;
    }

    public ObjectFieldDefinitionComposite getInboudProperties() {
        return inboudProperties;
    }

    public ObjectFieldDefinitionComposite getInvocationProperties() {
        return invocationProperties;
    }

    public ObjectFieldDefinitionComposite getSessionProperties() {
        return sessionProperties;
    }

    public ObjectFieldDefinitionComposite getOutboundProperties() {
        return outboundProperties;
    }
}
