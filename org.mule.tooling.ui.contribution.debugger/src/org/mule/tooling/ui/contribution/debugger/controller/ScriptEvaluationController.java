
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Display;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DefaultDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.ClientInitializedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IScriptEvaluationEditor;

public class ScriptEvaluationController
{
    private IScriptEvaluationEditor scriptEvaluation;
    private EventBus eventBus;
    private DebuggerClient client;

    public ScriptEvaluationController(IScriptEvaluationEditor scriptEvaluation, EventBus eventBus)
    {
        super();
        this.scriptEvaluation = scriptEvaluation;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind()
    {

        scriptEvaluation.setExpressionTypes(new String[]{"mule", "headers", "groovy", "variable", "jython",
            "javascript"});
        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
            new IEventHandler<ClientInitializedEvent>()
            {
                @Override
                public void onEvent(ClientInitializedEvent event)
                {
                    client = event.getClient();
                }
            });

        scriptEvaluation.getExpressionControl().addKeyListener(new KeyListener()
        {

            @Override
            public void keyReleased(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.character == SWT.CR || e.character == SWT.LF)
                {
                    client.executeScript(
                        "#[" + scriptEvaluation.getExpressionType() + ":" + scriptEvaluation.getScriptText()
                                        + "]", new DefaultDebuggerResponseCallback()
                        {

                            @Override
                            public void onScriptEvaluation(final ScriptResultInfo info)
                            {
                                Display.getDefault().asyncExec(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        scriptEvaluation.setResultText(info.getToStringResult());
                                    }
                                });
                            }

                            @Override
                            public void onException(final RemoteDebugException exception)
                            {
                                Display.getDefault().asyncExec(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        scriptEvaluation.setResultText("ERROR :\n Remote exception  :\n"
                                                                       + exception.getMessage());
                                    }
                                });

                            }

                        });
                }

            }
        });
    }
}
