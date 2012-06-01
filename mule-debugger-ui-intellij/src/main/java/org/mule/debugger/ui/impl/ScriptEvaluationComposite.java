
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

public class ScriptEvaluationComposite extends JPanel  implements IScriptEvaluationEditor
{

    private JTextArea resultText;
    private JTextField script;
    private JComboBox expressionType;
    private TreeTable resultTree;
    private JButton setResultAsPayload;

    public ScriptEvaluationComposite()
    {
        this.resultText = new JTextArea();
        this.script = new JTextField();

        this.expressionType = new JComboBox();
        this.expressionType.setEditable(true);
        this.resultTree = new TreeTable(new ListTreeTableModel(null,new ColumnInfo[0])){
            @Override
            public TreeTableCellRenderer createTableRenderer(TreeTableModel treeTableModel) {
                TreeTableCellRenderer tableRenderer = super.createTableRenderer(treeTableModel);
                UIUtil.setLineStyleAngled(tableRenderer);
//                tableRenderer.setRootVisible(false);
                tableRenderer.setShowsRootHandles(true);

                return tableRenderer;
            }
        };

        this.setResultAsPayload = new JButton("Set result as payload");


        JPanel scriptContainer = new JPanel();
        scriptContainer.setLayout(new BorderLayout());
        scriptContainer.add(expressionType,BorderLayout.WEST);
        scriptContainer.add(script,BorderLayout.CENTER);
        this.setLayout(new BorderLayout());

        JBTabbedPane debuggerTabs = new JBTabbedPane(SwingConstants.TOP);
        debuggerTabs.add("Text",resultText);
        debuggerTabs.add("Tree",resultTree);

        this.add(scriptContainer,BorderLayout.NORTH);
        this.add(debuggerTabs,BorderLayout.CENTER);
        this.add(setResultAsPayload,BorderLayout.SOUTH);

    }

    public JTextArea getResultText() {
        return resultText;
    }

    public JTextField getScript() {
        return script;
    }

    public JComboBox getExpressionType() {
        return expressionType;
    }

    public TreeTable getResultTree() {
        return resultTree;
    }

    public JButton getSetResultAsPayload() {
        return setResultAsPayload;
    }
}
