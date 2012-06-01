package org.mule.debugger.ui.controller;

import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DefaultDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.debugger.ui.event.EventBus;
import org.mule.debugger.ui.event.IEvent;
import org.mule.debugger.ui.event.IEventHandler;
import org.mule.debugger.ui.events.ClientInitializedEvent;
import org.mule.debugger.ui.events.DebuggerEventType;
import org.mule.debugger.ui.events.DisconnectedEvent;
import org.mule.debugger.ui.view.IScriptEvaluationEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ScriptEvaluationController {
    private static final String[] EXPRESSION_TYPES = new String[]{"groovy", "mule", "headers", "variable",
            "jython", "javascript", "bean", "endpoint", "function", "header", "headers-list", "json",
            "json-node", "jxpath", "map-payload", "message", "ognl", "payload", "process", "regex", "string",
            "xpath", "xpath2", "xpath-node"};
    private IScriptEvaluationEditor scriptEvaluation;
    private EventBus eventBus;
    private DebuggerClient client;

    public ScriptEvaluationController(IScriptEvaluationEditor scriptEvaluation, EventBus eventBus) {
        super();
        this.scriptEvaluation = scriptEvaluation;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind() {

        scriptEvaluation.getExpressionType().setModel(new DefaultComboBoxModel(EXPRESSION_TYPES));


        eventBus.registerListener(DebuggerEventType.CLIENT_INITIALIZED,
                new IEventHandler<ClientInitializedEvent>() {
                    @Override
                    public void onEvent(ClientInitializedEvent event) {
                        client = event.getClient();
                    }
                });

        scriptEvaluation.getSetResultAsPayload().setAction(new AbstractAction("Set result as payload") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = scriptEvaluation.getExpressionType().getSelectedIndex();
                client.executeScriptAssignResultToPayload("#[" + EXPRESSION_TYPES[selectedIndex] + ":"
                        + scriptEvaluation.getScript().getText() + "]", null);
            }
        });

        scriptEvaluation.getScript().addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    int selectedIndex = scriptEvaluation.getExpressionType().getSelectedIndex();
                    client.executeScript(
                            "#[" + EXPRESSION_TYPES[selectedIndex] + ":" + scriptEvaluation.getScript().getText()
                                    + "]", new DefaultDebuggerResponseCallback() {

                        @Override
                        public void onScriptEvaluation(final ScriptResultInfo info) {
                            SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    scriptEvaluation.getResultText().setText(info.getToStringResult());
                                    ObjectFieldDefinition excResultDef = info.getResult();

                                    scriptEvaluation.getResultTree()
                                            .setModel(ObjectFieldDefinitionTreeTableModel.createTreeNode(excResultDef));

                                }
                            });
                        }

                        @Override
                        public void onException(final RemoteDebugException exception) {
                            SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    scriptEvaluation.getResultText().setText("ERROR :\n Remote exception  :\n"
                                            + exception.getMessage());

                                    ObjectFieldDefinition excResultDef = exception.getException();

                                    scriptEvaluation.getResultTree()
                                            .setModel(ObjectFieldDefinitionTreeTableModel.createTreeNode(excResultDef));
                                }
                            });

                        }

                    });
                }
            }

        }
        );

        this.eventBus.registerListener(DebuggerEventType.WAITING, new IEventHandler() {
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

        this.eventBus.registerListener(DebuggerEventType.DISCONNECTED, new IEventHandler<DisconnectedEvent>() {

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

    private void cleanUI() {
        scriptEvaluation.getResultText().setText("");
        scriptEvaluation.getResultTree()
                .setModel(new ListTreeTableModel(null, new ColumnInfo[0]));
    }
}
