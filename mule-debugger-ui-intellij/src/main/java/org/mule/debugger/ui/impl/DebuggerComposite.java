/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.impl;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

import javax.swing.*;
import java.awt.*;

public class DebuggerComposite extends SimpleToolWindowPanel {

    private DefaultActionGroup toolBar;
    private MuleDebuggerPayloadView payloadComposite;
    private MuleDebuggerPropertiesView propertiesView;


    public DebuggerComposite() {
        super(false, true);
        createControl();
    }


    protected void createControl() {
        this.propertiesView = new MuleDebuggerPropertiesView();
        this.payloadComposite = new MuleDebuggerPayloadView();
        this.setLayout(new BorderLayout());
        JSplitPane debuggerTabs = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        debuggerTabs.add(payloadComposite);
        debuggerTabs.add(propertiesView);
        JPanel toolBarPanel = new JPanel(new GridLayout());
        toolBar = new DefaultActionGroup();
        toolBarPanel.add(
                ActionManager.getInstance().createActionToolbar("MuleDebugger", toolBar, false).getComponent());
        toolBarPanel.setBackground(Color.GRAY);

        this.setToolbar(toolBarPanel);
        this.setContent(debuggerTabs);


    }

    public DefaultActionGroup getToolBar() {
        return toolBar;
    }

    public MuleDebuggerPayloadView getPayloadComposite() {
        return payloadComposite;
    }

    public MuleDebuggerPropertiesView getPropertiesView() {
        return propertiesView;
    }
}
