
package org.mule.tooling.ui.contribution.debugger.ui;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class MuleDebuggerComposite extends Composite
{

    private Text url;
    private Spinner port;
    private Text outputText;
    private TreeViewer treeViewer;
    private Text className;

    public MuleDebuggerComposite(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new GridLayout());
        createControl(this);

    }

    protected void createControl(Composite parent)
    {
        createConnectionProperties(parent);

        createPayloadView(parent);
    }

    protected void createPayloadView(Composite parent)
    {
        Group payloadData = new Group(parent, SWT.NULL);
        payloadData.setLayout(new GridLayout(2, false));
        payloadData.setText("Payload");
        payloadData.setLayoutData(new GridData(GridData.FILL_BOTH));
        new Label(payloadData, SWT.NULL).setText("Class Name");
        className = new Text(payloadData, SWT.BORDER);
        className.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        TabFolder outputTabs = new TabFolder(payloadData, SWT.TOP);
        GridDataFactory.fillDefaults()
            .align(SWT.FILL, SWT.FILL)
            .grab(true, true)
            .span(2, 0)
            .applyTo(outputTabs);

        TabItem item = new TabItem(outputTabs, SWT.NONE);
        outputText = new Text(outputTabs, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        outputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        outputText.setFont(JFaceResources.getTextFont());
        outputText.setEditable(false);
        item.setControl(outputText);
        item.setText("Text");

        item = new TabItem(outputTabs, SWT.NONE);
        treeViewer = new TreeViewer(outputTabs, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        treeViewer.setContentProvider(new TreeNodeContentProvider());
        item.setControl(treeViewer.getControl());
        item.setText("Data structure");
    }

    protected void createConnectionProperties(Composite parent)
    {
        Composite connectionProperties = new Composite(parent, SWT.NULL);
        connectionProperties.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        connectionProperties.setLayout(new GridLayout(4, false));
        new Label(connectionProperties, SWT.NULL).setText("URL");
        url = new Text(connectionProperties, SWT.BORDER);
        url.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        new Label(connectionProperties, SWT.NULL).setText("Port");
        port = new Spinner(connectionProperties, SWT.BORDER);
        port.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

}
