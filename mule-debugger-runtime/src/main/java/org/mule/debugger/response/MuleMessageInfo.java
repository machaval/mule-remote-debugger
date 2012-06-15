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


    private String uniqueId;
    private String correlationId;
    private String messageRootId;
    private String encoding;
    private Map<String, ObjectFieldDefinition> inboundProperties;
    private Map<String, ObjectFieldDefinition> invocationProperties;
    private Map<String, ObjectFieldDefinition> sessionProperties;
    private Map<String, ObjectFieldDefinition> outboundProperties;

    private MessageProcessorInfo messageProcessorInfo;


    public MessageProcessorInfo getMessageProcessorInfo() {
        return messageProcessorInfo;
    }

    public void setMessageProcessorInfo(MessageProcessorInfo messageProcessorInfo) {
        this.messageProcessorInfo = messageProcessorInfo;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Map<String, ObjectFieldDefinition> getInboundProperties() {
        return inboundProperties;
    }

    public Map<String, ObjectFieldDefinition> getInvocationProperties() {
        return invocationProperties;
    }

    public String getEncoding() {
        return encoding;
    }

    public Map<String, ObjectFieldDefinition> getSessionProperties() {
        return sessionProperties;
    }




    public void setPayloadDefinition(ObjectFieldDefinition payloadDefinition) {
        this.payloadDefinition = payloadDefinition;
    }

    public ObjectFieldDefinition getPayloadDefinition() {
        return payloadDefinition;
    }


    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setInboundProperties(Map<String, ObjectFieldDefinition> inboundProperties) {
        this.inboundProperties = inboundProperties;
    }

    public void setInvocationProperties(Map<String, ObjectFieldDefinition> invocationProperties) {
        this.invocationProperties = invocationProperties;
    }

    public void setSessionProperties(Map<String, ObjectFieldDefinition> sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    public ObjectFieldDefinition getExceptionPayloadDefinition() {
        return exceptionPayloadDefinition;
    }

    public void setExceptionPayloadDefinition(ObjectFieldDefinition exceptionPayloadDefinition) {
        this.exceptionPayloadDefinition = exceptionPayloadDefinition;
    }

    public Map<String, ObjectFieldDefinition> getOutboundProperties() {
        return outboundProperties;
    }

    public void setOutboundProperties(Map<String, ObjectFieldDefinition> outboundProperties) {
        this.outboundProperties = outboundProperties;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessageRootId() {
        return messageRootId;
    }

    public void setMessageRootId(String messageRootId) {
        this.messageRootId = messageRootId;
    }
}
