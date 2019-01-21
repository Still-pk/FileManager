package com.company;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        JFrame jframe = new JFrame("My File manager");
        jframe.setSize(800,800);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BorderLayout());
        File[] froots = File.listRoots();
        DefaultMutableTreeNode super_root = new DefaultMutableTreeNode("My Computer");
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(super_root);
        for (int i = 0; i < froots.length; i++) {
            File froot = froots[i];
            if (froot.isDirectory()){
                NodeDir dir = new NodeDir(froot);
                super_root.add(dir);
            }else {
                DefaultMutableTreeNode mroot = new DefaultMutableTreeNode(froot);
                super_root.add(mroot);}
        }
        JTree jt = new JTree(defaultTreeModel);
        jt.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode mutableTreeNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                TreePath selected = event.getPath();
                jt.setSelectionPath(selected);
                if (!(mutableTreeNode.toString()== "My Computer")) {
                    File myFolder = new File(mutableTreeNode.toString());
                    File[] files = myFolder.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (file.isDirectory()){
                            NodeDir dir = new NodeDir(file);
                            mutableTreeNode.add(dir);
                        }else {
                            DefaultMutableTreeNode mfile = new DefaultMutableTreeNode(file);
                            mutableTreeNode.add(mfile);}
                    }
                }}
            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                TreePath selected = event.getPath().getParentPath();
                jt.setSelectionPath(selected);
            }
        });
        JScrollPane jScrollPane = new JScrollPane(jt);
        jScrollPane.setPreferredSize(new Dimension(200,800));
        jframe.getContentPane().add(jScrollPane,BorderLayout.WEST);
        jframe.setVisible(true);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        jframe.getContentPane().add(buttonPanel,BorderLayout.CENTER);
        //добавление кнопки создания файла
        JButton addButton = new JButton("    Add file  ");
        buttonPanel.add(addButton,BorderLayout.CENTER);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Write File name");
                if ((s != null) && (s.length() > 0)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(jt.getLastSelectedPathComponent().toString());
                    System.out.println(jt.getLastSelectedPathComponent().toString());
                    sb.append("\\");
                    sb.append(s);
                    File newFile = new File(sb.toString());
                    TreePath treePath = jt.getLeadSelectionPath();
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(newFile);
                    ((DefaultMutableTreeNode)treePath.getLastPathComponent()).add(child);
                    defaultTreeModel.reload((DefaultMutableTreeNode)treePath.getLastPathComponent());
                    try
                    {
                        boolean created = newFile.createNewFile();
                        if(created)
                            System.out.println("File has been created");
                    }
                    catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }else {JOptionPane.showMessageDialog(null,"Print something","Try again",JOptionPane.ERROR_MESSAGE);}}
        });
        //добавление кнопки удаление файла
        JButton deleteButton = new JButton(" Delete file ");
        buttonPanel.add(deleteButton,BorderLayout.CENTER);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(jt.getLastSelectedPathComponent().toString());
                TreePath parentSelection = jt.getLeadSelectionPath().getParentPath();
                TreePath treePath = jt.getLeadSelectionPath();
                jt.setSelectionPath(parentSelection);
                defaultTreeModel.removeNodeFromParent((DefaultMutableTreeNode)treePath.getLastPathComponent());
                if(file.delete()){
                    JOptionPane.showMessageDialog(null,"File has been deleted");
                    System.out.println("файл удален");
                }else{ System.out.println("Файла не обнаружено");}
            }
        });
        // добавление кнопки создать директорию
        JButton createDir = new JButton(" Сreate dir ");
        buttonPanel.add(createDir,BorderLayout.CENTER);
        createDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Write Folder name");
                if ((s != null) && (s.length() > 0)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(jt.getLastSelectedPathComponent().toString());
                    System.out.println(jt.getLastSelectedPathComponent().toString());
                    sb.append("\\");
                    sb.append(s);
                    File dir = new File(sb.toString());
                    TreePath treePath = jt.getLeadSelectionPath();
                    NodeDir child = new NodeDir(dir);
                    ((DefaultMutableTreeNode) treePath.getLastPathComponent()).add(child);
                    defaultTreeModel.reload((DefaultMutableTreeNode) treePath.getLastPathComponent());
                    boolean created = dir.mkdir();
                    if (created){
                        System.out.println("Folder has been created");
                    }else {JOptionPane.showMessageDialog(null,"Print something","Try again",JOptionPane.ERROR_MESSAGE);}
                }}
        });
        // добавление кнопки удалить директорию
        JButton deleteDir = new JButton("  DeleteDir ");
        buttonPanel.add(deleteDir,BorderLayout.CENTER);
        deleteDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}