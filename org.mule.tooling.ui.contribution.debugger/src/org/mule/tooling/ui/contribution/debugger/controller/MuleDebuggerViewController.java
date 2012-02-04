
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.swt.widgets.Display;
import org.mule.tooling.ui.contribution.debugger.controller.events.ConnectedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.DisconnectedEvent;
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
        editor.setMuleMessageDebuggerEnabled(false);
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
                        editor.setMuleMessageDebuggerEnabled(true);
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
                        editor.setMuleMessageDebuggerEnabled(false);
                    }
                });
            }
        });
    }
}
