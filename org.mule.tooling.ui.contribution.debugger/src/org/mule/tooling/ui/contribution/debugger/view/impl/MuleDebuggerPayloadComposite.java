
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.mule.tooling.ui.contribution.debugger.view.IPayloadEditor;

public class MuleDebuggerPayloadComposite extends Composite implements IPayloadEditor
{

    private Text payloadOutputText;
    private TreeViewer payloadTreeViewer;
    private Text payloadClassName;
    private Text encoding;
    private Text uniqueId;

    public MuleDebuggerPayloadComposite(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new FillLayout());
        createControl(this);

    }

    protected void createControl(Composite parent)
    {
        createPayloadView(parent);
    }

    protected Composite createPayloadView(Composite parent)
    {
        SashForm payloadProperties = new SashForm(parent, SWT.HORIZONTAL);

        Group generalData = new Group(payloadProperties, SWT.NULL);
        generalData.setText("Payload properties");

        generalData.setLayout(new GridLayout(2, false));
        generalData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        new Label(generalData, SWT.NULL).setText("Class Name");
        setClassName(new Text(generalData, SWT.BORDER));
        getPayloadClassName().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        getPayloadClassName().setEditable(false);
        new Label(generalData, SWT.NULL).setText("Encoding");
        encoding = new Text(generalData, SWT.BORDER);
        encoding.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        encoding.setEditable(false);
        new Label(generalData, SWT.NULL).setText("Id");
        uniqueId = new Text(generalData, SWT.BORDER);
        uniqueId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        uniqueId.setEditable(false);
        new Label(generalData, SWT.NULL).setText("Payload");
        setOutputText(new Text(generalData, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL));
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.horizontalSpan = 2;
        getOutputText().setLayoutData(layoutData);
        getOutputText().setFont(JFaceResources.getTextFont());
        getOutputText().setEditable(false);

        Group treeView = new Group(payloadProperties, SWT.NULL);
        treeView.setText("Tree view");
        FillLayout layout = new FillLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        treeView.setLayout(layout);

        setPayloadTreeViewer(new TreeViewer(treeView, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER));
        getPayloadTreeViewer().getTree().setHeaderVisible(true);
        TreeColumn col = new TreeColumn(getPayloadTreeViewer().getTree(), SWT.NONE);
        col.setText("Name");
        col.setResizable(true);
        col.setWidth(120);
        col = new TreeColumn(getPayloadTreeViewer().getTree(), SWT.NONE);
        col.setText("ClassName");
        col.setWidth(120);
        col.setResizable(true);
        col = new TreeColumn(getPayloadTreeViewer().getTree(), SWT.NONE);
        col.setWidth(120);
        col.setText("Value");
        col.setResizable(true);

        return payloadProperties;
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

    @Override
    public void setEncoding(String encoding)
    {
        this.encoding.setText(encoding);
    }

    @Override
    public void setUniqueId(String uniqueId)
    {
        this.uniqueId.setText(uniqueId);
    }

}
