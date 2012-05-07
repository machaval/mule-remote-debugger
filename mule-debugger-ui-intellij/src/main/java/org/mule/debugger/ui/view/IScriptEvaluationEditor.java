
package org.mule.debugger.ui.view;

import org.mule.widgets.JTreeTable;

import javax.swing.*;

public interface IScriptEvaluationEditor
{

    JTextField getExpressionControl();

    void setResultText(String text);

    String getScriptText();
    
    void setExpressionTypes(String[] expressionTypes);
    
    String getExpressionType();
    
    JTreeTable getResultTree();
    
    JButton getSetResultAsPayload();

}
