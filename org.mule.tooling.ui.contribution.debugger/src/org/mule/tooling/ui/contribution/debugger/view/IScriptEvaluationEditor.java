
package org.mule.tooling.ui.contribution.debugger.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public interface IScriptEvaluationEditor
{

    Control getExpressionControl();

    void setResultText(String text);

    String getScriptText();
    
    void setExpressionTypes(String[] expressionTypes);
    
    String getExpressionType();
    
    TreeViewer getResultTree();
    
    Button getSetResultAsPayload();

}
