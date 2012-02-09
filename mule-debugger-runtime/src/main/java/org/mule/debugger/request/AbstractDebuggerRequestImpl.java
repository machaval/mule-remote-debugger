/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.request;

public abstract class AbstractDebuggerRequestImpl implements IDebuggerRequest {

    protected static long requestCounter = 0L;

    public long id;

    protected AbstractDebuggerRequestImpl() {

        id = requestCounter++;
    }

    public long getRequestId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDebuggerRequestImpl)) return false;

        AbstractDebuggerRequestImpl that = (AbstractDebuggerRequestImpl) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
