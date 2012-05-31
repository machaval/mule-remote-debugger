/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.impl;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClassLinkTableCellRenderer extends DefaultTableCellRenderer  {


    private String className;

    public ClassLinkTableCellRenderer() {


    }



    @Override
    public void setText(String text) {
        className = text;

        super.setText("<html><u><bold>" + text + "</u></bold></html>");
    }
}
