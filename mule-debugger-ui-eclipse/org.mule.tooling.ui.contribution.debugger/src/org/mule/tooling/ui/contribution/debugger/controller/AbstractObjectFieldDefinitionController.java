
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.debug.ui.actions.OpenTypeAction;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.widgets.Display;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IObjectFieldDefinitionEditor;

public abstract class AbstractObjectFieldDefinitionController
{

    private IObjectFieldDefinitionEditor editor;

    protected abstract void populateData(final NewMuleMessageArrivedEvent event);

    private final EventBus eventBus;

    public AbstractObjectFieldDefinitionController(IObjectFieldDefinitionEditor editor, EventBus eventBus)
    {
        super();
        this.setEditor(editor);
        this.eventBus = eventBus;
        bind();
    }

    protected void bind()
    {
        getEditor().getPayloadTreeViewer().setContentProvider(new TreeNodeContentProvider());
        getEditor().getPayloadTreeViewer().setLabelProvider(new TreeNodeLabelProvider());
        getEditor().getPayloadTreeViewer().addSelectionChangedListener(new ISelectionChangedListener()
        {
    
            @Override
            public void selectionChanged(SelectionChangedEvent event)
            {
    
                ISelection selection = event.getSelection();
                if (!selection.isEmpty())
                {
    
                    IStructuredSelection treeSelection = (IStructuredSelection) selection;
                    TreeNode node = (TreeNode) treeSelection.getFirstElement();
                    ObjectFieldDefinition def = (ObjectFieldDefinition) node.getValue();
                    getEditor().setSelectionTextValue(def.getValue());
    
                }
            }
        });
        getEditor().getPayloadTreeViewer().addDoubleClickListener(new IDoubleClickListener()
        {
    
            @Override
            public void doubleClick(DoubleClickEvent event)
            {
                IStructuredSelection treeSelection = (IStructuredSelection) event.getSelection();
                TreeNode node = (TreeNode) treeSelection.getFirstElement();
                ObjectFieldDefinition def = (ObjectFieldDefinition) node.getValue();
                String className = def.getClassName();
                try
                {
                    @SuppressWarnings("restriction")
                    IType file = OpenTypeAction.findTypeInWorkspace(className);
    
                    if (file instanceof IJavaElement)
                    {
                        JavaUI.openInEditor((IJavaElement) file);
                    }
                    else
                    {
                        MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Warning",
                            "Class : '" + className + "' can not be opened");
                    }
    
                }
                catch (CoreException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
    
            }
        });
    
        this.getEventBus().registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
            new IEventHandler<NewMuleMessageArrivedEvent>()
            {
    
                @Override
                public void onEvent(final NewMuleMessageArrivedEvent event)
                {
                    Display.getDefault().asyncExec(new Runnable()
                    {
    
                        @Override
                        public void run()
                        {
                            populateData(event);
    
                        }
                    });
    
                }
            });
    }

    public EventBus getEventBus()
    {
        return eventBus;
    }

    public void setEditor(IObjectFieldDefinitionEditor editor)
    {
        this.editor = editor;
    }

    public IObjectFieldDefinitionEditor getEditor()
    {
        return editor;
    }

}
