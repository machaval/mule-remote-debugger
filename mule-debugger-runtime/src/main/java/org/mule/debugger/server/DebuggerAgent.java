/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.server;

import org.mule.AbstractAgent;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.ExceptionNotificationListener;
import org.mule.api.context.notification.MessageProcessorNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.api.context.notification.ServerNotificationListener;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.context.notification.ExceptionNotification;
import org.mule.context.notification.MessageProcessorNotification;
import org.mule.context.notification.NotificationException;
import org.mule.context.notification.ServerNotificationManager;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.remote.RemoteDebuggerService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class DebuggerAgent extends AbstractAgent {

    public static final String MULE_DEBUG_PORT = "mule.debug.port";
    public static final String MULE_DEBUG_SUSPEND = "mule.debug.suspend";

    private transient static Logger logger = Logger.getLogger(DebuggerAgent.class.getName());
   //Change this for a SET
    private static final List<String> MESSAGE_PROCESSOR_NAMES_BLACK_LIST = Arrays.asList(
            "com.mulesoft.mule.tracking.event.EventMessageProcessor",
            "com.mulesoft.mule.tracking.event.TransactionMessageProcessor",
            "org.mule.api.processor.MessageProcessors$LifecyleAwareMessageProcessorWrapper",
            "org.mule.construct.AbstractFlowConstruct$1$1",
            "org.mule.construct.AbstractPipeline$ProcessIfPipelineStartedMessageProcessor",
            "org.mule.construct.processor.FlowConstructStatisticsMessageProcessor",
            "org.mule.construct.AbstractPipeline$1",
            "org.mule.component.AbstractComponent$1$1",
            "org.mule.endpoint.DefaultOutboundEndpoint",
            "org.mule.endpoint.inbound.InboundLoggingMessageProcessor",
            "org.mule.endpoint.inbound.InboundNotificationMessageProcessor",
            "org.mule.endpoint.inbound.InboundEndpointPropertyMessageProcessor",
            "org.mule.endpoint.inbound.InboundEndpointMimeTypeCheckingMessageProcessor",
            "org.mule.endpoint.inbound.InboundExceptionDetailsMessageProcessor",
            "org.mule.endpoint.outbound.OutboundEndpointPropertyMessageProcessor",
            "org.mule.endpoint.outbound.OutboundEndpointMimeTypeCheckingMessageProcessor",
            "org.mule.endpoint.outbound.OutboundEventTimeoutMessageProcessor",
            "org.mule.endpoint.outbound.OutboundExceptionDetailsMessageProcessor",
            "org.mule.endpoint.outbound.OutboundLoggingMessageProcessor",
            "org.mule.endpoint.outbound.OutboundNotificationMessageProcessor",
            "org.mule.endpoint.outbound.OutboundResponsePropertiesMessageProcessor",
            "org.mule.endpoint.outbound.OutboundRewriteResponseEventMessageProcessor",
            "org.mule.endpoint.outbound.OutboundRootMessageIdPropertyMessageProcessor",
            "org.mule.endpoint.outbound.OutboundSessionHandlerMessageProcessor",
            "org.mule.interceptor.LoggingInterceptor",
            "org.mule.interceptor.ProcessingTimeInterceptor",
            "org.mule.lifecycle.processor.ProcessIfStartedMessageProcessor",
            "org.mule.lifecycle.processor.ProcessIfStartedWaitIfPausedMessageProcessor",
            "org.mule.lifecycle.processor.ProcessIfStartedWaitIfSyncPausedMessageProcessor",
            "org.mule.module.cxf.config.FlowConfiguringMessageProcessor",
            "org.mule.processor.AsyncInterceptingMessageProcessor",
            "org.mule.processor.OptionalAsyncInterceptingMessageProcessor",
            "org.mule.processor.EndpointTransactionalInterceptingMessageProcessor",
            "org.mule.processor.TransactionalInterceptingMessageProcessor",
            "org.mule.processor.ExceptionHandlingMessageProcessor",
            "org.mule.processor.LaxAsyncInterceptingMessageProcessor",
            "org.mule.processor.LaxSedaStageInterceptingMessageProcessor",
            "org.mule.processor.SedaStageInterceptingMessageProcessor",
            "org.mule.processor.chain.DefaultMessageProcessorChain",
            "org.mule.processor.chain.InterceptingChainLifecycleWrapper",
            "org.mule.processor.chain.InterceptingChainLifecycleWrapper$1",
            "org.mule.routing.requestreply.AsyncReplyToPropertyRequestReplyReplier",
            "org.mule.routing.requestreply.ReplyToParameterProcessor",
            "org.mule.routing.requestreply.ReplyToPropertyRequestReplyReplier",
            "org.mule.service.processor.ServiceLoggingMessageProcessor",
            "org.mule.service.processor.ServiceInternalMessageProcessor",
            "org.mule.source.StartableCompositeMessageSource$InternalMessageProcessor",
            "org.mule.transport.AbstractConnector$DispatcherMessageProcessor"
    );

    private RemoteDebuggerService server;
    private DebuggerHandler handler;


    public DebuggerAgent() {
        super("Debugger Agent");
    }

    public void dispose() {

    }

    public void initialise() throws InitialisationException {

    }

    public void start() throws MuleException {
        //maybe we can start only if a system property was specified to avoid perfomance issues
        startDebuggerService();
    }

    public void stop() throws MuleException {
        if (getHandler() != null) {
            getHandler().disconnectClient();
            getServer().stopService();
        }
    }


    private void startDebuggerService() {
        setHandler(new DebuggerHandler());
        final CountDownLatch suspendLatch = new CountDownLatch(1);
        boolean suspend = Boolean.getBoolean(MULE_DEBUG_SUSPEND);
        logger.info("Suspend property is " + suspend);
        if (suspend) {
            getHandler().addListener(new IDebuggerServiceListener() {
                public void onStart() {
                    suspendLatch.countDown();
                }

                public void onStop() {

                }
            });
        }
        setServer(new RemoteDebuggerService(Integer.getInteger(MULE_DEBUG_PORT, 6666), getHandler()));
        getServer().startService();


        if (suspend) {
            try {
                System.out.println("Waiting for client to connect");
                suspendLatch.await();
            } catch (InterruptedException e) {

            }
            logger.info("Debugger started");
        }
        //We need to invoke this listener for every app that is deployed
        //registerForApplicationNotifications(null);
    }

    public MessageProcessorNotificationListener<MessageProcessorNotification> registerForApplicationNotifications(MuleContext context) {
        try {
            final ServerNotificationManager notificationManager = context.getNotificationManager();
            if (!notificationManager.isNotificationDynamic()) {
                notificationManager.setNotificationDynamic(true);
            }
            registerNotificationType(notificationManager, MuleMessageDebuggerListener.class, MessageProcessorNotification.class);

            final MuleMessageDebuggerListener muleMessageDebuggerListener = new MuleMessageDebuggerListener(context);
            context.registerListener(muleMessageDebuggerListener);
            return muleMessageDebuggerListener;
        } catch (NotificationException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected final void registerNotificationType(final ServerNotificationManager notificationManager,
                                                  @SuppressWarnings("rawtypes") final Class<? extends ServerNotificationListener> listenerType,
                                                  final Class<? extends ServerNotification> notificationType) {
        @SuppressWarnings("rawtypes")
        final Map<Class<? extends ServerNotificationListener>, Set<Class<? extends ServerNotification>>> mapping = notificationManager.getInterfaceToTypes();
        if (!mapping.containsKey(listenerType)) {
            notificationManager.addInterfaceToType(listenerType, notificationType);
        }
    }

    public RemoteDebuggerService getServer() {
        return server;
    }

    private void setServer(RemoteDebuggerService server) {
        this.server = server;
    }

    private void setHandler(DebuggerHandler handler) {
        this.handler = handler;
    }


    private class MuleMessageDebuggerListener implements MessageProcessorNotificationListener<MessageProcessorNotification> {
        private MuleContext context;

        private MuleMessageDebuggerListener(MuleContext context) {
            this.context = context;
        }

        public void onNotification(MessageProcessorNotification notification) {
            if (notification.getAction() == MessageProcessorNotification.MESSAGE_PROCESSOR_PRE_INVOKE) {
                MuleMessage message = notification.getSource().getMessage();
                if (getHandler().isClientConnected() && getHandler().isDebuggingThisMessage(message)) {

                    ExpressionManager expressionManager = context.getExpressionManager();
                    MessageProcessor processor = notification.getProcessor();
                    if (!MESSAGE_PROCESSOR_NAMES_BLACK_LIST.contains(processor.getClass().getName())) {
                        getHandler().messageArrived(new MuleDebuggingContext(message, expressionManager, Thread.currentThread().getContextClassLoader(), processor));
                    }
                }
            }
        }
    }

    private class MuleExceptionMessageDebuggerLister implements ExceptionNotificationListener<ExceptionNotification> {

        public void onNotification(ExceptionNotification notification) {
            if (notification.getAction() == ExceptionNotification.EXCEPTION_ACTION) {

                if (getHandler().isClientConnected()) {

                    getHandler().resume();
                }
            }
        }
    }

    public DebuggerHandler getHandler() {
        return handler;
    }


}
