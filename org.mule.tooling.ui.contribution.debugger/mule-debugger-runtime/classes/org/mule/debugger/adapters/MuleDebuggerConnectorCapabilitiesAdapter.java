
package org.mule.debugger.adapters;

import org.mule.api.Capabilities;
import org.mule.api.Capability;


/**
 * A <code>MuleDebuggerConnectorCapabilitiesAdapter</code> is a wrapper around {@link org.mule.debugger.MuleDebuggerConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
public class MuleDebuggerConnectorCapabilitiesAdapter
    extends org.mule.debugger.MuleDebuggerConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
