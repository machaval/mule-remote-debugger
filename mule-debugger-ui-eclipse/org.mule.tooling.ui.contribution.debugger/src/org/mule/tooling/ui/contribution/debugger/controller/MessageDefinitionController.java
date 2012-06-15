
package org.mule.tooling.ui.contribution.debugger.controller;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.mule.debugger.response.MessageProcessorInfo;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.view.IObjectFieldDefinitionEditor;

public class MessageDefinitionController extends AbstractObjectFieldDefinitionController
{

    public MessageDefinitionController(IObjectFieldDefinitionEditor editor, EventBus eventBus)
    {
        super(editor,eventBus);
        
        
    
    }

    @Override
    protected void populateData(final NewMuleMessageArrivedEvent event)
    {
        final MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
        final List<ObjectFieldDefinition> emptyList = Collections.emptyList();
        final MessageProcessorInfo messageProcessorInfo = muleMessageInfo.getMessageProcessorInfo();
        final String messageProcessorClassName = messageProcessorInfo.getClassName();
        final String messageProcessorName = messageProcessorInfo.getAnnotations().get(
            "{http://www.mulesoft.org/schema/mule/documentation}name");
        final ObjectFieldDefinition encoding = new ObjectFieldDefinition("Encoding", String.class.getName(),
            muleMessageInfo.getEncoding(), emptyList);
        final ObjectFieldDefinition uniqueId = new ObjectFieldDefinition("Id", String.class.getName(),
            muleMessageInfo.getUniqueId(), emptyList);

        final ObjectFieldDefinition currentProcessor = new ObjectFieldDefinition("Message Processor",
            messageProcessorClassName, messageProcessorName, emptyList);

        ObjectFieldDefinition payloadDef = muleMessageInfo.getPayloadDefinition();
        ObjectFieldDefinition excPayloadDef = muleMessageInfo.getExceptionPayloadDefinition();

        getEditor().getPayloadTreeViewer().setInput(
            new TreeNode[]{ObjectTreeNodeBuilder.createTreeNode(currentProcessor),
                ObjectTreeNodeBuilder.createTreeNode(payloadDef),
                ObjectTreeNodeBuilder.createTreeNode(excPayloadDef),
                ObjectTreeNodeBuilder.createTreeNode(uniqueId),
                ObjectTreeNodeBuilder.createTreeNode(encoding)});
    }

}
