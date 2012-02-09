/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.client;

import org.mule.debugger.exception.RemoteDebugException;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.debugger.response.ScriptResultInfo;

public class DefaultDebuggerResponseCallback implements IDebuggerResponseCallback {
    public void onMuleMessageArrived(MuleMessageInfo muleMessageInfo) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onScriptEvaluation(ScriptResultInfo info) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onConnected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onExit() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onError(String error) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onException(RemoteDebugException exception) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onWaitingForNextMessage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
