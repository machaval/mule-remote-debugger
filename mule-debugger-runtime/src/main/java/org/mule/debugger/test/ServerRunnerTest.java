/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.test;

import org.mule.debugger.MuleDebuggerConnector;

import java.util.HashMap;
import java.util.Map;

public class ServerRunnerTest {
    public static void main(String[] args) {

        MuleDebuggerConnector muleDebuggerConnector = new MuleDebuggerConnector();
        muleDebuggerConnector.setPortNumber(1234);
        muleDebuggerConnector.initialize();

        long i = 0;
        while (i < 100000L) {
            try {
                System.out.println("Debuging message I :" + i);
                Map<String, String> test = new HashMap<String, String>();
                test.put("hola", "mundo" + i);
                muleDebuggerConnector.debug(test);
                Thread.sleep(20000L);
                i++;
            } catch (InterruptedException e) {

            }
        }
    }
}
