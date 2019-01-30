package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

class MyPanel extends JPanel {
    MyPanel() {
        setLayout(new GridLayout(0,1));

        //Creating Jlist
        DefaultListModel<String> listModel1 = new DefaultListModel<>();

        JList<String> list1 = new JList<>(listModel1);
        JScrollPane scroll = new JScrollPane(list1);

        File[] files = File.listRoots();
        for (File file : files) {
            listModel1.addElement(file.toString());
        }

        list1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {

                    // Double-click detected

                    String selected = list1.getSelectedValue();
                    String back = "...";


                    if (selected.equals(back)){

                        System.out.println("back");
                        list1.setSelectedIndex(2);
                        String selection2 = list1.getSelectedValue();
                        File father = new File(new File(selection2).getParent());
                        System.out.println(father.toString());
                        if((father.toString()).equals("C:" + File.separator)){listModel1.removeAllElements();
                            for (File file : files) {
                                listModel1.addElement(file.toString());
                            }
                        }else{
                            File parent = father.getParentFile();



                            System.out.println(parent.toString());
                            File[] parents = parent.listFiles();
                            listModel1.removeAllElements();
                            listModel1.addElement(back);
                            if (parents != null) {
                                for (File parent1 : parents ){
                                    listModel1.addElement(parent1.toString());
                                }
                            }

                        }}
                    else{
                        File folder = new File(selected);

                        File[] files1 = folder.listFiles();
                        listModel1.removeAllElements();
                        if (files1 != null) {
                            listModel1.addElement("...");
                            for (File file : files1) {
                                listModel1.addElement(file.toString());
                            }
                        }}

                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                }
            }
        });



        add(scroll);

    }
}
