/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org.mule.debugger.ui.impl;

import com.intellij.openapi.ui.DialogWrapper;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;

public class NewPropertyDialog extends DialogWrapper {


    private JTextField nameField;
    private JTextField valueField;


    public NewPropertyDialog() {
        super(true);
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        JComponent component = new JPanel();
        component.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel name = new JLabel("Name");
        nameField = new JTextField();
        JLabel value = new JLabel("Value");
        valueField = new JTextField();
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;

        component.add(name,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.weightx = 0.8;
        c.gridy = 0;
        nameField.setColumns(25);
        component.add(nameField,c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        component.add(value,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.weightx = 0.8;
        c.gridy = 1;
        valueField.setColumns(25);
        component.add(valueField,c);
        return component;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getValueField() {
        return valueField;
    }
}
