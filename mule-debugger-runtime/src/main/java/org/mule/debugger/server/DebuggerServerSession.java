package org.mule.debugger.server;

import com.google.gson.Gson;
import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.transport.IServerDebuggerProtocol;
import org.mule.debugger.MuleDebuggingMessage;
import org.mule.debugger.commands.ICommand;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.response.MuleMessageInfo;

import java.util.logging.Logger;

public class DebuggerServerSession {
    private IServerDebuggerProtocol protocolServer;

    private MuleDebuggingMessage payload;
    private volatile boolean keepOnDebugging = true;


    private static Logger log = Logger.getLogger(DebuggerServerSession.class.getName());

    public DebuggerServerSession(IServerDebuggerProtocol protocolServer, MuleDebuggingMessage payload) {
        this.protocolServer = protocolServer;
        this.payload = payload;

    }

    public void debugMessage(DebuggerHandler debuggerHandler) {

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


    public MuleDebuggingMessage getPayload() {
        return payload;
    }

    public void setPayload(MuleDebuggingMessage payload) {
        this.payload = payload;
    }
}
