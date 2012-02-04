
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.mule.tooling.ui.contribution.debugger.view.IPayloadEditor;

public class MuleDebuggerPayloadComposite extends Composite implements
     IPayloadEditor
{

  
    private Text payloadOutputText;
    private TreeViewer payloadTreeViewer;
    private Text payloadClassName;

    public MuleDebuggerPayloadComposite(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new GridLayout());
        createControl(this);

    }

    protected void createControl(Composite parent)
    {
        createPayloadView(parent);
    }

    protected Composite createPayloadView(Composite parent)
    {
        Composite payloadData = new Composite(parent, SWT.NULL);
        payloadData.setLayout(new GridLayout(2, false));

        payloadData.setLayoutData(new GridData(GridData.FILL_BOTH));

        SashForm sashComposite = new SashForm(payloadData, SWT.HORIZONTAL);
        GridData layoutData = new GridData(GridData.FILL_BOTH);
        layoutData.horizontalSpan = 2;
        sashComposite.setLayoutData(layoutData);

        Composite generalData = new Composite(sashComposite, SWT.NULL);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        generalData.setLayout(layout);
        generalData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        new Label(generalData, SWT.NULL).setText("Class Name");
        setClassName(new Text(generalData, SWT.BORDER));
        getPayloadClassName().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        setOutputText(new Text(generalData, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL));
        layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.horizontalSpan = 2;
        getOutputText().setLayoutData(layoutData);
        getOutputText().setFont(JFaceResources.getTextFont());
        getOutputText().setEditable(false);
        setPayloadTreeViewer(new TreeViewer(sashComposite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER));
        getPayloadTreeViewer().setContentProvider(new TreeNodeContentProvider());
        return payloadData;
    }

   

  

    public void setClassName(Text className)
    {
        this.payloadClassName = className;
    }

    public Text getPayloadClassName()
    {
        return payloadClassName;
    }

    public void setOutputText(Text outputText)
    {
        this.setPayloadOutputText(outputText);
    }

    public Text getOutputText()
    {
        return getPayloadOutputText();
    }

    public void setPayloadOutputText(Text payloadOutputText)
    {
        this.payloadOutputText = payloadOutputText;
    }

    public Text getPayloadOutputText()
    {
        return payloadOutputText;
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
    public void setPayloadOutput(String paylodOutput)
    {
        getPayloadOutputText().setText(paylodOutput);

    }

    @Override
    public void setPayloadClassName(String className)
    {
        getPayloadClassName().setText(className);
    }

}
