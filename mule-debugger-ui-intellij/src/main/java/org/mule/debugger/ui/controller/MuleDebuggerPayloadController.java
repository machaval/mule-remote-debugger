package org.mule.debugger.ui.controller;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import org.mule.debugger.response.MessageProcessorInfo;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;
import org.mule.debugger.ui.view.IObjectFieldDefinitionEditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import java.util.List;

public class MuleDebuggerPayloadController extends AbstractObjectFieldDefinitionController {


    public MuleDebuggerPayloadController(IObjectFieldDefinitionEditor editor, EventBus eventBus,Project project) {
        super(editor, eventBus, project);


    }

    @Override
    protected void populateData(NewMuleMessageArrivedEvent event) {
        final TreeTable payloadTreeViewer = getEditor().getPayloadTreeViewer();
        final MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
        final List<ObjectFieldDefinition> emptyList = Collections.emptyList();
        final MessageProcessorInfo messageProcessorInfo = muleMessageInfo.getMessageProcessorInfo();
        final String messageProcessorClassName = messageProcessorInfo.getClassName();
        String messageProcessorName = messageProcessorInfo.getAnnotations().get(
                "{http://www.mulesoft.org/schema/mule/documentation}name");
        if (messageProcessorName == null) {
            int indexOfDot = messageProcessorClassName.lastIndexOf(".");
            if (indexOfDot < 0) {
                indexOfDot = 0;
            } else {
                indexOfDot++;
            }
            messageProcessorName = messageProcessorClassName.substring(indexOfDot);
        }
        final ObjectFieldDefinition encoding = new ObjectFieldDefinition("Encoding", String.class.getName(),
                muleMessageInfo.getEncoding(), emptyList);
        final ObjectFieldDefinition uniqueId = new ObjectFieldDefinition("Id", String.class.getName(),
                muleMessageInfo.getUniqueId(), emptyList);

        final ObjectFieldDefinition currentProcessor = new ObjectFieldDefinition("Message Processor",
                messageProcessorClassName, messageProcessorName, emptyList);

        ObjectFieldDefinition payloadDef = muleMessageInfo.getPayloadDefinition();
        ObjectFieldDefinition excPayloadDef = muleMessageInfo.getExceptionPayloadDefinition();

        payloadTreeViewer.setModel(ObjectFieldDefinitionTreeTableModel.createTreeNode(Arrays.asList(currentProcessor, payloadDef, excPayloadDef, uniqueId, encoding)));
        //Needs to read the selection listener every time
        payloadTreeViewer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel model = payloadTreeViewer.getSelectionModel();
                    int lead = model.getLeadSelectionIndex();
                    getEditor().setSelectionTextValue(String.valueOf(payloadTreeViewer.getModel().getValueAt(lead, 2)));
                }


            }
        });
    }


}
