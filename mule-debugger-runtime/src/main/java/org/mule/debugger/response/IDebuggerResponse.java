package org.mule.debugger.response;

import org.mule.debugger.client.IDebuggerResponseCallback;

import java.io.Serializable;

/**
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
public interface IDebuggerResponse extends Serializable {
    void callCallback(IDebuggerResponseCallback callback);
}
