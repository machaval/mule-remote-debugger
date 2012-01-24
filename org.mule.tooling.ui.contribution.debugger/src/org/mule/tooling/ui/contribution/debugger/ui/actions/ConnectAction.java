
package org.mule.tooling.ui.contribution.debugger.ui.actions;

import org.eclipse.jface.action.Action;
import org.mule.tooling.ui.contribution.debugger.ui.DebuggerImages;

public class ConnectAction extends Action
{

    public ConnectAction()
    {
        super();
        setImageDescriptor(DebuggerImages.IMG_CONNECT);
        setToolTipText("Connect");
        setText("Connect");
    }

}
