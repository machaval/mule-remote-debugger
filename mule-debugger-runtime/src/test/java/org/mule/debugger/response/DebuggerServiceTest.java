/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import org.junit.Test;
import org.mockito.Mockito;
import org.mule.api.MuleMessage;
import org.mule.debugger.server.DebuggerHandler;

public class DebuggerServiceTest {
    @Test
    public void debuggerServiceTest() {
        DebuggerHandler service = new DebuggerHandler();

        service.breakPoint(Mockito.mock(MuleMessage.class));
        System.out.println("Break point done");
    }
}
