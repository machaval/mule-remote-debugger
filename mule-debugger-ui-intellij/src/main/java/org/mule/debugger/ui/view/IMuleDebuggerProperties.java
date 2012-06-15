package org.mule.debugger.ui.view;

import com.intellij.ui.AnActionButtonRunnable;

import javax.swing.*;

public interface IMuleDebuggerProperties {
    JTable getInvocationProperties();

    JTable getSessionProperties();

    JTable getInboundProperties();

    JTable getOutboundProperties();

    void setAddInvocation(AnActionButtonRunnable addInvocation);

    void setRemoveInvocation(AnActionButtonRunnable removeInvocation);

    void setAddSession(AnActionButtonRunnable addSession);

    void setRemoveSession(AnActionButtonRunnable removeSession);

    void setAddOutbound(AnActionButtonRunnable addOutbound);

    void setRemoveOutbound(AnActionButtonRunnable removeOutbound);
}
