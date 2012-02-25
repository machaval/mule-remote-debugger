
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.mule.tooling.ui.contribution.debugger.view.IScriptEvaluationEditor;

public class ScriptEvaluationComposite extends Composite implements IScriptEvaluationEditor
{
    private Text result;

    private Text script;
    private Combo expression;
    private TreeViewer resultTree;
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

        TabFolder resultTab = new TabFolder(parent, SWT.BORDER);
        resultTab.setLayoutData(new GridData(GridData.FILL_BOTH));
        TabItem textResult = new TabItem(resultTab, SWT.NULL);
        textResult.setText("To String");
        result = new Text(resultTab, SWT.BORDER | SWT.MULTI);
        result.setLayoutData(new GridData(GridData.FILL_BOTH));
        textResult.setControl(result);
        setResultTree(new TreeViewer(resultTab, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER));
        getResultTree().getTree().setHeaderVisible(true);

        getResultTree().getTree().setHeaderVisible(true);
        TreeColumn col = new TreeColumn(getResultTree().getTree(), SWT.NONE);
        col.setText("Name");
        col.setResizable(true);
        col.setWidth(120);
        col = new TreeColumn(getResultTree().getTree(), SWT.NONE);
        col.setText("ClassName");
        col.setWidth(120);
        col.setResizable(true);
        col = new TreeColumn(getResultTree().getTree(), SWT.NONE);
        col.setWidth(120);
        col.setText("Value");
        col.setResizable(true);

        TabItem treeResult = new TabItem(resultTab, SWT.NULL);
        treeResult.setText("Tree");
        treeResult.setControl(getResultTree().getTree());
        setSetResultAsPayload(new Button(parent, SWT.NULL));
        getSetResultAsPayload().setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
        getSetResultAsPayload().setText("Set result as payload");

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

    public void setResultTree(TreeViewer resultTree)
    {
        this.resultTree = resultTree;
    }

    public TreeViewer getResultTree()
    {
        return resultTree;
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
