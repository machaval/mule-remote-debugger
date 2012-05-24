package org.mule.debugger.ui.impl;

import org.mule.debugger.ui.view.IConnectionPropertiesEditor;

import javax.swing.*;
import java.awt.*;

public class ConnectionPropertiesView extends JPanel implements IConnectionPropertiesEditor {

    private JTextField url;
    private JSpinner port;

    public ConnectionPropertiesView() {

        url = new JTextField();
        port = new JSpinner();

        final JPanel hostPanel = new JPanel();
        hostPanel.add(new Label("Host"));
        hostPanel.add(url);

        final JPanel portPanel = new JPanel();
        portPanel.add(new Label("Port"));
        portPanel.add(port);
        this.setLayout(new GridLayout(1,2));
        this.add(hostPanel);
        this.add(portPanel);
    }


    @Override
    public String getURL() {
        return this.url.getText();
    }

    @Override
    public String getPort() {
        return String.valueOf(port.getValue());
    }

    @Override
    public void setURL(String url) {
        this.url.setText(url);
    }

    @Override
    public void setPort(int port) {
        this.port.setValue(port);
    }
}
