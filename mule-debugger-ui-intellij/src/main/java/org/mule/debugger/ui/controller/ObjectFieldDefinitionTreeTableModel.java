package org.mule.debugger.ui.controller;

import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import org.mule.debugger.response.ObjectFieldDefinition;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
        return String.class;
    }

    @Override
    public Object getValueAt(Object node, int column) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) node;
        switch (column) {
            case 0:
                return objectDef.getName();
            case 1:
                return objectDef.getClassName();
            default:
                return objectDef.getValue();
        }

    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {

    }

    @Override
    public void setTree(JTree tree) {

    }

    @Override
    public Object getChild(Object parent, int index) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) parent;
        List<ObjectFieldDefinition> innerElements = objectDef.getInnerElements();

        return innerElements.get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        ObjectFieldDefinition objectDef = (ObjectFieldDefinition) parent;
        List<ObjectFieldDefinition> innerElements = objectDef.getInnerElements();

        return innerElements == null ? 0 : innerElements.size();
    }
}
