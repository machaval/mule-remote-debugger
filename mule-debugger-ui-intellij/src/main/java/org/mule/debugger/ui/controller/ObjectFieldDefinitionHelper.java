/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.controller;

import org.mule.debugger.response.ObjectFieldDefinition;

import java.util.ArrayList;
import java.util.Collection;

public class ObjectFieldDefinitionHelper {
    public static ObjectFieldDefinition createRootNode(Collection<ObjectFieldDefinition> values) {
        return new ObjectFieldDefinition("Root","","",new ArrayList<ObjectFieldDefinition>(values));
    }

    public static ObjectFieldDefinition createEmptyNode(){
        return new ObjectFieldDefinition("Root", "", "", new ArrayList<ObjectFieldDefinition>());
    }
}
