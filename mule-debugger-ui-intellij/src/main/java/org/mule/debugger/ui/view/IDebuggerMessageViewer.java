
package org.mule.debugger.ui.view;

import com.intellij.ui.treeStructure.treetable.TreeTable;

public interface IDebuggerMessageViewer
{
    void setCurrentProcessor(String processorName);
    
    void setSelectionTextValue(String paylodOutput);

    void setSelectionClassName(String className);

    void setEncoding(String encoding);

    void setUniqueId(String uniqueId);

    TreeTable getPayloadTreeViewer();

}
