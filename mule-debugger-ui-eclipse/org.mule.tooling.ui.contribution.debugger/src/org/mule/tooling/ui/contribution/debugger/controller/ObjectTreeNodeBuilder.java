
package org.mule.tooling.ui.contribution.debugger.controller;

import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.mule.debugger.response.ObjectFieldDefinition;

public class ObjectTreeNodeBuilder
{
    public static TreeNode createTreeNode(ObjectFieldDefinition object)
    {
        TreeNode result = new TreeNode(object);
        List<ObjectFieldDefinition> innerElements = object.getInnerElements();
        TreeNode[] children = new TreeNode[innerElements.size()];
        int i = 0;
        for (ObjectFieldDefinition element : innerElements)
        {
            TreeNode createTreeNode = createTreeNode(element);
            createTreeNode.setParent(result);
            children[i] = createTreeNode;
            i++;
        }
        result.setChildren(children);
        return result;

    }
}
