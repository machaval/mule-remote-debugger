package org.mule.debugger.ui.view;

import com.intellij.ui.AnActionButtonRunnable;

import javax.swing.*;

public interface IMuleDebuggerProperties {
    IObjectFieldDefinitionEditor getInvocationProperties();

    IObjectFieldDefinitionEditor getSessionProperties();

    IObjectFieldDefinitionEditor getInboudProperties();

    IObjectFieldDefinitionEditor getOutboundProperties();
}
