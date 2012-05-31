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
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClassLinkTableCellEditor extends AbstractCellEditor implements TableCellEditor {


    private Label text;
    private String className;


    public ClassLinkTableCellEditor() {

        text = new Label();

        text.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        text.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), className, "Error", JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), className, "Error", JOptionPane.ERROR_MESSAGE);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), className, "Error", JOptionPane.ERROR_MESSAGE);

            }

            @Override
            public void mouseEntered(MouseEvent e) {


            }

            @Override
            public void mouseExited(MouseEvent e) {


            }
        });
        text.setFocusable(true);
    }

    @Override
    public Object getCellEditorValue() {
        return className;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        className = String.valueOf(value);
        text.setText(className);
        return text;
    }
}
