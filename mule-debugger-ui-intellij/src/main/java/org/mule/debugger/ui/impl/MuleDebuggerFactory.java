/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.impl;

import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.execution.ui.layout.PlaceInGrid;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.ui.content.Content;
import com.intellij.xdebugger.impl.ui.XDebuggerUIConstants;
import org.jetbrains.annotations.NotNull;
import org.mule.debugger.ui.actions.ConnectAction;
import org.mule.debugger.ui.actions.ResumeAction;
import org.mule.debugger.ui.actions.StepOverAction;
import org.mule.debugger.ui.controller.DebuggerResponseCallback;
import org.mule.debugger.ui.event.EventBus;

import javax.swing.*;

public class MuleDebuggerFactory {


    private ObjectFieldDefinitionComposite payloadComposite;
    private MuleDebuggerPropertiesView propertiesView;
    private ScriptEvaluationComposite scriptView;
    //This is for

    @NotNull
    protected final RunnerLayoutUi myUi;
    private EventBus eventBus;


    public MuleDebuggerFactory(@NotNull RunnerLayoutUi myUi, EventBus eventBus) {
        this.myUi = myUi;
        this.eventBus = eventBus;
    }


    public JComponent createControl() {
        this.propertiesView = new MuleDebuggerPropertiesView();
        this.payloadComposite = new ObjectFieldDefinitionComposite();
        this.scriptView = new ScriptEvaluationComposite();



        Content properties = myUi.createContent("Properties", propertiesView, "Properties",
                XDebuggerUIConstants.VARIABLES_TAB_ICON, null);
        properties.setCloseable(false);

        myUi.addContent(properties, 0, PlaceInGrid.center, false);
        Content payload = myUi.createContent("Message", payloadComposite, "Message",
                XDebuggerUIConstants.CONSOLE_TAB_ICON, payloadComposite.getPayloadTreeViewer());
        payload.setCloseable(false);
        myUi.addContent(payload, 0, PlaceInGrid.left, false);

        Content script = myUi.createContent("Script", scriptView, "Script",
                XDebuggerUIConstants.WATCHES_TAB_ICON, scriptView.getScript());
        script.setCloseable(false);
        myUi.addContent(script, 0, PlaceInGrid.right, false);


        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(new ConnectAction(eventBus, new DebuggerResponseCallback(eventBus)));
        group.add(new StepOverAction(eventBus));
        group.add(new ResumeAction(eventBus));
        myUi.getOptions().setLeftToolbar(group, ActionPlaces.DEBUGGER_TOOLBAR);
        return myUi.getComponent();


    }



    public ObjectFieldDefinitionComposite getPayloadComposite() {
        return payloadComposite;
    }

    public MuleDebuggerPropertiesView getPropertiesView() {
        return propertiesView;
    }

    public ScriptEvaluationComposite getScriptView() {
        return scriptView;
    }


}
