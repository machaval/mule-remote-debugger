/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger;

import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.processor.MessageProcessor;

public class MuleDebuggingContext {


    private MuleMessage message;
    private ExpressionManager expressionManager;
    private ClassLoader contextClassLoader;
    private Class messageProcessor;


    public MuleDebuggingContext(MuleMessage message, ExpressionManager manager, ClassLoader contextClassLoader, Class messageProcessor) {
        this.message = message;
        this.expressionManager = manager;
        this.contextClassLoader = contextClassLoader;
        this.messageProcessor = messageProcessor;
    }

    public Object getPayload() {
        return message.getPayload();
    }


    public MuleMessage getMessage() {
        return message;
    }

    public ExpressionManager getExpressionManager() {
        return expressionManager;
    }


    public ClassLoader getContextClassLoader() {
        return contextClassLoader;
    }


    public Class getMessageProcessor() {
        return messageProcessor;
    }
}
