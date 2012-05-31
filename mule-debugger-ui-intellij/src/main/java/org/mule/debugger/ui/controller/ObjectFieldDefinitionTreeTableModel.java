package org.mule.debugger.ui.controller;

import com.intellij.ui.JBDefaultTreeCellRenderer;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import org.mule.debugger.response.ObjectFieldDefinition;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;

public class ObjectFieldDefinitionTreeTableModel extends DefaultTreeModel
        implements TreeTableModel {

    private static String[] COLUMN_NAMES = new String[]{"Name", "Class", "Value"};

    private ObjectFieldDefinitionTreeTableModel(Object root) {
        super(new DefaultMutableTreeNode(root));
    }

    public static TreeTableModel createTreeNode(ObjectFieldDefinition object) {
        return new ObjectFieldDefinitionTreeTableModel(object);
    }


    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return TreeTableModel.class;
            case 1:
                return Class.class;
            case 2:
                return String.class;
            default:
                return Object.class;

        }
    }

    @Override
    public Object getValueAt(Object node, int column) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) ((DefaultMutableTreeNode) node).getUserObject();

        switch (column) {
            case 0:
                return node;
            case 1:
                return objectDef.getClassName();
            default:
                return objectDef.getValue();
        }

    }

    @Override
    public boolean isLeaf(Object node) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) ((DefaultMutableTreeNode) node).getUserObject();
        return objectDef.getInnerElements().isEmpty();
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return column == 1;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {

    }



    @Override
    public void setTree(JTree tree) {
        tree.setCellRenderer(new JBDefaultTreeCellRenderer(tree){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component treeCellRendererComponent = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if(value instanceof DefaultMutableTreeNode){
                    Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                    if(userObject instanceof ObjectFieldDefinition){
                        setText(((ObjectFieldDefinition)userObject).getName());
                    }
                }
                return treeCellRendererComponent;
            }
        });
    }

    @Override
    public Object getChild(Object parent, int index) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) ((DefaultMutableTreeNode) parent).getUserObject();
        List<ObjectFieldDefinition> innerElements = objectDef.getInnerElements();

        return new DefaultMutableTreeNode(innerElements.get(index));
    }

    @Override
    public int getChildCount(Object parent) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) ((DefaultMutableTreeNode) parent).getUserObject();
        List<ObjectFieldDefinition> innerElements = objectDef.getInnerElements();

        return innerElements == null ? 0 : innerElements.size();
    }
}
