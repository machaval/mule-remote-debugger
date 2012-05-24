
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerProperties;

public class MuleDebuggerPropertiesView extends Composite implements IMuleDebuggerProperties
{

    private static final String[] COLUMN_PROPERTIES = new String[]{"Name", "Value"};
    private TableViewer inboudProperties;
    private TableViewer invocationProperties;
    private TableViewer sessionProperties;

    public MuleDebuggerPropertiesView(Composite parent, int style)
    {
        super(parent, style);
        this.setLayout(new FillLayout());
        createControl(this);
    }

    protected void createControl(Composite parent)
    {

        SashForm properties = new SashForm(parent, SWT.HORIZONTAL);
        Group inbound = new Group(properties, SWT.NULL);
        inbound.setLayout(new FillLayout());
        inbound.setText("Inbound");
        setInboudProperties(createTable(inbound));
        Group invocation = new Group(properties, SWT.NULL);
        invocation.setLayout(new FillLayout());
        invocation.setText("Variables");
        setInvocationProperties(createTable(invocation));
        Group session = new Group(properties, SWT.NULL);
        session.setLayout(new FillLayout());
        session.setText("Session");
        setSessionProperties(createTable(session));

    }

    /**
     * Create the Table
     */
    private TableViewer createTable(final Composite parent)
    {
        int style = SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;

        Table table = new Table(parent, style);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        // 2nd column with task Description
        final TableColumn nameColumn;
        nameColumn = new TableColumn(table, SWT.LEFT, 0);
        nameColumn.setText("Name");
        nameColumn.setWidth(100);
        final TableColumn valueColumn;
        valueColumn = new TableColumn(table, SWT.LEFT, 0);
        valueColumn.setText("Value");
        valueColumn.setWidth(100);
        // column.setResizable(false);

        TableViewer tableViewer = new TableViewer(table);
        
        tableViewer.setColumnProperties(COLUMN_PROPERTIES);

        return tableViewer;

    }

    public void setInboudProperties(TableViewer inboudProperties)
    {
        this.inboudProperties = inboudProperties;
    }

    public TableViewer getInboudProperties()
    {
        return inboudProperties;
    }

    public void setInvocationProperties(TableViewer invocationProperties)
    {
        this.invocationProperties = invocationProperties;
    }

    public TableViewer getInvocationProperties()
    {
        return invocationProperties;
    }

    public void setSessionProperties(TableViewer sessionProperties)
    {
        this.sessionProperties = sessionProperties;
    }

    public TableViewer getSessionProperties()
    {
        return sessionProperties;
    }

}
