/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.test;

import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DebuggerConnection;
import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ScriptResultInfo;

import java.io.IOException;

public class MuleDebuggerClientTest {
    public static void main(String[] args) throws IOException {
        DebuggerConnection localhost = new DebuggerConnection("localhost", 6666);
        final DebuggerClient debuggerClient = new DebuggerClient(localhost);
        debuggerClient.start(new IDebuggerResponseCallback() {
            public void onMuleMessageArrived(MuleMessageInfo muleMessageInfo) {
                System.out.println("MuleDebuggerClientTest.onMuleMessageArrived (muleMessage: " + muleMessageInfo + ")");
            }

            public void onScriptEvaluation(ScriptResultInfo info) {
                System.out.println("MuleDebuggerClientTest.onScriptEvaluation( info : " + info + ")");
            }

            public void onConnected() {
                System.out.println("MuleDebuggerClientTest.onConnected");
            }

            public void onExit() {
                System.out.println("MuleDebuggerClientTest.onExit");
                debuggerClient.disconnect();
            }

            public void onError(String error) {
                System.out.println("MuleDebuggerClientTest.onError(error : " + error + ")");
            }

            public void onException(RemoteDebugException exception) {
                System.out.println("MuleDebuggerClientTest.onException");
            }

            public void onResume() {
                System.out.println("MuleDebuggerClientTest.onWaitingForNextMessage");
            }
        });
    }
}
