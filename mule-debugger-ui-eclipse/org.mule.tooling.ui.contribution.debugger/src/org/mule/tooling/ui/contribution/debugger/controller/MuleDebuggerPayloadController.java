
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.debug.ui.actions.OpenTypeAction;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IPayloadEditor;

public class MuleDebuggerPayloadController
{

    private final class OpenClassName implements SelectionListener
    {
        @Override
        public void widgetSelected(SelectionEvent e)
        {
            String className = e.text;
            try
            {
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

        @Override
        public void widgetDefaultSelected(SelectionEvent e)
        {

        }
    }

    private IPayloadEditor payload;
    private final EventBus eventBus;

    public MuleDebuggerPayloadController(IPayloadEditor payload, EventBus eventBus)
    {
        super();
        this.payload = payload;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind()
    {
        payload.getPayloadTreeViewer().setContentProvider(new TreeNodeContentProvider());
        payload.getPayloadTreeViewer().setLabelProvider(new JavaBeanLabelProvider());

        payload.getTransformer().addSelectionListener(new OpenClassName());
        payload.getPayloadClassName().addSelectionListener(new OpenClassName());

        this.eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
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
                            MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                            payload.setEncoding(muleMessageInfo.getEncoding());
                            payload.setUniqueId(muleMessageInfo.getUniqueId());
                            payload.setPayloadClassName("<a>" + muleMessageInfo.getPayloadClassName()
                                                        + "</a>");
                            payload.setPayloadOutput(muleMessageInfo.getPayloadString());
                            payload.setCurrentProcessor("<a>" + muleMessageInfo.getCurrentProcessor()
                                                        + "</a>");
                            ObjectFieldDefinition payloadDef = muleMessageInfo.getPayloadDefinition();
                            ObjectFieldDefinition excPayloadDef = muleMessageInfo.getExceptionPayloadDefinition();

                            payload.getPayloadTreeViewer().setInput(
                                new TreeNode[]{ObjectTreeNodeBuilder.createTreeNode(payloadDef),
                                    ObjectTreeNodeBuilder.createTreeNode(excPayloadDef)});

                        }
                    });

                }
            });
    }

}
