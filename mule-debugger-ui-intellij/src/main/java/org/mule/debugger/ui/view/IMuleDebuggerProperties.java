package org.mule.debugger.ui.view;

import javax.swing.*;

public interface IMuleDebuggerProperties
{
    JTable getInvocationProperties();

    JTable getSessionProperties();

    JTable getInboundProperties();
}
