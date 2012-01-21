/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.request;

import org.mule.debugger.commands.ExitCommandImpl;
import org.mule.debugger.commands.ICommand;

public class ExitDebuggerRequest implements IDebuggerRequest {
    public ICommand createCommand() {
        return new ExitCommandImpl();
    }
}
