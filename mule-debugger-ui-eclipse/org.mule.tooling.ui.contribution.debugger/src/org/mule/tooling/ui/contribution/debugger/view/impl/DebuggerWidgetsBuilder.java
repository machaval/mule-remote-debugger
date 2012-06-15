
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class DebuggerWidgetsBuilder
{
    /**
     * Create the Table
     */
    public static TreeViewer createObjectDefinitionTreeTable(final Composite parent)
    {
        TreeViewer treeViewer = new TreeViewer(parent, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        final Tree tree = treeViewer.getTree();
        tree.setHeaderVisible(true);
        final TreeColumn nameCol = new TreeColumn(treeViewer.getTree(), SWT.NONE);
        nameCol.setText("Name");
        nameCol.setResizable(true);
        nameCol.setWidth(120);
        final TreeColumn classCol = new TreeColumn(treeViewer.getTree(), SWT.NONE);
        classCol.setText("Class Name");
        classCol.setWidth(120);
        classCol.setResizable(true);
        final TreeColumn valueCol = new TreeColumn(treeViewer.getTree(), SWT.NONE);
        valueCol.setWidth(120);
        valueCol.setText("Value");
        valueCol.setResizable(true);

        parent.addControlListener(new ControlAdapter()
        {
            public void controlResized(ControlEvent e)
            {
                Rectangle area = parent.getClientArea();
                Point preferredSize = tree.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                int scrollBarWidth = 13;
                int width = area.width - 2 * tree.getBorderWidth() - scrollBarWidth;
                if (preferredSize.y > area.height + tree.getHeaderHeight())
                {
                    // Subtract the scrollbar width from the total column width
                    // if a vertical scrollbar will be required
                    Point vBarSize = tree.getVerticalBar().getSize();
                    width -= vBarSize.x;
                }
                Point oldSize = tree.getSize();
                if (oldSize.x > area.width)
                {
                    // table is getting smaller so make the columns
                    // smaller first and then resize the table to
                    // match the client area width
                    nameCol.setWidth(width / 5);
                    classCol.setWidth(width / 3);
                    valueCol.setWidth(width - nameCol.getWidth() - classCol.getWidth());
                    tree.setSize(area.width, area.height);
                }
                else
                {
                    // table is getting bigger so make the table
                    // bigger first and then make the columns wider
                    // to match the client area width
                    tree.setSize(area.width, area.height);
                    nameCol.setWidth(width / 5);
                    classCol.setWidth(width / 3);
                    valueCol.setWidth(width - nameCol.getWidth() - classCol.getWidth());
                    
                }
            }
        });

        return treeViewer;

    }
}
