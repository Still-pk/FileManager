package com.Pauls;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

class SceneModel {


    private DefaultTreeModel defaultTreeModel;
    private DefaultListModel<String> listModel;


    DefaultListModel<String> getListModel() {
        return listModel;
    }


    DefaultTreeModel getDefaultTreeModel() {
        return defaultTreeModel;
    }

    void SceneModelStart() {
        //Jtree model

        DefaultMutableTreeNode super_root = new DefaultMutableTreeNode("Root");
        defaultTreeModel = new DefaultTreeModel(super_root);
        File[] froots = File.listRoots();
        for (File froot : froots) {

            if (froot.isDirectory()){
                NodeDir dir = new NodeDir(froot);
                super_root.add(dir);
            }else {
                DefaultMutableTreeNode mroot = new DefaultMutableTreeNode(froot);
                super_root.add(mroot);}
        }


        //Jlist model
        listModel = new DefaultListModel<>();
        File[] files = File.listRoots();
        for (File file : files ) {
            listModel.addElement(file.toString());
        }
    }


}
