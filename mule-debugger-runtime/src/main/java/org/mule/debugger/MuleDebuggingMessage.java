/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger;

import java.util.Map;

public class MuleDebuggingMessage
{
    private Object payload;
    private Map<String, Object> inboundHeaders;


    public MuleDebuggingMessage(Object payload, Map<String, Object> inboundHeaders)
    {
        this.payload = payload;
        this.inboundHeaders = inboundHeaders;

    }

    public Object getPayload()
    {
        return payload;
    }

    public void setPayload(Object payload)
    {
        this.payload = payload;
    }

    public Map<String, Object> getInboundHeaders()
    {
        return inboundHeaders;
    }

    public void setInboundHeaders(Map<String, Object> inboundHeaders)
    {
        this.inboundHeaders = inboundHeaders;
    }


}
