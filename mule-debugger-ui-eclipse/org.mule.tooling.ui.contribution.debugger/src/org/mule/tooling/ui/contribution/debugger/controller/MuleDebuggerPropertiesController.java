
package org.mule.tooling.ui.contribution.debugger.controller;

import org.mule.debugger.response.MuleMessageInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerProperties;

public class MuleDebuggerPropertiesController
{

    private IMuleDebuggerProperties propertiesView;
    private EventBus bus;

    public MuleDebuggerPropertiesController(IMuleDebuggerProperties propertiesView, EventBus bus)
    {
        super();
        this.propertiesView = propertiesView;
        this.bus = bus;
        bind();
    }

    protected void bind()
    {

        new AbstractObjectFieldDefinitionController(propertiesView.getInboudProperties(), bus)
        {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event)
            {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setInput(
                    ObjectTreeNodeBuilder.createTreeNode(muleMessageInfo.getInboundProperties().values()));

            }
        };
        
        new AbstractObjectFieldDefinitionController(propertiesView.getInvocationProperties(), bus)
        {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event)
            {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setInput(
                    ObjectTreeNodeBuilder.createTreeNode(muleMessageInfo.getInvocationProperties().values()));

            }
        };
        
        new AbstractObjectFieldDefinitionController(propertiesView.getSessionProperties(), bus)
        {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event)
            {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setInput(
                    ObjectTreeNodeBuilder.createTreeNode(muleMessageInfo.getSessionProperties().values()));

            }
        };
        
        new AbstractObjectFieldDefinitionController(propertiesView.getOutboundProperties(), bus)
        {

            @Override
            protected void populateData(NewMuleMessageArrivedEvent event)
            {
                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                getEditor().getPayloadTreeViewer().setInput(
                    ObjectTreeNodeBuilder.createTreeNode(muleMessageInfo.getOutboundProperties().values()));

            }
        };
    }

}
