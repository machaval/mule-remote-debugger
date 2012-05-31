
package org.mule.debugger.ui.view;

import com.intellij.ui.treeStructure.treetable.TreeTable;
import org.mule.widgets.JTreeTable;

import javax.swing.*;

public interface IScriptEvaluationEditor
{

    JTextArea getResultText();

    JTextField getScript() ;

    JComboBox getExpressionType();

    TreeTable getResultTree();

    JButton getSetResultAsPayload();

}
