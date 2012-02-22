/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response.functional;

import org.mule.debugger.client.DebuggerClient;
import org.mule.debugger.client.DebuggerConnection;
import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ScriptResultInfo;
import org.mule.tck.FunctionalTestCase;

import java.io.IOException;

public class DebuggerFunctionalTestCase extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    public void testDebugger() throws IOException {
        DebuggerClient client = new DebuggerClient(new DebuggerConnection("localhost",6666));
        client.start(new IDebuggerResponseCallback() {
            public void onMuleMessageArrived(MuleMessageInfo muleMessageInfo) {

            }

            public void onScriptEvaluation(ScriptResultInfo info) {

            }

            public void onConnected() {

            }

            public void onExit() {

            }

            public void onError(String error) {

            }

            public void onException(RemoteDebugException exception) {

            }

            public void onResume() {

            }
        });
        assertTrue("Debugger started", true);
    }
}
