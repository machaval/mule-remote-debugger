
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.mule.tooling.ui.contribution.debugger.view.IScriptEvaluationEditor;

public class ScriptEvaluationComposite extends Composite implements IScriptEvaluationEditor
{
    private Text result;

    private Text script;
    private Combo expression;

    public ScriptEvaluationComposite(Composite parent, int style)
    {
        super(parent, style);
        //
        this.setLayout(new GridLayout());
        createControl(this);
    }

    protected void createControl(Composite parent)
    {

        Composite scriptContainer = new Composite(parent, SWT.NULL);
        scriptContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        scriptContainer.setLayout(new GridLayout(3, false));
        expression = new Combo(scriptContainer, SWT.NULL);

        script = new Text(scriptContainer, SWT.BORDER);
        script.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        result = new Text(parent, SWT.BORDER | SWT.MULTI);
        result.setLayoutData(new GridData(GridData.FILL_BOTH));

    }

    @Override
    public Control getExpressionControl()
    {

        return script;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.mule.tooling.ui.contribution.debugger.ui.IScriptEvaluationEditor#setResultText
     * (java.lang.String)
     */
    @Override
    public void setResultText(String text)
    {
        result.setText(text);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.mule.tooling.ui.contribution.debugger.ui.IScriptEvaluationEditor#getScriptText
     * ()
     */
    @Override
    public String getScriptText()
    {
        return script.getText();
    }

    public void setExpressionTypes(String[] expressionTypes)
    {
        expression.setItems(expressionTypes);
        expression.select(0);
    }

    public String getExpressionType()
    {
        return expression.getItems()[expression.getSelectionIndex()];
    }
}
