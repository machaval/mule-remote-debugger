/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import java.io.Serializable;
import java.util.Map;

public class MuleMessageInfo implements Serializable {

    private ObjectFieldDefinition payloadDefinition;
    private ObjectFieldDefinition exceptionPayloadDefinition;

    private String payloadClassName;
    private String payloadString;
    private String uniqueId;
    private String encoding;
    private Map<String, String> inboundProperties;
    private Map<String, String> invocationProperties;
    private Map<String, String> sessionProperties;


    public String getPayloadString() {
        return payloadString;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Map<String, String> getInboundProperties() {
        return inboundProperties;
    }

    public Map<String, String> getInvocationProperties() {
        return invocationProperties;
    }

    public String getEncoding() {
        return encoding;
    }

    public Map<String, String> getSessionProperties() {
        return sessionProperties;
    }

    public String getPayloadClassName() {
        return payloadClassName;
    }


    public void setPayloadDefinition(ObjectFieldDefinition payloadDefinition) {
        this.payloadDefinition = payloadDefinition;
    }

    public ObjectFieldDefinition getPayloadDefinition() {
        return payloadDefinition;
    }

    public void setPayloadClassName(String payloadClassName) {
        this.payloadClassName = payloadClassName;
    }

    public void setPayloadString(String payloadString) {
        this.payloadString = payloadString;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setInboundProperties(Map<String, String> inboundProperties) {
        this.inboundProperties = inboundProperties;
    }

    public void setInvocationProperties(Map<String, String> invocationProperties) {
        this.invocationProperties = invocationProperties;
    }

    public void setSessionProperties(Map<String, String> sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    public ObjectFieldDefinition getExceptionPayloadDefinition() {
        return exceptionPayloadDefinition;
    }

    public void setExceptionPayloadDefinition(ObjectFieldDefinition exceptionPayloadDefinition) {
        this.exceptionPayloadDefinition = exceptionPayloadDefinition;
    }
}
