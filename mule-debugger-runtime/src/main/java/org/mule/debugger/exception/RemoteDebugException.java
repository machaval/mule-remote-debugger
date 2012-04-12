/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.exception;

import org.mule.debugger.response.ObjectFieldDefinition;

public class RemoteDebugException extends Exception {

    private ObjectFieldDefinition exception;

    public RemoteDebugException(String message, Exception e) {
        super(message);
        exception = ObjectFieldDefinition.createFromObject(e, "exception");
    }

    public RemoteDebugException() {
    }

    public ObjectFieldDefinition getException() {
        return exception;
    }

    public void setException(ObjectFieldDefinition exception) {
        this.exception = exception;
    }
}
