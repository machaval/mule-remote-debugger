/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.component;

import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.mule.debugger.ui.controller.MuleDebuggerPayloadController;
import org.mule.debugger.ui.controller.MuleDebuggerPropertiesController;
import org.mule.debugger.ui.controller.ScriptEvaluationController;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.impl.MuleDebuggerFactory;

public class DebuggerWindow implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        EventBus eventBus = new EventBus();

        RunnerLayoutUi runnerLayoutUi = RunnerLayoutUi.Factory.getInstance(project).create(
                "Mule-Debugger", "MuleESB Debugger", project.getName(), project);
        MuleDebuggerFactory comp = new MuleDebuggerFactory(runnerLayoutUi, eventBus);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(comp.createControl(), "Payload", false);

        new MuleDebuggerPayloadController(comp.getPayloadComposite(), eventBus, project);
        new MuleDebuggerPropertiesController(comp.getPropertiesView(), eventBus, project);
        new ScriptEvaluationController(comp.getScriptView(), eventBus, project);


        toolWindow.getContentManager().addContent(content);
        //toolWindow.getContentManager().addContent(prop);
    }
}
