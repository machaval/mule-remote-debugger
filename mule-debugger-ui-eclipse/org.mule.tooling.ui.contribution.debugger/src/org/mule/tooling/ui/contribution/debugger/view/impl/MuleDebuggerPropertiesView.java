
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerProperties;

public class MuleDebuggerPropertiesView extends Composite implements IMuleDebuggerProperties
{

    private ObjectFieldDefinitionComposite inboudProperties;
    private ObjectFieldDefinitionComposite invocationProperties;
    private ObjectFieldDefinitionComposite sessionProperties;
    private ObjectFieldDefinitionComposite outboundProperties;

    public MuleDebuggerPropertiesView(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new FillLayout());
        createControl(this);
    }

    protected void createControl(Composite parent)
    {

        TabFolder propertiesFolder = new TabFolder(parent, SWT.NULL);
        setInboudProperties(createTreeTable(propertiesFolder));
        setInvocationProperties(createTreeTable(propertiesFolder));
        setSessionProperties(createTreeTable(propertiesFolder));
        setOutboundProperties(createTreeTable(propertiesFolder));

        TabItem item = new TabItem(propertiesFolder, SWT.NULL);
        item.setText("Inbound");
        item.setControl(getInboudProperties());
        item = new TabItem(propertiesFolder, SWT.NULL);
        item.setText("Flow");
        item.setControl(getInvocationProperties());
        item = new TabItem(propertiesFolder, SWT.NULL);
        item.setText("Session");
        item.setControl(getSessionProperties());
        item = new TabItem(propertiesFolder, SWT.NULL);
        item.setText("Outbound");
        item.setControl(getOutboundProperties());

    }

    /**
     * Create the Table
     */
    private ObjectFieldDefinitionComposite createTreeTable(final Composite parent)
    {
        return new ObjectFieldDefinitionComposite(parent, SWT.NULL);
    }

    public void setInboudProperties(ObjectFieldDefinitionComposite inboudProperties)
    {
        this.inboudProperties = inboudProperties;
    }

    public ObjectFieldDefinitionComposite getInboudProperties()
    {
        return inboudProperties;
    }

    public void setInvocationProperties(ObjectFieldDefinitionComposite invocationProperties)
    {
        this.invocationProperties = invocationProperties;
    }

    public ObjectFieldDefinitionComposite getInvocationProperties()
    {
        return invocationProperties;
    }

    public void setSessionProperties(ObjectFieldDefinitionComposite sessionProperties)
    {
        this.sessionProperties = sessionProperties;
    }

    public ObjectFieldDefinitionComposite getSessionProperties()
    {
        return sessionProperties;
    }

    public void setOutboundProperties(ObjectFieldDefinitionComposite outboundProperties)
    {
        this.outboundProperties = outboundProperties;
    }

    public ObjectFieldDefinitionComposite getOutboundProperties()
    {
        return outboundProperties;
    }

}
