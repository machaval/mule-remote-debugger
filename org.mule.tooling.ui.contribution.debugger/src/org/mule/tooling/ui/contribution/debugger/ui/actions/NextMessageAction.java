
package org.mule.tooling.ui.contribution.debugger.ui.actions;

import org.eclipse.jface.action.Action;
import org.mule.tooling.ui.contribution.debugger.ui.DebuggerImages;

public class NextMessageAction extends Action
{

    public NextMessageAction()
    {
        super();
        //
        setImageDescriptor(DebuggerImages.IMG_NEXT);
        setToolTipText("Debugg Message");
        setText("Debugg Message");
    }

}
