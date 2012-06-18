
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.mule.tooling.ui.contribution.debugger.view.IObjectFieldDefinitionEditor;

public class ObjectFieldDefinitionComposite extends Composite implements IObjectFieldDefinitionEditor
{

    private Text valueText;
    private TreeViewer payloadTreeViewer;

    public ObjectFieldDefinitionComposite(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new GridLayout());
        createControl(this);

    }

    protected void createControl(Composite parent)
    {
        SashForm form = new SashForm(parent, SWT.VERTICAL);
        form.setLayoutData(new GridData(GridData.FILL_BOTH));

        setPayloadTreeViewer(DebuggerWidgetsBuilder.createObjectDefinitionTreeTable(form));
        getPayloadTreeViewer().getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
        setValueText(new Text(form, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP));
        GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
        layoutData.heightHint = 30;
        getValueText().setLayoutData(layoutData);
        getValueText().setFont(JFaceResources.getTextFont());
        getValueText().setEditable(false);
        form.setWeights(new int[]{8, 1});
    }

    public void setPayloadTreeViewer(TreeViewer payloadTreeViewer)
    {
        this.payloadTreeViewer = payloadTreeViewer;
    }

    public TreeViewer getPayloadTreeViewer()
    {
        return payloadTreeViewer;
    }

    @Override
    public void setSelectionTextValue(String value)
    {
        if (value != null){
            getValueText().setText(value);
        }
        

    }

    public void setValueText(Text valueText)
    {
        this.valueText = valueText;
    }

    public Text getValueText()
    {
        return valueText;
    }

}
