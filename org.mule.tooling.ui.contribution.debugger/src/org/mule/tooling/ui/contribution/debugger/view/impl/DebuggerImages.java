
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.mule.tooling.ui.contribution.debugger.Activator;

public class DebuggerImages
{
    private static final String PLUGIN_ID = Activator.PLUGIN_ID;

    public static ImageDescriptor IMG_CONNECT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/connect.png");

    public static ImageDescriptor IMG_DISCONNECT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/disconnect.png");

    public static ImageDescriptor IMG_NEXT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/next.png");

    private static DebuggerImages instance = null;

    public static DebuggerImages getDataMapperImages()
    {
        if (instance == null)
        {
            instance = new DebuggerImages();
        }
        return instance;
    }

    private DebuggerImages()
    {
        init();
    }

    public void init()
    {

    }

    public Image getImage(String key)
    {
        return JFaceResources.getImageRegistry().get(key);

    }
}
