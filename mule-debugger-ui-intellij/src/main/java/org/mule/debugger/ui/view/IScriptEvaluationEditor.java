
package org.mule.debugger.ui.view;

import com.intellij.ui.treeStructure.treetable.TreeTable;
import org.mule.debugger.ui.impl.ObjectFieldDefinitionComposite;
import org.mule.widgets.JTreeTable;

import javax.swing.*;

public interface IScriptEvaluationEditor
{



    JTextField getScript() ;

    JComboBox getExpressionType();

    ObjectFieldDefinitionComposite getResult();

    JButton getSetResultAsPayload();



}
