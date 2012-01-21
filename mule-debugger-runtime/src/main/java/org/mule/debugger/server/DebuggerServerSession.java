package org.mule.debugger.server;

import com.google.gson.Gson;
import org.mule.debugger.IDebuggerProtocol;
import org.mule.debugger.commands.ICommand;
import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.DebuggerResponse;
import org.mule.debugger.MuleDebuggingMessage;
import org.mule.debugger.commands.CommandsRegistry;
import org.mule.debugger.response.MuleMessageArrivedResponse;
import org.mule.debugger.response.MuleMessageInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DebuggerServerSession {
    private IDebuggerProtocol protocol;

    private MuleDebuggingMessage payload;
    private boolean keepOnDebugging = true;


    private static Logger log = Logger.getLogger(DebuggerServerSession.class.getName());

    public DebuggerServerSession(IDebuggerProtocol protocol, MuleDebuggingMessage payload) {
        this.protocol = protocol;
        this.payload = payload;

    }

    public void start(DebuggerHandler debuggerHandler) {

        DebuggerResponse messageToSend = new MuleMessageArrivedResponse(new MuleMessageInfo(objectToString(payload), String.valueOf(payload.getClass()), String.valueOf(payload)));
        while (keepOnDebugging) {
            protocol.sendResponse(messageToSend);
            IDebuggerRequest message = protocol.getRequest();
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

    private DebuggerResponse execute(IDebuggerRequest message, DebuggerHandler debuggerHandler) {
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
