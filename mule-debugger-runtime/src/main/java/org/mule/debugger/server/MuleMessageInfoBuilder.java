/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.api.MuleMessage;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MuleMessageInfoBuilder {

    public static MuleMessageInfo createFromMuleMessage(MuleMessage message) {
        MuleMessageInfo result = new MuleMessageInfo();

        Object messagePayload = message.getPayload();

        result.setPayloadDefinition(ObjectFieldDefinition.createFromObject(messagePayload, "payload"));
        result.setExceptionPayloadDefinition(ObjectFieldDefinition.createFromObject(message.getExceptionPayload(), "exceptionPayload"));

        result.setPayloadString(String.valueOf(messagePayload));
        result.setUniqueId(message.getUniqueId());
        result.setEncoding(message.getEncoding());
        result.setPayloadClassName(messagePayload.getClass().getCanonicalName());
        Set<String> inboundPropertyNames = message.getInboundPropertyNames();
        Map<String, String> inboundProperties = new HashMap<String, String>();
        for (String inboundPropertyName : inboundPropertyNames) {
            Object inboundProperty = message.getInboundProperty(inboundPropertyName);
            inboundProperties.put(inboundPropertyName, String.valueOf(inboundProperty));
        }
        result.setInboundProperties(inboundProperties);
        Set<String> invocationPropertyNames = message.getInvocationPropertyNames();
        Map<String, String> invocationProperties = new HashMap<String, String>();
        for (String invocationPropertyName : invocationPropertyNames) {
            invocationProperties.put(invocationPropertyName, String.valueOf(message.getInvocationProperty(invocationPropertyName)));
        }
        result.setInvocationProperties(invocationProperties);
        Map<String, String> sessionProperties = new HashMap<String, String>();
        Set<String> sessionPropertyNames = message.getSessionPropertyNames();
        for (String sessionPropertyName : sessionPropertyNames) {
            sessionProperties.put(sessionPropertyName, String.valueOf(message.getSessionProperty(sessionPropertyName)));
        }
        result.setSessionProperties(sessionProperties);


        return result;
    }
}
