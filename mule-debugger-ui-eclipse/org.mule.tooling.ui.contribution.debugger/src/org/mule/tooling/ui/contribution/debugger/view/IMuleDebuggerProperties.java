package org.mule.tooling.ui.contribution.debugger.view;

import org.eclipse.jface.viewers.TableViewer;

public interface IMuleDebuggerProperties
{
    TableViewer getInvocationProperties();
    
    TableViewer getSessionProperties();
    
    TableViewer getInboudProperties();
}
