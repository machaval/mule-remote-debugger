
package org.mule.tooling.ui.contribution.debugger.ui;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.mule.tooling.ui.contribution.debugger.ui.actions.ConnectAction;
import org.mule.tooling.ui.contribution.debugger.ui.actions.NextMessageAction;

public class MuleDebuggerView extends ViewPart
{

    @Override
    public void createPartControl(Composite parent)
    {
        createToolBar();
        MuleDebuggerComposite muleDebuggerComposite = new MuleDebuggerComposite(parent, SWT.NULL);
    }

    public void createToolBar()
    {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(new ConnectAction());
        mgr.add(new NextMessageAction());
    }

    @Override
    public void setFocus()
    {

    }

}
