
package org.mule.debugger.ui.controller;

import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.*;
import org.mule.debugger.ui.view.IMuleDebuggerEditor;

import javax.swing.*;

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
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        editor.setDebuggerConnected(true);

                    }
                });

            }
        });

        eventBus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>()
        {

            @Override
            public void onEvent(DisconnectedEvent event)
            {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        editor.setDebuggerConnected(false);

                    }
                });
            }
        });

        eventBus.registerListener(DebuggerEventType.WAITING, new IEventHandler<ResumeEvent>()
        {

            @Override
            public void onEvent(ResumeEvent event)
            {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        editor.setDebuggerWaiting(true);

                    }
                });
            }
        });

        eventBus.registerListener(DebuggerEventType.EXCEPTION, new IEventHandler<DebuggerExceptionEvent>()
        {

            @Override
            public void onEvent(final DebuggerExceptionEvent event)
            {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {


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
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            editor.setDebuggerWaiting(false);

                        }
                    });
                }
            });

    }


}
