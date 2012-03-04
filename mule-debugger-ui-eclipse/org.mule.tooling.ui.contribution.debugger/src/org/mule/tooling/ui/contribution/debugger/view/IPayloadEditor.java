
package org.mule.tooling.ui.contribution.debugger.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Link;

public interface IPayloadEditor
{
    void setCurrentProcessor(String processorName);
    
    void setPayloadOutput(String paylodOutput);

    void setPayloadClassName(String className);

    void setEncoding(String encoding);

    void setUniqueId(String uniqueId);

    TreeViewer getPayloadTreeViewer();
    
    Link getTransformer();
    
    Link getPayloadClassName();

}
