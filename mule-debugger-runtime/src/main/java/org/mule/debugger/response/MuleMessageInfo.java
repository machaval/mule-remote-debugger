/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

public class MuleMessageInfo {
    private String jsonPaylod;
    private String payloadClassName;
    private String toStringPayload;

    public MuleMessageInfo(String jsonPaylod, String payloadClassName, String toStringPayload) {
        this.jsonPaylod = jsonPaylod;
        this.payloadClassName = payloadClassName;
        this.toStringPayload = toStringPayload;

    }

    public String getJsonPaylod() {
        return jsonPaylod;
    }

    public String getPayloadClassName() {
        return payloadClassName;
    }

    public String getToStringPayload() {
        return toStringPayload;
    }
}
