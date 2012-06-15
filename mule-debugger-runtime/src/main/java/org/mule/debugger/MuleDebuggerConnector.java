/**
 * Mule Development Kit
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.debugger;


import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Stop;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.context.notification.MessageProcessorNotificationListener;
import org.mule.context.notification.MessageProcessorNotification;
import org.mule.debugger.server.DebuggerAgent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The mule debugger allows to inspect the content of the mule message, and go through all the message processor on the flow.
 *
 * @author MuleSoft, Inc.
 */
@Module(name = "debugger", description = "Mule Debugger" )
public class MuleDebuggerConnector {


    private transient static Logger logger = Logger.getLogger(MuleDebuggerConnector.class.getName());


    private DebuggerAgent agent;

    @Inject
    private MuleContext context;

    private MessageProcessorNotificationListener<MessageProcessorNotification> listener;

    public MuleDebuggerConnector() {
    }


    @PostConstruct
    public void initialize() {
        //This code should be removed when the agent is implemented with the mule framework
        if(agent != null){
            return;
        }
        agent = new DebuggerAgent();
        try {
            agent.start();
            listener = agent.registerForApplicationNotifications(context);

        } catch (MuleException e) {
            logger.log(Level.WARNING, "Error", e);
        }

    }

    @Stop
    public void shutdown() {
        context.unregisterListener(listener);
        try {
            agent.stop();
        } catch (MuleException e) {
            logger.log(Level.WARNING, "Error", e);
        }
    }


    /**
     * Debug the payload content
     * <p/>
     * {@sample.xml ../../../doc/MuleDebugger-connector.xml.sample debugger:breakpoint}
     *
     * @param message   The mule message
     * @param condition The conditional expression
     * @return The payload
     */
    @Processor
    public Object breakpoint(MuleMessage message, @Optional @Default("true") Boolean condition) {


        if (agent.getHandler().isClientConnected()) {

            boolean debug = true;
            if (condition != null) {
                debug = condition;
            }
            if (debug) {
                agent.getHandler().breakPoint(message);
            }
        }

        return message.getPayload();
    }


    public void setContext(MuleContext context) {
        this.context = context;
    }


}
