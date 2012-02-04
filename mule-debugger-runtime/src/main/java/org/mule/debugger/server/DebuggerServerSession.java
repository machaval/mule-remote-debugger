package org.mule.debugger.server;

import com.google.gson.Gson;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.MuleDebuggingContext;
import org.mule.debugger.commands.ICommand;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.response.MuleMessageInfo;

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

        Object payload = muleDebuggingMessage.getPayload();
        IDebuggerResponse messageToSend = new MuleMessageArrivedResponse(new MuleMessageInfo(objectToString(payload), String.valueOf(payload.getClass()), String.valueOf(payload)));
        while (keepOnDebugging) {
            protocolServer.sendResponse(messageToSend);
            IDebuggerRequest message = protocolServer.getRequest();
            messageToSend = execute(message, debuggerHandler);
        }
    }

    public String objectToString(Object element) {
        Gson gson = new Gson();
        return gson.toJson(element);
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
}
