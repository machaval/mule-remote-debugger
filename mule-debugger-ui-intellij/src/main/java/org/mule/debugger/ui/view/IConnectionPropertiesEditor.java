
package org.mule.debugger.ui.view;

public interface IConnectionPropertiesEditor
{
    String getURL();

    String getPort();

    void setURL(String url);

    void setPort(int port);
}
