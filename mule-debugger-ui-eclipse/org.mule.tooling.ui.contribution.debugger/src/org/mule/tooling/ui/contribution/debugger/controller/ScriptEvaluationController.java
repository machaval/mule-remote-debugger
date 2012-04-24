
package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DefaultDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.ClientInitializedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IScriptEvaluationEditor;

public class ScriptEvaluationController
{
    private static final String[] EXPRESSION_TYPES = new String[]{"mule", "headers", "groovy", "variable",
        "jython", "javascript", "bean", "endpoint", "function", "header", "headers-list", "json",
        "json-node", "jxpath", "map-payload", "message", "ognl", "payload", "process", "regex", "string",
        "xpath", "xpath2", "xpath-node"};
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

        scriptEvaluation.setExpressionTypes(EXPRESSION_TYPES);

        scriptEvaluation.getResultTree().setContentProvider(new TreeNodeContentProvider());
        scriptEvaluation.getResultTree().setLabelProvider(new TreeNodeLabelProvider());

        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
            new IEventHandler<ClientInitializedEvent>()
            {
                @Override
                public void onEvent(ClientInitializedEvent event)
                {
                    client = event.getClient();
                }
            });

        scriptEvaluation.getSetResultAsPayload().addMouseListener(new MouseListener()
        {

            @Override
            public void mouseUp(MouseEvent e)
            {
                client.executeScriptAssignResultToPayload("#[" + scriptEvaluation.getExpressionType() + ":"
                                                          + scriptEvaluation.getScriptText() + "]", null);

            }

            @Override
            public void mouseDown(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDoubleClick(MouseEvent e)
            {
                // TODO Auto-generated method stub

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
                                        ObjectFieldDefinition excResultDef = info.getResult();

                                        scriptEvaluation.getResultTree()
                                            .setInput(
                                                new TreeNode[]{ObjectTreeNodeBuilder.createTreeNode(excResultDef)});

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

                                        ObjectFieldDefinition excResultDef = exception.getException();

                                        scriptEvaluation.getResultTree()
                                            .setInput(
                                                new TreeNode[]{ObjectTreeNodeBuilder.createTreeNode(excResultDef)});
                                    }
                                });

                            }

                        });
                }

            }
        });
    }
}
