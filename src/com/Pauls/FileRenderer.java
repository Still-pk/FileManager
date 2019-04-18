package com.Pauls;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list,
                value, index, isSelected, cellHasFocus);
        JLabel label = (JLabel) component;
        String s = value.toString();
        File file = new File(s);
        if (file.isDirectory()) {
        Icon icon = UIManager.getIcon("FileView.directoryIcon");
        label.setIcon(icon);
            return label;
        }
        else {
            Icon icon = UIManager.getIcon("FileView.fileIcon");
            label.setIcon(icon);
            return label;
        }


    }
}
