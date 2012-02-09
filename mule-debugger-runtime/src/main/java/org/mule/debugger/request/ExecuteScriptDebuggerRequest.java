/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.request;

import org.mule.debugger.commands.ExecuteScriptCommandImpl;
import org.mule.debugger.commands.ICommand;

public class ExecuteScriptDebuggerRequest extends AbstractDebuggerRequestImpl {

    private String script;

    public ExecuteScriptDebuggerRequest(String script) {
        this.script = script;
    }

    public ICommand createCommand() {
        return new ExecuteScriptCommandImpl(script);
    }
}
