
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class ContributorsDialog extends TitleAreaDialog
{

    public ContributorsDialog(Shell parentShell)
    {
        super(parentShell);
        // TODO Auto-generated constructor stub
    }

    protected Control createDialogArea(Composite parent)
    {
        setTitle("Debugger contributors");
        setMessage("Thanks to this guys you can enjoy the debugging experience");
        Group contributors = new Group(parent, SWT.NULL);
        contributors.setLayout(new FillLayout(SWT.VERTICAL));
        contributors.setLayoutData(new GridData(GridData.FILL_BOTH));
        CLabel author = new CLabel(contributors, SWT.NULL);
        author.setText("Author : Mariano Achaval");
        author.setImage(DebuggerImages.getDebuggerImages().getImage("mariano"));

        CLabel buildManager = new CLabel(contributors, SWT.NULL);
        buildManager.setText("Build Manager : Alberto Pose");
        buildManager.setImage(DebuggerImages.getDebuggerImages().getImage("alberto"));

        CLabel expressionSupport = new CLabel(contributors, SWT.NULL);
        expressionSupport.setText("Expression support : Santiago Vacas");
        expressionSupport.setImage(DebuggerImages.getDebuggerImages().getImage("santiago"));

        CLabel muleRuntimeSupport = new CLabel(contributors, SWT.NULL);
        muleRuntimeSupport.setText("Mule runtime support : Julien Eluard");
        muleRuntimeSupport.setImage(DebuggerImages.getDebuggerImages().getImage("julien"));

        CLabel studioSupport = new CLabel(contributors, SWT.NULL);
        studioSupport.setText("Studio support : Jorge D'Alessandro");
        studioSupport.setImage(DebuggerImages.getDebuggerImages().getImage("jorge"));

        CLabel productManager = new CLabel(contributors, SWT.NULL);
        productManager.setText("Product manager : Alberto Aresca");
        productManager.setImage(DebuggerImages.getDebuggerImages().getImage("tano"));

        return contributors;
    }

    @Override
    protected boolean isResizable()
    {
        return true;
    }

    @Override
    protected Point getInitialSize()
    {
        return new Point(300, 460);
    }

}
