
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.mule.tooling.ui.contribution.debugger.view.IScriptEvaluationEditor;

public class ScriptEvaluationComposite extends Composite implements IScriptEvaluationEditor
{

    private Text script;
    private Combo expression;
    private ObjectFieldDefinitionComposite result;

    private Button setResultAsPayload;

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
        GridLayout layout = new GridLayout(3, false);
        layout.horizontalSpacing = 2;
        scriptContainer.setLayout(layout);
        expression = new Combo(scriptContainer, SWT.NULL);
        new Label(scriptContainer, SWT.NULL).setText(":");
        script = new Text(scriptContainer, SWT.BORDER);
        script.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        result = new ObjectFieldDefinitionComposite(parent, SWT.NULL);
        result.setLayoutData(new GridData(GridData.FILL_BOTH));

        setSetResultAsPayload(new Button(parent, SWT.NULL));
        getSetResultAsPayload().setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
        getSetResultAsPayload().setText("Set result as payload");

    }

    @Override
    public Control getExpressionControl()
    {

        return script;
    }

    public ObjectFieldDefinitionComposite getResult()
    {
        return result;
    }

    public void setResult(ObjectFieldDefinitionComposite result)
    {
        this.result = result;
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

    public void setSetResultAsPayload(Button setResultAsPayload)
    {
        this.setResultAsPayload = setResultAsPayload;
    }

    public Button getSetResultAsPayload()
    {
        return setResultAsPayload;
    }
}
