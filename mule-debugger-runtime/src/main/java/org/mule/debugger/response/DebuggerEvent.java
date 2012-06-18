/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import org.mule.debugger.request.IDebuggerRequest;

public abstract class DebuggerEvent implements IDebuggerServerEvent {

    private IDebuggerRequest request;
    public DebuggerEvent() {
    }

    public void setRequest(IDebuggerRequest request) {
        this.request = request;
    }

    public IDebuggerRequest getRequest() {
        return request;
    }
}
