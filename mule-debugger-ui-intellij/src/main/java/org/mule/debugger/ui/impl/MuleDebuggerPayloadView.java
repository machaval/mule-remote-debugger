package org.mule.debugger.ui.impl;


import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableCellRenderer;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.UIUtil;
import org.mule.debugger.ui.view.IDebuggerMessageViewer;


import javax.swing.*;
import java.awt.*;

public class MuleDebuggerPayloadView extends JSplitPane implements IDebuggerMessageViewer {


    private TreeTable payloadTreeViewer;

    private JTextArea stringValue;

    public MuleDebuggerPayloadView() {
        super(JSplitPane.VERTICAL_SPLIT);
        this.createControl();

    }

    protected void createControl() {

        payloadTreeViewer = new TreeTable(new ListTreeTableModel(null,new ColumnInfo[0])){
            @Override
            public TreeTableCellRenderer createTableRenderer(TreeTableModel treeTableModel) {
                TreeTableCellRenderer tableRenderer = super.createTableRenderer(treeTableModel);
                UIUtil.setLineStyleAngled(tableRenderer);
                tableRenderer.setRootVisible(false);
                tableRenderer.setShowsRootHandles(true);

                return tableRenderer;
            }
        };

        payloadTreeViewer.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        stringValue = new JTextArea();
        stringValue.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        stringValue.setEditable(false);


        this.setDividerLocation(0.7);
        this.add(new JScrollPane(payloadTreeViewer));
        this.add(stringValue);

    }



    @Override
    public void setSelectionTextValue(String paylodOutput) {
        stringValue.setText(paylodOutput);
    }



    @Override
    public TreeTable getPayloadTreeViewer() {
        return payloadTreeViewer;
    }

}
