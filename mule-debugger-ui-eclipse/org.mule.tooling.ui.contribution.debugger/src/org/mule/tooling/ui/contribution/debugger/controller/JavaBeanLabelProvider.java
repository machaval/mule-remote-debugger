/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tooling.ui.contribution.debugger.controller;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;
import org.mule.debugger.response.ObjectFieldDefinition;
import org.mule.tooling.ui.contribution.debugger.view.impl.DebuggerImages;

public class JavaBeanLabelProvider extends LabelProvider implements ITableLabelProvider
{

    @Override
    public Image getColumnImage(Object element, int columnIndex)
    {

        if (columnIndex == 0)
        {
            TreeNode node = (TreeNode) element;
            ObjectFieldDefinition value = (ObjectFieldDefinition) node.getValue();
            DebuggerImages debuggerImages = DebuggerImages.getDebuggerImages();
            return value.getInnerElements().isEmpty()
                                                     ? debuggerImages.getImage(DebuggerImages.JAVA_ATTR)
                                                     : debuggerImages.getImage(DebuggerImages.JAVA_ELEMENT);
        }
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex)
    {
        TreeNode node = (TreeNode) element;
        ObjectFieldDefinition value = (ObjectFieldDefinition) node.getValue();
        switch (columnIndex)
        {
            case 0 :
                return value.getName();

            case 1 :
                return value.getClassName();

            case 2 :
                return value.getValue();

        }
        return "";
    }

}
