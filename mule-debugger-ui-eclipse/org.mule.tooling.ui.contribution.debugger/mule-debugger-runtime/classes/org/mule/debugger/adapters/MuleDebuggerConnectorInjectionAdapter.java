
package org.mule.debugger.adapters;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.debugger.MuleDebuggerConnector;


/**
 * A <code>MuleDebuggerConnectorInjectionAdapter</code> is a wrapper around {@link MuleDebuggerConnector } that allows the injection of several Mule facilities when a MuleContext is set.
 * 
 */
public class MuleDebuggerConnectorInjectionAdapter
    extends MuleDebuggerConnectorLifecycleAdapter
    implements MuleContextAware
{


    @Override
    public void setMuleContext(MuleContext context) {
        super.setContext(context);
        super.setExpressionManager(context.getExpressionManager());
    }

}
