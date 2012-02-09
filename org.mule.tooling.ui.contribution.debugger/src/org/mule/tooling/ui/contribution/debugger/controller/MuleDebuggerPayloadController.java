
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.swt.widgets.Display;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IPayloadEditor;

public class MuleDebuggerPayloadController
{

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
                            payload.setPayloadClassName(muleMessageInfo.getPayloadClassName());
                            payload.setPayloadOutput(event.getMuleMessageInfo().getPayloadString());
                        }
                    });

                }
            });
    }

}
