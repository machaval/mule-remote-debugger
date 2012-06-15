package org.mule.debugger.ui.controller;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.DisconnectedEvent;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;
import org.mule.debugger.ui.impl.ClassLinkTableCellEditor;
import org.mule.debugger.ui.impl.ClassLinkTableCellRenderer;
import org.mule.debugger.ui.view.IDebuggerMessageViewer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.util.List;

public class MuleDebuggerPayloadController {


    private IDebuggerMessageViewer payload;
    private final EventBus eventBus;
    private Project project;

    public MuleDebuggerPayloadController(IDebuggerMessageViewer payload, EventBus eventBus,Project project) {
        super();
        this.payload = payload;
        this.eventBus = eventBus;
        this.project = project;
        bind();
    }

    protected void bind() {


        final TreeTable payloadTreeViewer = payload.getPayloadTreeViewer();
        payloadTreeViewer.setModel(getEmptyTableModel());

        payloadTreeViewer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel model = payloadTreeViewer.getSelectionModel();
                    int lead = model.getLeadSelectionIndex();
                    payload.setSelectionTextValue(String.valueOf(payloadTreeViewer.getModel().getValueAt(lead, 2)));
                }


            }
        });


        payloadTreeViewer.setDefaultRenderer(Class.class, new ClassLinkTableCellRenderer());
        payloadTreeViewer.setDefaultEditor(Class.class, new ClassLinkTableCellEditor(project));

        final MouseMotionAdapter mma = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {

                Point p = e.getPoint();


                int columnNumber = payloadTreeViewer.columnAtPoint(p);
                if (columnNumber > 0) {
                    Class<?> aClass = payloadTreeViewer.getColumnClass(columnNumber);
                    if (aClass == Class.class) {
                        payloadTreeViewer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        payloadTreeViewer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else {
                    payloadTreeViewer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }
        };
        payloadTreeViewer.addMouseMotionListener(mma);

        this.eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(final NewMuleMessageArrivedEvent event) {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();

                                payload.setSelectionTextValue(muleMessageInfo.getPayloadString());

                                List<ObjectFieldDefinition> innerElements = Collections.emptyList();
                                ObjectFieldDefinition encoding = new ObjectFieldDefinition("Encoding", String.class.getName(), muleMessageInfo.getEncoding(), innerElements);
                                ObjectFieldDefinition messageProcessor = new ObjectFieldDefinition("Message Processor", muleMessageInfo.getCurrentProcessor(), muleMessageInfo.getCurrentProcessor(), innerElements);
                                ObjectFieldDefinition id = new ObjectFieldDefinition("Message ID", String.class.getName(), muleMessageInfo.getUniqueId(), innerElements);
                                ObjectFieldDefinition payloadDef = muleMessageInfo.getPayloadDefinition();
                                ObjectFieldDefinition excPayloadDef = muleMessageInfo.getExceptionPayloadDefinition();

                                ObjectFieldDefinition root = new ObjectFieldDefinition("Root", "", "", Arrays.asList(messageProcessor, id, encoding, payloadDef, excPayloadDef));
                                payloadTreeViewer.setModel(ObjectFieldDefinitionTreeTableModel.createTreeNode(root));
                                //Needs to read the selection listener every time
                                payloadTreeViewer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                                    @Override
                                    public void valueChanged(ListSelectionEvent e) {
                                        if (!e.getValueIsAdjusting()) {
                                            ListSelectionModel model = payloadTreeViewer.getSelectionModel();
                                            int lead = model.getLeadSelectionIndex();
                                            payload.setSelectionTextValue(String.valueOf(payloadTreeViewer.getModel().getValueAt(lead, 2)));
                                        }


                                    }
                                });
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
        payload.getPayloadTreeViewer().setModel(getEmptyTableModel());
        payload.setSelectionTextValue("");
    }

    private TreeTableModel getEmptyTableModel() {
        return ObjectFieldDefinitionTreeTableModel.createTreeNode(new ObjectFieldDefinition("Root", "", "", new ArrayList<ObjectFieldDefinition>()));
    }

}
