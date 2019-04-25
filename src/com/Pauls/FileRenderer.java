package com.Pauls;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileRenderer extends DefaultListCellRenderer {
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        JLabel label = (JLabel) component;
        String s = value.toString();
        File file = new File(s);
        if (file.getParent() != null) {


        if (file.isDirectory()) {
        //Icon icon = UIManager.getIcon("FileView.directoryIcon");
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(file.getName());
            return label;
        }
        else {
            //Icon icon = UIManager.getIcon("FileView.fileIcon");
            label.setIcon(fileSystemView.getSystemIcon(file));
            label.setText(file.getName());
            return this;
        }
        }
        label.setIcon(fileSystemView.getSystemIcon(file));
        return this;
    }
}
