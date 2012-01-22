/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import java.io.Serializable;

public class ScriptResultInfo implements Serializable{

    private String jsonResult;
    private String className;
    private String toStringResult;

    public ScriptResultInfo(String jsonResult, String className, String toStringResult) {
        this.jsonResult = jsonResult;
        this.className = className;
        this.toStringResult = toStringResult;
    }

    public String getJsonResult() {
        return jsonResult;
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
                "jsonResult='" + jsonResult + '\'' +
                ", className='" + className + '\'' +
                ", toStringResult='" + toStringResult + '\'' +
                '}';
    }
}
