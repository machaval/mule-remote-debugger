/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.response;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class ObjectFieldDefinition implements Serializable{

    private String name;
    private String className;
    private String value;
    private List<ObjectFieldDefinition> innerElements;

    public ObjectFieldDefinition(String name, String className, String value, List<ObjectFieldDefinition> innerElements) {
        this.name = name;
        this.className = className;
        this.value = value;
        this.innerElements = innerElements;
    }


    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getValue() {
        return value;
    }

    public List<ObjectFieldDefinition> getInnerElements() {
        return innerElements;
    }

    @Override
    public String toString() {
        return "ObjectFieldDefinition{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", value='" + value + '\'' +
                ", innerElements=" + innerElements +
                '}';
    }

    private static ObjectFieldDefinition createFromObject(Object value, String name, final int depth) {
        int nextDepth = depth + 1;
        Class<? extends Object> valueClazz = value == null? null : value.getClass();
        if (nextDepth >= 5 ||value == null || valueClazz.isPrimitive() || ClassUtils.isWrapperType(valueClazz) || String.class.isAssignableFrom(valueClazz)) {
            return createSimpleField(value, valueClazz,name);
        }
        Field[] fields = valueClazz.getDeclaredFields();
        List<ObjectFieldDefinition> elements = new ArrayList<ObjectFieldDefinition>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(value);
                if (field.getType().isPrimitive() || field.getType().isEnum()|| fieldValue == null) {
                    elements.add(createSimpleField(fieldValue,field.getType() ,field.getName()));
                } else if (field.getType().isArray()) {
                    List<ObjectFieldDefinition> arrayElements = new ArrayList<ObjectFieldDefinition>();
                    int length = Array.getLength(fieldValue);
                    for (int i = 0; i < length; i++) {
                        Object arrayElement = Array.get(fieldValue, i);
                        arrayElements.add(createFromObject(arrayElement, String.valueOf(i), nextDepth));
                    }
                    elements.add(new ObjectFieldDefinition(name, valueClazz.getCanonicalName(), String.valueOf(value), arrayElements));
                } else if (Iterable.class.isAssignableFrom(field.getType())) {
                    List<ObjectFieldDefinition> arrayElements = new ArrayList<ObjectFieldDefinition>();
                    Iterable iterableValue = (Iterable) fieldValue;
                    int i = 0;
                    for (Object iterableElement : iterableValue) {
                        arrayElements.add(createFromObject(iterableElement, String.valueOf(i), nextDepth));
                        i++;
                    }
                    elements.add(new ObjectFieldDefinition(name, valueClazz.getCanonicalName(), String.valueOf(value), arrayElements));
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    Map<?, ?> mapValue = (Map) fieldValue;
                    List<ObjectFieldDefinition> mapElements = new ArrayList<ObjectFieldDefinition>();
                    for (Map.Entry entryElement : mapValue.entrySet()) {
                        mapElements.add(createFromObject(entryElement.getValue(), String.valueOf(entryElement.getKey()), nextDepth));
                    }
                    elements.add(new ObjectFieldDefinition(name, valueClazz.getCanonicalName(), String.valueOf(value), mapElements));
                }  else {
                    elements.add(createFromObject(fieldValue,field.getName(),nextDepth));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new ObjectFieldDefinition(name, valueClazz.getCanonicalName(), String.valueOf(value),elements);
    }



    private static ObjectFieldDefinition createSimpleField(Object value, Class clazz,String name) {
        String canonicalName = clazz != null?clazz.getCanonicalName() : "null";
        return new ObjectFieldDefinition(name, canonicalName, String.valueOf(value), Collections.<ObjectFieldDefinition>emptyList());
    }

    public static ObjectFieldDefinition createFromObject(Object value, String name){
        return createFromObject(value, name, 0);
    }
}
