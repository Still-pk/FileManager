package com.company;
import javax.swing.tree.DefaultMutableTreeNode;
public class NodeDir extends DefaultMutableTreeNode {
    boolean isExpand;
    public NodeDir(Object userObject) {
        super(userObject);
    }
    @Override
    public boolean isLeaf() {
        return false;
    }
    public boolean isExpand() {
        return isExpand;
    }
    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}