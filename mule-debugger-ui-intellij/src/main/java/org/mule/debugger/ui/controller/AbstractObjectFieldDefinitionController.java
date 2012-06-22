/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.controller;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.OpenSourceUtil;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.DisconnectedEvent;
import org.mule.debugger.ui.events.NewMuleMessageArrivedEvent;
import org.mule.debugger.ui.view.IObjectFieldDefinitionEditor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class AbstractObjectFieldDefinitionController {
    private final IObjectFieldDefinitionEditor editor;
    protected final EventBus eventBus;
    private final Project project;

    public AbstractObjectFieldDefinitionController(IObjectFieldDefinitionEditor editor, EventBus eventBus, Project project) {
        super();
        this.editor = editor;
        this.eventBus = eventBus;
        this.project = project;
        bind();
    }

    protected void bind() {


        final TreeTable treeViewer = getEditor().getPayloadTreeViewer();
        treeViewer.setModel(getEmptyTableModel());

        treeViewer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel model = treeViewer.getSelectionModel();
                    int lead = model.getLeadSelectionIndex();
                    getEditor().setSelectionTextValue(String.valueOf(treeViewer.getModel().getValueAt(lead, 2)));
                }


            }
        });

       treeViewer.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) {
                   ListSelectionModel model = treeViewer.getSelectionModel();
                   int lead = model.getLeadSelectionIndex();
                   String className = String.valueOf(treeViewer.getModel().getValueAt(lead, 1));
                   PsiClass aClass = JavaPsiFacade.getInstance(getProject()).findClass(className, GlobalSearchScope.allScope(getProject()));
                   if (aClass != null) {
                       OpenSourceUtil.navigate(aClass);
                   }
               }
           }
       });


        this.getEventBus().registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
                new IEventHandler<NewMuleMessageArrivedEvent>() {

                    @Override
                    public void onEvent(final NewMuleMessageArrivedEvent event) {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {


                                populateData(event);
                            }
                        });

                    }
                });

        this.getEventBus().registerListener(DebuggerEventType.WAITING, new IEventHandler() {
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

        this.getEventBus().registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>() {

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

    protected abstract void populateData(NewMuleMessageArrivedEvent event);

    private void cleanUI() {
        getEditor().getPayloadTreeViewer().setModel(getEmptyTableModel());
        getEditor().setSelectionTextValue("");
    }

    private TreeTableModel getEmptyTableModel() {
        return ObjectFieldDefinitionTreeTableModel.createTreeNode();
    }

    public IObjectFieldDefinitionEditor getEditor() {
        return editor;
    }


    public EventBus getEventBus() {
        return eventBus;
    }

    public Project getProject() {
        return project;
    }


}
