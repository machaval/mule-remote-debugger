/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.api.AnnotatedObject;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.response.MessageProcessorInfo;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ObjectFieldDefinition;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MuleMessageInfoBuilder {

    public static MuleMessageInfo createFromMuleMessage(MuleDebuggingContext debuggingContext) {
        MuleMessage message = debuggingContext.getMessage();
        MuleMessageInfo result = new MuleMessageInfo();

        Object messagePayload = message.getPayload();

        result.setPayloadDefinition(ObjectFieldDefinition.createFromObject(messagePayload, "payload"));
        result.setExceptionPayloadDefinition(ObjectFieldDefinition.createFromObject(message.getExceptionPayload(), "exceptionPayload"));
        MessageProcessor messageProcessor = debuggingContext.getMessageProcessor();
        Map<String,String> processorAnnotations = new HashMap<String, String>();
        if(messageProcessor instanceof AnnotatedObject)
        {
            Map<QName, Object> annotations = ((AnnotatedObject) messageProcessor).getAnnotations();
            for (Map.Entry<QName, Object> qNameObjectEntry : annotations.entrySet()) {
                processorAnnotations.put(qNameObjectEntry.getKey().toString(),String.valueOf(qNameObjectEntry.getValue()));
            }
        }
        String processorClassName = messageProcessor.getClass().getName();
        result.setMessageProcessorInfo(new MessageProcessorInfo(processorClassName,processorAnnotations));

        result.setUniqueId(message.getUniqueId());
        result.setEncoding(message.getEncoding());
        result.setMessageRootId(message.getMessageRootId());
        result.setCorrelationId(message.getCorrelationId());


        Set<String> inboundPropertyNames = message.getInboundPropertyNames();
        Map<String, ObjectFieldDefinition> inboundProperties = new HashMap<String, ObjectFieldDefinition>();
        for (String inboundPropertyName : inboundPropertyNames) {
            Object inboundProperty = message.getInboundProperty(inboundPropertyName);
            inboundProperties.put(inboundPropertyName, ObjectFieldDefinition.createFromObject(inboundProperty, inboundPropertyName));
        }
        result.setInboundProperties(inboundProperties);
        Set<String> invocationPropertyNames = message.getInvocationPropertyNames();
        Map<String, ObjectFieldDefinition> invocationProperties = new HashMap<String, ObjectFieldDefinition>();
        for (String invocationPropertyName : invocationPropertyNames) {
            invocationProperties.put(invocationPropertyName, ObjectFieldDefinition.createFromObject(message.getInvocationProperty(invocationPropertyName), invocationPropertyName));
        }
        result.setInvocationProperties(invocationProperties);
        Map<String, ObjectFieldDefinition> sessionProperties = new HashMap<String, ObjectFieldDefinition>();
        Set<String> sessionPropertyNames = message.getSessionPropertyNames();
        for (String sessionPropertyName : sessionPropertyNames) {
            sessionProperties.put(sessionPropertyName, ObjectFieldDefinition.createFromObject(message.getSessionProperty(sessionPropertyName),sessionPropertyName));
        }
        result.setSessionProperties(sessionProperties);
        Map<String, ObjectFieldDefinition> outboundProperties = new HashMap<String, ObjectFieldDefinition>();
        Set<String> outboundPropertyNames = message.getOutboundPropertyNames();
        for (String outboundPropertyName : outboundPropertyNames) {
            outboundProperties.put(outboundPropertyName, ObjectFieldDefinition.createFromObject(message.getOutboundProperty(outboundPropertyName), outboundPropertyName));
        }
        result.setOutboundProperties(outboundProperties);


        return result;
    }
}
