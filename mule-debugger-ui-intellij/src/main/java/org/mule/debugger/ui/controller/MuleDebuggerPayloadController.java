package org.mule.debugger.ui.controller;

import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;
import org.mule.debugger.ui.view.IDebuggerMessageViewer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MuleDebuggerPayloadController {


    private IDebuggerMessageViewer payload;
    private final EventBus eventBus;

    public MuleDebuggerPayloadController(IDebuggerMessageViewer payload, EventBus eventBus) {
        super();
        this.payload = payload;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind() {

        payload.getPayloadTreeViewer().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });


        this.eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(final NewMuleMessageArrivedEvent event) {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                                payload.setEncoding(muleMessageInfo.getEncoding());
                                payload.setUniqueId(muleMessageInfo.getUniqueId());
                                payload.setSelectionClassName(muleMessageInfo.getPayloadClassName());
                                payload.setSelectionTextValue(muleMessageInfo.getPayloadString());
                                payload.setCurrentProcessor(muleMessageInfo.getCurrentProcessor());
                                ObjectFieldDefinition payloadDef = muleMessageInfo.getPayloadDefinition();
                                ObjectFieldDefinition excPayloadDef = muleMessageInfo.getExceptionPayloadDefinition();

                                payload.getPayloadTreeViewer().setModel(ObjectFieldDefinitionTreeTableModel.createTreeNode(payloadDef));

                            }
                        });

                    }
                });
    }

}
