
package org.mule.debugger.ui.view;

import com.intellij.ui.treeStructure.treetable.TreeTable;

public interface IDebuggerMessageViewer
{

    
    void setSelectionTextValue(String paylodOutput);



    TreeTable getPayloadTreeViewer();

}
