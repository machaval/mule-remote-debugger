/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.test;

import org.mule.debugger.client.DebuggerConnection;
import org.mule.debugger.response.DebuggerResponse;

public class MuleDebuggerClientTest {
    public static void main(String[] args) {
        DebuggerConnection localhost = new DebuggerConnection("localhost", 1234);
        DebuggerResponse debuggerResponse = localhost.connect();
        System.out.println("debuggerResponse = " + debuggerResponse);
    }
}
