/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;


import junit.framework.TestCase;
import org.junit.Test;


public class ObjectFieldDefinitionTest {
    @Test
    public void testCreateFromObjectWithString() throws Exception {
        ObjectFieldDefinition fromObject = ObjectFieldDefinition.createFromObject("hola", "payload");
        System.out.println("fromObject = " + fromObject);
        TestCase.assertNotNull(fromObject);
    }

    @Test
    public void testCreateFromObjectWithDummyObject() throws Exception {

        ObjectFieldDefinition fromObject = ObjectFieldDefinition.createFromObject(new SimpleObject("Mariano", "Achaval", 10, new Integer(10), new Long(3L)), "payload");
        System.out.println("fromObject = " + fromObject);
        TestCase.assertNotNull(fromObject);
    }

     @Test
    public void testCreateFromObjectWithNull() throws Exception {

        ObjectFieldDefinition fromObject = ObjectFieldDefinition.createFromObject(null, "payload");
        System.out.println("fromObject = " + fromObject);
        TestCase.assertNotNull(fromObject);
    }
}
