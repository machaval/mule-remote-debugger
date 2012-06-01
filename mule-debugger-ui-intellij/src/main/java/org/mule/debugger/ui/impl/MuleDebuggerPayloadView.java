package org.mule.debugger.ui.impl;


import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBScrollPane;
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

public class MuleDebuggerPayloadView extends JPanel implements IDebuggerMessageViewer {


    private TreeTable payloadTreeViewer;

    private JTextArea stringValue;

    public MuleDebuggerPayloadView() {
        super();
        this.createControl();

    }

    protected void createControl() {
        this.setLayout(new BorderLayout(1,3));
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
        stringValue.setEditable(false);
        stringValue.setRows(3);

        this.add(new JBScrollPane(payloadTreeViewer),BorderLayout.CENTER);
        this.add(new JBScrollPane(stringValue),BorderLayout.SOUTH);


        //this.setBorder(IdeBorderFactory.createTitledBorder("Message"));

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
