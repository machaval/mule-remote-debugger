/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

public class SimpleObject {
    private String name;
    private String lastName;
    private int size;
    private Integer other;
    private Long pepe;

    public SimpleObject(String name, String lastName, int size, Integer other, Long pepe) {
        this.name = name;
        this.lastName = lastName;
        this.size = size;
        this.other = other;
        this.pepe = pepe;
    }
}
