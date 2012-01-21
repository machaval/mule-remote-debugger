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
import org.mule.debugger.response.ScriptEvaluationInfo;

public interface IDebuggerResponseCallback {

    void onMuleMessageArrived(MuleMessageInfo muleMessageInfo);

    void onScriptEvaluation(ScriptEvaluationInfo info);

    void onConnected();

    void onExit();

    void onError(String error);

    void onException(RemoteDebugException exception);

    void onWaitingForNextMessage();
}