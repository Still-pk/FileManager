package com.Pauls;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class ListMouseAdapter extends MouseAdapter {
    private JList jlist;
    private DefaultListModel<String> yoursListModel;
    private String fatherOld;

    ListMouseAdapter(JList jlist, DefaultListModel<String> yoursListModel) {
        this.jlist = jlist;
        this.yoursListModel = yoursListModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        //JList list = (JList) e.getSource();
        if (e.getClickCount() == 2) {
            String selected = jlist.getSelectedValue().toString();
            String back = "...";


            if (selected.equals(back)) {
                if (jlist.getLastVisibleIndex() > 0) {
                    jlist.setSelectedIndex(2);
                    System.out.println("back");
                    if (jlist.getSelectedValue() != null) {
                        String selected2 = jlist.getSelectedValue().toString();
                        System.out.println("Selected2 = " + selected2);
                        try {
                            File father = new File(new File(selected2).getParent());
                            File parent = father.getParentFile();
                            File[] parents = parent.listFiles();
                            yoursListModel.removeAllElements();
                            yoursListModel.addElement(back);
                            if (parents != null) {
                                for (File parent1 : parents) {
                                    yoursListModel.addElement(parent1.toString());
                                }
                            }
                        } catch (java.lang.NullPointerException e1) {
                            yoursListModel.removeAllElements();
                            File[] files = File.listRoots();
                            for (File file : files) {
                                yoursListModel.addElement(file.toString());
                            }
                        }

                    }


                } else if (jlist.getLastVisibleIndex() == 0) {
                    File fatherold1 = new File(fatherOld);
                    File[] fatherOlds2 = fatherold1.listFiles();
                    yoursListModel.removeAllElements();
                    yoursListModel.addElement(back);
                    if (fatherOlds2 != null) {
                        for (File fatherOlds : fatherOlds2) {
                            yoursListModel.addElement(fatherOlds.toString());
                        }
                    }
                }
            } else {
                    System.out.println("selected1 =" + selected);
                    File folder = new File(selected);
                    fatherOld = new File(selected).getParent();
                    System.out.println("fatherOld" + fatherOld);
                    if (folder.isDirectory()) {
                        File[] folders = folder.listFiles();
                        if (folders != null) {
                                yoursListModel.removeAllElements();
                                yoursListModel.addElement(back);
                                for (File folder1 : folders) {
                                    yoursListModel.addElement(folder1.toString());
                                }
                            System.out.println("last index"+jlist.getLastVisibleIndex());

                        }
                    }
                }
            }
        }
    }

