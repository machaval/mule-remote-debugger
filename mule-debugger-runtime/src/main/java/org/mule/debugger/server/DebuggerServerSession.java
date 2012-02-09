package org.mule.debugger.server;

import com.google.gson.Gson;
import org.mule.api.MuleMessage;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.commands.ICommand;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.response.MuleMessageInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class DebuggerServerSession {
    private IServerDebuggerProtocol protocolServer;

    private MuleDebuggingContext muleDebuggingMessage;
    private volatile boolean keepOnDebugging = true;


    private static Logger log = Logger.getLogger(DebuggerServerSession.class.getName());

    public DebuggerServerSession(IServerDebuggerProtocol protocolServer, MuleDebuggingContext muleDebuggingMessage) {
        this.protocolServer = protocolServer;
        this.muleDebuggingMessage = muleDebuggingMessage;

    }

    public void debugMessage(DebuggerHandler debuggerHandler) {


        IDebuggerResponse messageToSend = new MuleMessageArrivedResponse(createFromMuleMessage(muleDebuggingMessage.getMessage()));
        protocolServer.sendResponse(messageToSend);
        while (keepOnDebugging) {
            IDebuggerRequest request = protocolServer.getRequest();
            messageToSend = execute(request, debuggerHandler);
            messageToSend.setRequest(request);
            protocolServer.sendResponse(messageToSend);
        }
    }


    public void stop() {
        keepOnDebugging = false;
    }

    private IDebuggerResponse execute(IDebuggerRequest message, DebuggerHandler debuggerHandler) {
        ICommand command = message.createCommand();
        command.setHandler(debuggerHandler);
        command.setCurrentSession(this);
        return command.execute();
    }


    public MuleDebuggingContext getMuleDebuggingMessage() {
        return muleDebuggingMessage;
    }

    public void setMuleDebuggingMessage(MuleDebuggingContext muleDebuggingMessage) {
        this.muleDebuggingMessage = muleDebuggingMessage;
    }

    public static MuleMessageInfo createFromMuleMessage(MuleMessage message) {
        MuleMessageInfo result = new MuleMessageInfo();

        Object messagePayload = message.getPayload();

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
