
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.mule.tooling.ui.contribution.debugger.view.IPayloadEditor;

public class MuleDebuggerPayloadComposite extends Composite implements IPayloadEditor
{

    private Text valueText;
    private TreeViewer payloadTreeViewer;
    private Link className;
    private Text encoding;
    private Text uniqueId;
    private Link transformer;

    public MuleDebuggerPayloadComposite(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new GridLayout());
        createControl(this);

    }

    protected void createControl(Composite parent)
    {

       
        SashForm payloadProperties = new SashForm(parent, SWT.HORIZONTAL);
        payloadProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Group treeView = new Group(payloadProperties, SWT.NULL);
        treeView.setText("Payload");
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

        Group selectionDetails = new Group(payloadProperties, SWT.NULL);
        selectionDetails.setText("Selection details");
        selectionDetails.setLayout(new GridLayout(2, false));
        selectionDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        new Label(selectionDetails, SWT.NULL).setText("Class Name");
        setClassName(new Link(selectionDetails, SWT.NULL));
        getClassName().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        setValueText(new Text(selectionDetails, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL));
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.horizontalSpan = 2;
        getValueText().setLayoutData(layoutData);
        getValueText().setFont(JFaceResources.getTextFont());
        getValueText().setEditable(false);
        
        Group generalData = new Group(parent, SWT.NULL);
        generalData.setText("Message properties");
        generalData.setLayout(new GridLayout(6, false));
        generalData.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        new Label(generalData, SWT.NULL).setText("Message processor");
        transformer = new Link(generalData, SWT.NULL);
        transformer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        new Label(generalData, SWT.NULL).setText("Id");
        uniqueId = new Text(generalData, SWT.BORDER);
        uniqueId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        uniqueId.setEditable(false);

        new Label(generalData, SWT.NULL).setText("Encoding");
        encoding = new Text(generalData, SWT.BORDER);
        encoding.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        encoding.setEditable(false);


    }

    public void setClassName(Link className)
    {
        this.className = className;
    }

    public Link getClassName()
    {
        return className;
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
        getValueText().setText(value);

    }

    @Override
    public void setSelectionClassName(String className)
    {
        getClassName().setText(className);
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

    @Override
    public void setCurrentProcessor(String transformerName)
    {
        transformer.setText(transformerName);

    }

    public Link getTransformer()
    {
        return transformer;
    }

    public void setTransformer(Link transformer)
    {
        this.transformer = transformer;
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
