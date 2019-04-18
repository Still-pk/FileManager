package com.Pauls;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.nio.file.Path;

public class MyTreeWillExpandListener implements TreeWillExpandListener {
    private JTree jt;

    MyTreeWillExpandListener(JTree jt) {
        this.jt = jt;
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event)  {
        DefaultMutableTreeNode mutableTreeNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
        TreePath selected = event.getPath();
        jt.setSelectionPath(selected);
        if (!(mutableTreeNode.toString().equals("Root"))){
            File myFolder = new File(mutableTreeNode.toString());

            File[] files = myFolder.listFiles();
            if (files != null) {
                for (File f : files ) {
                    if (f.isDirectory()){
                        NodeDir dir = new NodeDir(f);
                        mutableTreeNode.add(dir);
                    }else {
                        DefaultMutableTreeNode mfile = new DefaultMutableTreeNode(f);
                        mutableTreeNode.add(mfile);}
                }
            }
        }
    }


    @Override
    public void treeWillCollapse(TreeExpansionEvent event)  {
        TreePath selected = event.getPath().getParentPath();
        jt.setSelectionPath(selected);
    }
}
