package org.mule.debugger.ui.component;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.mule.debugger.ui.event.EventBus;

/**
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
public class DebuggerComponent implements ProjectComponent {

    private EventBus bus;
    private Project project;

    public DebuggerComponent(Project project) {
        this.project = project;

    }

    public void initComponent() {
        bus = new EventBus();
    }

    public void disposeComponent() {

    }

    @NotNull
    public String getComponentName() {
        return "DebuggerComponent";
    }

    public void projectOpened() {
        // called when project is opened
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
