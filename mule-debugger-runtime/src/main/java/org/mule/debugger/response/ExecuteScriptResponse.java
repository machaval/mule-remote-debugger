/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import org.mule.debugger.client.IDebuggerResponseCallback;
import org.mule.debugger.commands.ExecuteScriptCommandImpl;

public class ExecuteScriptResponse extends DebuggerResponse {

    private ScriptResultInfo scriptEvaluationInfo;

    public ExecuteScriptResponse(ScriptResultInfo scriptEvaluationInfo) {
        super(ExecuteScriptCommandImpl.SCRIPT);
        this.scriptEvaluationInfo = scriptEvaluationInfo;
    }

    public ScriptResultInfo getScriptEvaluationInfo() {
        return scriptEvaluationInfo;
    }

    public void callCallback(IDebuggerResponseCallback callback) {
        callback.onScriptEvaluation(scriptEvaluationInfo);
    }
}
