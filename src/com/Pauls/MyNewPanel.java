package com.Pauls;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class MyNewPanel extends JPanel {
    MyNewPanel() {
        setLayout(new GridLayout(0,1));

        //Creating Jlist
        DefaultListModel<String> listModel1 = new DefaultListModel<>();

        JList<String> list1 = new JList<>(listModel1);
        JScrollPane scroll = new JScrollPane(list1);
        File[] files = File.listRoots();
                for (File file : files ) {
            listModel1.addElement(file.toString());
        }
        list1.addMouseListener(new ListMouseAdapter(list1, listModel1));
        add(scroll);
        /*File[] root = File.listRoots();
        for (File file : root ) {
            listModel1.addElement(file.toString());
        }
        */
    }
}
