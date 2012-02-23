
package org.mule.tooling.ui.contribution.debugger.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.mule.debugger.client.DebuggerClient;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerEventType;
import org.mule.tooling.ui.contribution.debugger.controller.events.ClientInitializedEvent;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.impl.DebuggerImages;

public class ResumeAction extends Action
{

    private DebuggerClient client;
    private final EventBus eventBus;

    public ResumeAction(EventBus eventBus)
    {
        super();
        this.eventBus = eventBus;
        this.setClient(client);
        setImageDescriptor(DebuggerImages.IMG_RESUME);
        setToolTipText("Resume");
        setText("Resume");
        this.setEnabled(false);
        this.eventBus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
            new IEventHandler<NewMuleMessageArrivedEvent>()
            {

                @Override
                public void onEvent(NewMuleMessageArrivedEvent event)
                {
                    setEnabled(true);
                }

            });

        this.eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
            new IEventHandler<ClientInitializedEvent>()
            {

                @Override
                public void onEvent(ClientInitializedEvent event)
                {
                    setClient(event.getClient());
                }
            });
        setAccelerator(SWT.CTRL | SWT.F6);
    }

    public void setClient(DebuggerClient client)
    {
        this.client = client;
    }

    public DebuggerClient getClient()
    {
        return client;
    }

    @Override
    public void run()
    {
        setEnabled(false);
        client.resume();
    }

}
