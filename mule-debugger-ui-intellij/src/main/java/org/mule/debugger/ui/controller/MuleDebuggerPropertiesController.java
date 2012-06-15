package org.mule.debugger.ui.controller;

import com.intellij.openapi.util.AsyncResult;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;

import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DefaultDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.*;
import org.mule.debugger.ui.impl.NewPropertyDialog;
import org.mule.debugger.ui.view.IMuleDebuggerProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MuleDebuggerPropertiesController {

    private IMuleDebuggerProperties propertiesView;
    private EventBus eventBus;
    private DebuggerClient client;

    public MuleDebuggerPropertiesController(IMuleDebuggerProperties propertiesView, EventBus bus) {
        super();
        this.propertiesView = propertiesView;
        this.eventBus = bus;
        bind();
    }

    protected void bind() {

        cleanUI();

        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
                new IEventHandler<ClientInitializedEvent>() {
                    @Override
                    public void onEvent(ClientInitializedEvent event) {
                        client = event.getClient();
                    }
                });


        propertiesView.setAddInvocation(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {

                final NewPropertyDialog newPropertyDialog = new NewPropertyDialog();
                newPropertyDialog.setTitle("Add New Flow Property");
                newPropertyDialog.centerRelativeToParent();
                AsyncResult<Boolean> booleanAsyncResult = newPropertyDialog.showAndGetOk();
                booleanAsyncResult.doWhenDone(new AsyncResult.Handler<Boolean>() {
                    @Override
                    public void run(Boolean aBoolean) {

                        client.executeScript("#[groovy:message.setInvocationProperty( \"" + newPropertyDialog.getNameField().getText() + "\"," + newPropertyDialog.getValueField().getText() + " )]", new DefaultDebuggerResponseCallback() {
                            @Override
                            public void onScriptEvaluation(ScriptResultInfo info) {
                                eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                            }

                            @Override
                            public void onException(RemoteDebugException exception) {
                                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    }
                });


            }
        });

        propertiesView.setRemoveInvocation(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                JTable invocationProperties = propertiesView.getInvocationProperties();
                int selectedRow = invocationProperties.getSelectedRow();
                String propertyName = String.valueOf(invocationProperties.getModel().getValueAt(selectedRow, 0));

                client.executeScript("#[groovy:message.removeProperty(\"" + propertyName + "\",org.mule.api.transport.PropertyScope.INVOCATION)]", new DefaultDebuggerResponseCallback() {
                    @Override
                    public void onScriptEvaluation(ScriptResultInfo info) {
                        eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                    }
                });
            }
        });

        propertiesView.setAddSession(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                final NewPropertyDialog newPropertyDialog = new NewPropertyDialog();
                newPropertyDialog.setTitle("Add New Session Property");
                newPropertyDialog.centerRelativeToParent();
                AsyncResult<Boolean> booleanAsyncResult = newPropertyDialog.showAndGetOk();
                booleanAsyncResult.doWhenDone(new AsyncResult.Handler<Boolean>() {
                    @Override
                    public void run(Boolean aBoolean) {

                        client.executeScript("#[groovy:message.setProperty(" + newPropertyDialog.getNameField().getText() + "," + newPropertyDialog.getValueField().getText() + ",org.mule.api.transport.PropertyScope.SESSION )]", new DefaultDebuggerResponseCallback() {
                            @Override
                            public void onScriptEvaluation(ScriptResultInfo info) {
                                eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                            }
                        });
                    }
                });
            }
        });


        propertiesView.setRemoveSession(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                JTable invocationProperties = propertiesView.getInvocationProperties();
                int selectedRow = invocationProperties.getSelectedRow();
                String propertyName = String.valueOf(invocationProperties.getModel().getValueAt(selectedRow, 0));

                client.executeScript("#[groovy:message.removeProperty(\"" + propertyName + "\",org.mule.api.transport.PropertyScope.SESSION)]", new DefaultDebuggerResponseCallback() {
                    @Override
                    public void onScriptEvaluation(ScriptResultInfo info) {
                        eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                    }
                });
            }
        });


        propertiesView.setAddOutbound(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                final NewPropertyDialog newPropertyDialog = new NewPropertyDialog();
                newPropertyDialog.setTitle("Add New Outbound Property");
                newPropertyDialog.centerRelativeToParent();
                AsyncResult<Boolean> booleanAsyncResult = newPropertyDialog.showAndGetOk();
                booleanAsyncResult.doWhenDone(new AsyncResult.Handler<Boolean>() {
                    @Override
                    public void run(Boolean aBoolean) {

                        client.executeScript("#[groovy:message.setProperty(" + newPropertyDialog.getNameField().getText() + "," + newPropertyDialog.getValueField().getText() + ",org.mule.api.transport.PropertyScope.OUTBOUND )]", new DefaultDebuggerResponseCallback() {
                            @Override
                            public void onScriptEvaluation(ScriptResultInfo info) {
                                eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                            }
                        });
                    }
                });
            }
        });


        propertiesView.setRemoveOutbound(new AnActionButtonRunnable() {
            @Override
            public void run(AnActionButton anActionButton) {
                JTable invocationProperties = propertiesView.getInvocationProperties();
                int selectedRow = invocationProperties.getSelectedRow();
                String propertyName = String.valueOf(invocationProperties.getModel().getValueAt(selectedRow, 0));

                client.executeScript("#[groovy:message.removeProperty(\"" + propertyName + "\",org.mule.api.transport.PropertyScope.OUTBOUND)]", new DefaultDebuggerResponseCallback() {
                    @Override
                    public void onScriptEvaluation(ScriptResultInfo info) {
                        eventBus.fireEvent(new NewMuleMessageArrivedEvent(info.getMessage()));
                    }
                });
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
                                propertiesView.getInboundProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getInboundProperties()));
                                propertiesView.getInvocationProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getInvocationProperties()));
                                propertiesView.getSessionProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getSessionProperties()));
                                propertiesView.getOutboundProperties().setModel(MapTableModel.createMapTableModel(muleMessageInfo.getOutboundProperties()));

                            }
                        });

                    }
                });

        this.eventBus.registerListener(DebuggerEventType.WAITING, new IEventHandler() {
            @Override
            public void onEvent(IEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cleanUI();
                    }
                });
            }
        });

        this.eventBus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>() {

            @Override
            public void onEvent(DisconnectedEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cleanUI();
                    }
                });
            }
        });
    }

    private void cleanUI() {
        propertiesView.getInboundProperties().setModel(new DefaultTableModel());
        propertiesView.getInvocationProperties().setModel(new DefaultTableModel());
        propertiesView.getSessionProperties().setModel(new DefaultTableModel());
        propertiesView.getOutboundProperties().setModel(new DefaultTableModel());
    }

}
