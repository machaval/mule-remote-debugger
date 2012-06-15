/**
 *
 * (c) 2011 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.commands;

import org.mule.debugger.response.IDebuggerResponse;
import org.mule.debugger.response.ResumeResponse;

public class ResumeCommandImpl extends AbstractCommand {

    public static final String RESUME = "resume";

    public IDebuggerResponse execute() {
        getHandler().resume();
        return new ResumeResponse();
    }

    public String getId() {
        return RESUME;
    }
}
