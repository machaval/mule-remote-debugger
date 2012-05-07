
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.mule.tooling.ui.contribution.debugger.view.IConnectionPropertiesEditor;

public class ConnectionPropertiesEditorComposite extends Composite implements IConnectionPropertiesEditor
{

    private Text url;
    private Spinner port;

    public ConnectionPropertiesEditorComposite(Composite parent, int style)
    {
        super(parent, style);

        createControl(this);

    }

    protected void createControl(Composite parent)
    {
        parent.setLayout(new GridLayout());
        Group group = new Group(parent, SWT.NULL);
        group.setText("Connection properties");
        group.setLayout(new GridLayout(4, false));
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.verticalAlignment = SWT.CENTER;
        gridData.grabExcessVerticalSpace = true;
        group.setLayoutData(gridData);
        new Label(group, SWT.NULL).setText("URL");
        url = new Text(group, SWT.BORDER);
        url.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        new Label(group, SWT.NULL).setText("Port");
        port = new Spinner(group, SWT.BORDER);
        port.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        port.setMaximum(9999);
    }

    @Override
    public String getURL()
    {
        return url.getText();
    }

    @Override
    public int getPort()
    {

        return Integer.parseInt(port.getText());
    }

    @Override
    public void setURL(String url)
    {
        //
        this.url.setText(url);

    }

    @Override
    public void setPort(int port)
    {
        //
        this.port.setSelection(port);

    }

}
