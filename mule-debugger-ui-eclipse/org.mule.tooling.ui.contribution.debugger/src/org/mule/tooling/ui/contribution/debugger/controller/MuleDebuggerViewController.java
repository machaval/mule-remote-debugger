
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.mule.tooling.ui.contribution.debugger.controller.events.ConnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DebuggerExceptionEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DisconnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.ResumeEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerEditor;

public class MuleDebuggerViewController
{
    private IMuleDebuggerEditor editor;
    private EventBus eventBus;

    public MuleDebuggerViewController(IMuleDebuggerEditor editor, EventBus eventBus)
    {
        super();
        this.editor = editor;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind()
    {
        editor.setDebuggerConnected(false);
        eventBus.registerListener(DebuggerEventType.CONNECTED, new IEventHandler<ConnectedEvent>()
        {

            @Override
            public void onEvent(ConnectedEvent event)
            {
                Display.getDefault().syncExec(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        editor.setDebuggerConnected(true);
                        //showDebuggerView();
                    }
                });

            }
        });

        eventBus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>()
        {

            @Override
            public void onEvent(DisconnectedEvent event)
            {
                Display.getDefault().syncExec(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        editor.setDebuggerConnected(false);
                      //  showDebuggerView();
                    }
                });
            }
        });

        eventBus.registerListener(DebuggerEventType.WAITING, new IEventHandler<ResumeEvent>()
        {

            @Override
            public void onEvent(ResumeEvent event)
            {
                Display.getDefault().syncExec(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        editor.setDebuggerWaiting(true);
                       // showDebuggerView();
                    }
                });
            }
        });

        eventBus.registerListener(DebuggerEventType.EXCEPTION, new IEventHandler<DebuggerExceptionEvent>()
        {

            @Override
            public void onEvent(final DebuggerExceptionEvent event)
            {
                Display.getDefault().syncExec(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        MessageDialog.openError(Display.getCurrent().getActiveShell(), "Remote Error",
                            event.getException().getMessage());

                    }
                });
            }
        });

        eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
            new IEventHandler<NewMuleMessageArrivedEvent>()
            {

                @Override
                public void onEvent(NewMuleMessageArrivedEvent event)
                {
                    Display.getDefault().syncExec(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            editor.setDebuggerWaiting(false);
                          //  showDebuggerView();
                        }
                    });
                }
            });
        
      

    }

    public void showDebuggerView()
    {
        IWorkbenchPage page = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage();
        try
        {
            page.showView("org.mule.tooling.ui.contribution.debugger.view");
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
        }
    }
}
