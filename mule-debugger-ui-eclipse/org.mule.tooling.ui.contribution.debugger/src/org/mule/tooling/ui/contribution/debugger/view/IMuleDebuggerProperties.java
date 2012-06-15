package org.mule.tooling.ui.contribution.debugger.view;

import org.mule.tooling.ui.contribution.debugger.view.impl.ObjectFieldDefinitionComposite;

public interface IMuleDebuggerProperties
{
    ObjectFieldDefinitionComposite getInvocationProperties();
    
    ObjectFieldDefinitionComposite getSessionProperties();
    
    ObjectFieldDefinitionComposite getInboudProperties();
    
    ObjectFieldDefinitionComposite getOutboundProperties();
}
