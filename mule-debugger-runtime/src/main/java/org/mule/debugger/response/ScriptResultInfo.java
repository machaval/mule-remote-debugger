/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import java.io.Serializable;

public class ScriptResultInfo implements Serializable {

    private MuleMessageInfo message;
    private ObjectFieldDefinition result;
    private String className;
    private String toStringResult;

    public ScriptResultInfo(MuleMessageInfo message, ObjectFieldDefinition result, String className, String toStringResult) {
        this.message = message;
        this.result = result;
        this.className = className;
        this.toStringResult = toStringResult;
    }

    public ObjectFieldDefinition getResult() {
        return result;
    }

    public String getClassName() {
        return className;
    }

    public String getToStringResult() {
        return toStringResult;
    }

    @Override
    public String toString() {
        return "ScriptResultInfo{" +
                "result=" + result +
                ", className='" + className + '\'' +
                ", toStringResult='" + toStringResult + '\'' +
                '}';
    }

    public MuleMessageInfo getMessage() {
        return message;
    }
}
