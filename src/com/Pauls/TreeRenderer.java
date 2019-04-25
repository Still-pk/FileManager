package com.Pauls;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;

public class TreeRenderer extends DefaultTreeCellRenderer {
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode ) {
            value = ((DefaultMutableTreeNode) value).getUserObject();
            if (value instanceof File) {
                File file = (File) value;
                if (file.getParentFile() != null){
                    if (file.isFile()) {
                        setIcon(fileSystemView.getSystemIcon(file));
                        setText(file.getName());
                    } else {
                        setIcon(fileSystemView.getSystemIcon(file));
                        setText(file.getName());
                    }
            }
                setIcon(fileSystemView.getSystemIcon(file));
            }
        }

        return this;
    }
}
