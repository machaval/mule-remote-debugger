/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.component;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.mule.debugger.ui.actions.ConnectAction;
import org.mule.debugger.ui.actions.ResumeAction;
import org.mule.debugger.ui.actions.StepOverAction;
import org.mule.debugger.ui.controller.DebuggerResponseCallback;
import org.mule.debugger.ui.controller.MuleDebuggerPayloadController;
import org.mule.debugger.ui.controller.MuleDebuggerPropertiesController;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.impl.DebuggerComposite;
import org.mule.debugger.ui.impl.MuleDebuggerPropertiesView;

public class DebuggerWindow implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        EventBus eventBus = new EventBus();
        DebuggerComposite comp = new DebuggerComposite();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        comp.getToolBar().add(new ConnectAction(eventBus, new DebuggerResponseCallback(eventBus)));
        comp.getToolBar().add(new StepOverAction(eventBus));
        comp.getToolBar().add(new ResumeAction(eventBus));


        new MuleDebuggerPayloadController(comp.getPayloadComposite(), eventBus);
        new MuleDebuggerPropertiesController(comp.getPropertiesView(), eventBus);
        Content content = contentFactory.createContent(comp, "Payload", false);


        toolWindow.getContentManager().addContent(content);
        //toolWindow.getContentManager().addContent(prop);
    }
}
