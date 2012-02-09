/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import org.mule.debugger.client.IDebuggerResponseCallback;

public class ExitDebuggerResponse extends DebuggerResponse {
    public ExitDebuggerResponse() {
        super();
    }


    public void callCallback(IDebuggerResponseCallback callback) {
        callback.onExit();
    }
}
