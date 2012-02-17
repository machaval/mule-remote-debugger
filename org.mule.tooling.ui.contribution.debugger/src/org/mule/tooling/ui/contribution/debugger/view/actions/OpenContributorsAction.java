package org.mule.tooling.ui.contribution.debugger.view.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.mule.tooling.ui.contribution.debugger.view.impl.ContributorsDialog;
import org.mule.tooling.ui.contribution.debugger.view.impl.DebuggerImages;

public class OpenContributorsAction extends Action
{

    
    
    public OpenContributorsAction()
    {
        super();
        setImageDescriptor(PlatformUI.getWorkbench()
            .getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_LCL_LINKTO_HELP));
        setToolTipText("Disconnect");
        setText("Disconnect");
        setEnabled(true);
    }

    @Override
    public void run()
    {
       new ContributorsDialog(Display.getCurrent().getActiveShell()).open();
    }

}
