package org.mule.debugger.transport;

import org.mule.debugger.request.IDebuggerRequest;
import org.mule.debugger.response.IDebuggerResponse;

/**
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
public interface IServerDebuggerProtocol {
    IDebuggerRequest getRequest();

    void sendResponse(IDebuggerResponse response);
}
