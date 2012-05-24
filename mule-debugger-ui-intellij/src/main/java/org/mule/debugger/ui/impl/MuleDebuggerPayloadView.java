package org.mule.debugger.ui.impl;


import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.util.ui.ColumnInfo;
import org.mule.debugger.ui.view.IDebuggerMessageViewer;


import javax.swing.*;
import java.awt.*;

public class MuleDebuggerPayloadView extends JPanel implements IDebuggerMessageViewer {


    private TreeTable payloadTreeViewer;
    private LinkLabel className;
    private Label uniqueId;
    private LinkLabel transformer;
    private JTextArea stringValue;

    public MuleDebuggerPayloadView() {
        this.createControl();

    }

    protected void createControl() {

        payloadTreeViewer = new TreeTable(new ListTreeTableModel(null,new ColumnInfo[0]));
//        payloadTreeViewer.setBorder(BorderFactory.createTitledBorder("Payload"));

        uniqueId = new Label();
        transformer = new LinkLabel();

        JPanel detailsContainer = new JPanel();
        detailsContainer.setBorder(BorderFactory.createTitledBorder("Details"));
        className = new LinkLabel();

        stringValue = new JTextArea();
        stringValue.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        stringValue.setEditable(false);
        JPanel classNameContainer = new JPanel();
         classNameContainer.setLayout(new FlowLayout(FlowLayout.LEFT));


        classNameContainer.add(new JLabel("Class name:"));

        classNameContainer.add(className);


        detailsContainer.setLayout(new BorderLayout());
        detailsContainer.add(classNameContainer, BorderLayout.NORTH);
        detailsContainer.add(stringValue, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        center.add(payloadTreeViewer);
        center.add(detailsContainer);

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Transformer class"));
        bottom.add(transformer);
        bottom.add(new JLabel("Id"));
        bottom.add(uniqueId);
        bottom.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setLayout(new BorderLayout());
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    }


    @Override
    public void setCurrentProcessor(String processorName) {
        transformer.setText(processorName);
    }

    @Override
    public void setSelectionTextValue(String paylodOutput) {
        stringValue.setText(paylodOutput);
    }

    @Override
    public void setSelectionClassName(String className) {
        this.className.setText(className);
    }

    @Override
    public void setEncoding(String encoding) {

    }

    @Override
    public void setUniqueId(String uniqueId) {
        this.uniqueId.setText(uniqueId);
    }

    @Override
    public TreeTable getPayloadTreeViewer() {
        return payloadTreeViewer;
    }

}
