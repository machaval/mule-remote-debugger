
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.mule.tooling.ui.contribution.debugger.Activator;

public class DebuggerImages
{
    public static final String JAVA_ATTR = "Java_Attr";

    public static final String JAVA_ELEMENT = "Java_Element";

    private static final String PLUGIN_ID = Activator.PLUGIN_ID;

    public static ImageDescriptor IMG_CONNECT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/connect.png");

    public static ImageDescriptor IMG_DISCONNECT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/disconnect.png");

    public static ImageDescriptor IMG_RESUME = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/resume.png");
    
    public static ImageDescriptor IMG_NEXT_STEP = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/small/step_over.png");

    public static ImageDescriptor IMG_ATTR = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/attribute.gif");

    public static ImageDescriptor IMG_ELEMENT = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
        "icons/small/element.gif");
    
    public static ImageDescriptor MARIANO = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/mariano.png");
    
    public static ImageDescriptor JORGE = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/jorge.png");
    
    public static ImageDescriptor ALBERTO = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/alberto.png");
    
    public static ImageDescriptor SANTIAGO = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/vacas.png");
    
    public static ImageDescriptor JULIEN = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/julien.png");
    
    public static ImageDescriptor TANO = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
    "icons/authors/tano.png");

    private static DebuggerImages instance = null;

    public static DebuggerImages getDebuggerImages()
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
        if (JFaceResources.getImageRegistry().get(JAVA_ELEMENT) == null)
        {
            JFaceResources.getImageRegistry().put(JAVA_ELEMENT, IMG_ELEMENT.createImage());
        }
        if (JFaceResources.getImageRegistry().get(JAVA_ATTR) == null)
        {
            JFaceResources.getImageRegistry().put(JAVA_ATTR, IMG_ATTR.createImage());
            JFaceResources.getImageRegistry().put("mariano", MARIANO.createImage());
            JFaceResources.getImageRegistry().put("jorge", JORGE.createImage());
            JFaceResources.getImageRegistry().put("alberto", ALBERTO.createImage());
            JFaceResources.getImageRegistry().put("julien", JULIEN.createImage());
            JFaceResources.getImageRegistry().put("santiago", SANTIAGO.createImage());
            JFaceResources.getImageRegistry().put("tano", TANO.createImage());
        }
        
        
        
    }

    public Image getImage(String key)
    {
        return JFaceResources.getImageRegistry().get(key);

    }
}
