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
import com.intellij.openapi.ui.TitlePanel;

import javax.swing.*;
import java.awt.*;

public class DebuggerComposite extends SimpleToolWindowPanel {

    private DefaultActionGroup toolBar;
    private MuleDebuggerPayloadView payloadComposite;
    private MuleDebuggerPropertiesView propertiesView;
    private ScriptEvaluationComposite scriptView;


    public DebuggerComposite() {
        super(false, true);
        createControl();
    }


    protected void createControl() {
        this.propertiesView = new MuleDebuggerPropertiesView();
        this.payloadComposite = new MuleDebuggerPayloadView();
        this.scriptView = new ScriptEvaluationComposite();

        this.setLayout(new BorderLayout());

        TitlePanel payloadContainer = new TitlePanel("Message","Message elements");
        payloadContainer.add(payloadComposite);

        /*TitlePanel propertiesContainer = new TitlePanel("Properties","Message properties");
        propertiesContainer.add(propertiesView);*/

        TitlePanel scriptContainer = new TitlePanel("Evaluate","Evaluate script");
        scriptContainer.add(scriptView);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane1.setBorder(BorderFactory.createEmptyBorder());
        splitPane1.add(payloadContainer);
        splitPane1.add(scriptContainer);
        splitPane.add(splitPane1);
        splitPane.add(propertiesView);
        JPanel toolBarPanel = new JPanel(new GridLayout());
        toolBar = new DefaultActionGroup();
        toolBarPanel.add(
                ActionManager.getInstance().createActionToolbar("MuleDebugger", toolBar, false).getComponent());
        toolBarPanel.setBackground(Color.GRAY);

        this.setToolbar(toolBarPanel);
        this.setContent(splitPane);


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

    public ScriptEvaluationComposite getScriptView() {
        return scriptView;
    }
}
