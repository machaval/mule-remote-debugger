/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

public class ScriptEvaluationInfo {

    private String jsonResult;
    private String className;
    private String toStringResult;

    public ScriptEvaluationInfo(String jsonResult, String className, String toStringResult) {
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
}
