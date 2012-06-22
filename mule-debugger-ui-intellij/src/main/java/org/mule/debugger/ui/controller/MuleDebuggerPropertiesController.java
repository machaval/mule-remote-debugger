package org.mule.debugger.ui.controller;

import com.intellij.openapi.project.Project;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.events.*;
import org.mule.debugger.ui.view.IMuleDebuggerProperties;

public class MuleDebuggerPropertiesController {

    private IMuleDebuggerProperties propertiesView;
    private EventBus bus;
    private Project project;
    private AbstractObjectFieldDefinitionController inboundController;
    private AbstractObjectFieldDefinitionController session;
    private AbstractObjectFieldDefinitionController flowVarControl;
    private AbstractObjectFieldDefinitionController outgoingControl;

    public MuleDebuggerPropertiesController(IMuleDebuggerProperties propertiesView, EventBus bus, Project project) {
        super();
        this.propertiesView = propertiesView;
        this.bus = bus;
        this.project = project;
        bind();
    }

    protected void bind() {

        inboundController = new AbstractObjectFieldDefinitionController(propertiesView.getInboudProperties(), bus, project) {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event) {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setModel(
                        ObjectFieldDefinitionTreeTableModel.createTreeNode(muleMessageInfo.getInboundProperties().values()));

            }
        };

        session = new AbstractObjectFieldDefinitionController(propertiesView.getInvocationProperties(), bus, project) {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event) {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setModel(
                        ObjectFieldDefinitionTreeTableModel.createTreeNode(muleMessageInfo.getInvocationProperties().values()));

            }
        };

        flowVarControl = new AbstractObjectFieldDefinitionController(propertiesView.getSessionProperties(), bus, project) {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event) {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();

                getEditor().getPayloadTreeViewer().setModel(
                        ObjectFieldDefinitionTreeTableModel.createTreeNode(muleMessageInfo.getSessionProperties().values()));

            }
        };

        outgoingControl = new AbstractObjectFieldDefinitionController(propertiesView.getOutboundProperties(), bus, project) {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event) {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setModel(
                        ObjectFieldDefinitionTreeTableModel.createTreeNode(muleMessageInfo.getOutboundProperties().values()));

            }
        };
    }


}
