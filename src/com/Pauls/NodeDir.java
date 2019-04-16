package com.Pauls;
import javax.swing.tree.DefaultMutableTreeNode;

public class NodeDir extends DefaultMutableTreeNode {

    NodeDir(Object userObject) {
        super(userObject);
    }
    @Override
    public boolean isLeaf() {
        return false;
    }
}