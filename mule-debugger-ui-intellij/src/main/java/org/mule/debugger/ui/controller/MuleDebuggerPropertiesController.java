package org.mule.debugger.ui.controller;

import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;
import org.mule.debugger.ui.view.IMuleDebuggerProperties;

import javax.swing.*;
import java.util.Map;

public class MuleDebuggerPropertiesController {

    private IMuleDebuggerProperties propertiesView;
    private EventBus bus;

    public MuleDebuggerPropertiesController(IMuleDebuggerProperties propertiesView, EventBus bus) {
        super();
        this.propertiesView = propertiesView;
        this.bus = bus;
        bind();
    }

    protected void bind() {


        this.bus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(final NewMuleMessageArrivedEvent event) {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                                propertiesView.getInboundProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getInboundProperties()));
                                propertiesView.getInvocationProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getInvocationProperties()));
                                propertiesView.getSessionProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getSessionProperties()));
                            }
                        });

                    }
                });
    }

}
