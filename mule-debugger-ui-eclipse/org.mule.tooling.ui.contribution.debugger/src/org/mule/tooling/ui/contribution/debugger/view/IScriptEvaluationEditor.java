
package org.mule.tooling.ui.contribution.debugger.view;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.mule.tooling.ui.contribution.debugger.view.impl.ObjectFieldDefinitionComposite;

public interface IScriptEvaluationEditor
{

    Control getExpressionControl();

    String getScriptText();

    void setExpressionTypes(String[] expressionTypes);

    String getExpressionType();

    Button getSetResultAsPayload();

    ObjectFieldDefinitionComposite getResult();

}
