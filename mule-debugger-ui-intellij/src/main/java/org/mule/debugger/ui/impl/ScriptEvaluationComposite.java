package org.mule.debugger.ui.impl;

import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableCellRenderer;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.UIUtil;
import org.mule.debugger.ui.view.IScriptEvaluationEditor;

import javax.swing.*;
import java.awt.*;

public class ScriptEvaluationComposite extends JPanel implements IScriptEvaluationEditor {


    private JTextField script;
    private JComboBox expressionType;
    private ObjectFieldDefinitionComposite resultTree;
    private JButton setResultAsPayload;

    public ScriptEvaluationComposite() {

        this.script = new JTextField();

        this.expressionType = new JComboBox();
        this.expressionType.setEditable(true);
        this.resultTree = new ObjectFieldDefinitionComposite();

        this.setResultAsPayload = new JButton("Set result as payload");


        JPanel scriptContainer = new JPanel();
        scriptContainer.setLayout(new BorderLayout());
        scriptContainer.add(expressionType, BorderLayout.WEST);
        scriptContainer.add(script, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());


        this.add(scriptContainer, BorderLayout.NORTH);
        this.add(resultTree, BorderLayout.CENTER);
        this.add(setResultAsPayload, BorderLayout.SOUTH);

    }


    public JTextField getScript() {
        return script;
    }

    public JComboBox getExpressionType() {
        return expressionType;
    }

    public ObjectFieldDefinitionComposite getResult() {
        return resultTree;
    }

    public JButton getSetResultAsPayload() {
        return setResultAsPayload;
    }
}
