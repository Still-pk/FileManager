package com.Pauls;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;


class SceneView extends JFrame {

    private DefaultTreeModel myTreeModel;
    private ListMouseAdapter myAdapter;
    private int focusOwner;
    private JTree jt;
    private DefaultListModel<String> myListModel;
    private File cop;

    void setListModel(DefaultListModel<String> listModel) {
        this.myListModel = listModel;
    }


    void setMyTreeModel(DefaultTreeModel myTreeModel) {
        this.myTreeModel = myTreeModel;
    }


    void sceneViewStart() {
        JFrame jframe = new JFrame("My File manager");
        jframe.setSize(800, 800);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BorderLayout());


        // add tree with scroll panel
        jt = new JTree(myTreeModel);
        MyTreeWillExpandListener myTreeWillExpandListener = new MyTreeWillExpandListener(jt);
        jt.addTreeWillExpandListener(myTreeWillExpandListener);
        JScrollPane jScrollPane = new JScrollPane(jt);
        jScrollPane.setPreferredSize(new Dimension(200, 800));
        jt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
              focusOwner = 1;

            }
        });

        jframe.getContentPane().add(jScrollPane, BorderLayout.WEST);


        // add List with scroll panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 1));
        JList<String> list = new JList<>(myListModel);
        list.setSelectedIndex(0);

        JScrollPane jScrollPane1 = new JScrollPane(list);
        listPanel.add(jScrollPane1);
        list.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                 focusOwner = 2;

            }
        });
        myAdapter = new ListMouseAdapter(list, myListModel);
        list.addMouseListener(myAdapter);
        jframe.getContentPane().add(listPanel, BorderLayout.CENTER);



        // add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        jframe.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        //добавление кнопки создания файла
        JButton addButton = new JButton("    Create file  ");
        buttonPanel.add(addButton,BorderLayout.CENTER);
        addButton.addActionListener(e -> newFile(focusOwner, jt, myTreeModel, myListModel, myAdapter));


        //добавление кнопки удаление файла
        JButton deleteButton = new JButton(" Delete file ");
        buttonPanel.add(deleteButton,BorderLayout.CENTER);
        deleteButton.addActionListener(e -> delFile(focusOwner, jt,myTreeModel , list, myListModel));



        // добавление кнопки создать директорию
        JButton createDir = new JButton(" Сreate dir ");
        buttonPanel.add(createDir,BorderLayout.CENTER);
        createDir.addActionListener(e -> createDirectory(focusOwner, jt,myTreeModel, myListModel, myAdapter));



        // добавление кнопки удалить директорию
        JButton deleteDir = new JButton("  DeleteDir ");
        buttonPanel.add(deleteDir,BorderLayout.CENTER);
        deleteDir.addActionListener(e -> deleteDirectory(focusOwner, jt, myTreeModel, list, myListModel));


        // добавление кнопки копировать
        JButton copyFile = new JButton("Copy");
        buttonPanel.add(copyFile,BorderLayout.CENTER);
        copyFile.addActionListener(e -> copyFile(focusOwner, jt, list));

        // добавление кнопки вставить
        JButton pastFile = new JButton("Paste");
        buttonPanel.add(pastFile, BorderLayout.CENTER);
        pastFile.addActionListener(e -> pastFile(focusOwner, myListModel, myTreeModel, jt, myAdapter));



        jframe.setVisible(true);
    }

    private void pastFile(int check, DefaultListModel<String> myListModel, DefaultTreeModel defaultTreeModel, JTree jt, ListMouseAdapter myAdapter) {
        String[] nameSplit;
        if ((File.separator).equals("\\\\")) {
            nameSplit = cop.toString().replace('/', '\\').split("\\\\" );
        } else
        {
            nameSplit = cop.toString().split("/" );
        }

        if (check == 1) {


            for (String names : nameSplit) {
                System.out.println(names);
            }
            System.out.println(nameSplit[nameSplit.length - 1]);

            String sb = jt.getLastSelectedPathComponent().toString() +
                    File.separator + nameSplit[nameSplit.length - 1];
            File file = new File(sb);

            TreePath treePath = jt.getLeadSelectionPath();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
            ((DefaultMutableTreeNode)treePath.getLastPathComponent()).add(child);

            defaultTreeModel.reload((DefaultMutableTreeNode)treePath.getLastPathComponent());
            try {
                copy(cop, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if ( check == 2) {
            String dest = myAdapter.getFolder() + File.separator + nameSplit[nameSplit.length - 1];
            File file = new File(dest);
            myListModel.addElement(dest);
            try {
                copy(cop, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private  void copyFile(int check, JTree jt, JList list) {
        if (check == 1) {

            cop =  new File (jt.getLastSelectedPathComponent().toString());

            System.out.println(cop.toString());
            }
        if (check == 2) {
            if (list.getSelectedValue() == null) {
                cop = new File (myAdapter.getFolder().toString());
                System.out.println(cop.toString());
            }
            else {
                cop = new File(list.getSelectedValue().toString());

                System.out.println(cop.toString());
            }
        }
    }

    private static void deleteDirectory(int check, JTree jt, DefaultTreeModel defaultTreeModel, JList list, DefaultListModel<String> myListModel) {
    if (check == 1) {
        File file = new File(jt.getLastSelectedPathComponent().toString());
        TreePath parentSelection = jt.getLeadSelectionPath().getParentPath();
        TreePath treePath = jt.getLeadSelectionPath();
        jt.setSelectionPath(parentSelection);
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (!f.delete()) {
                    JOptionPane.showMessageDialog(null,"Something gone wrong");
                }
            }


            if (file.delete()){JOptionPane.showMessageDialog(null, "Directory has been deleted");}
        }
        defaultTreeModel.removeNodeFromParent((DefaultMutableTreeNode) treePath.getLastPathComponent());

    }
    if (check == 2) {
        File file = new File(list.getSelectedValue().toString());
        myListModel.remove(list.getSelectedIndex());

         //jt.expandPath(treePath);

        if(file.delete()){
            JOptionPane.showMessageDialog(null,"Directory has been deleted");
            System.out.println("файл удален");



        } else { System.out.println("Файла не обнаружено");}
    }
    }

    private static void createDirectory(int check, JTree jt, DefaultTreeModel defaultTreeModel, DefaultListModel<String> myListModel, ListMouseAdapter myAdapter) {
        String s = JOptionPane.showInputDialog("Write Folder name");
        if ((s != null) && (s.length() > 0)) {
            if (check == 1) {
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
                try {
                    boolean created = dir.mkdir();
                    if (created) {
                        System.out.println("Folder has been created");
                    }
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            if (check == 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(myAdapter.getFolder());
                System.out.println(myAdapter.getFolder());
                stringBuilder.append("\\");
                stringBuilder.append(s);
                File dir = new File(stringBuilder.toString());
                myListModel.addElement(dir.toString());
                try {
                    boolean created = dir.mkdir();
                    if (created) {
                        System.out.println("Folder has been created");
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Print something", "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void delFile(int check, JTree jt, DefaultTreeModel myTreeModel,JList jlist, DefaultListModel<String> myListModel) {
    if (check == 1) {
        File file = new File(jt.getLastSelectedPathComponent().toString());
        TreePath parentSelection = jt.getLeadSelectionPath().getParentPath();
        TreePath treePath = jt.getLeadSelectionPath();
        jt.setSelectionPath(parentSelection);
        myTreeModel.removeNodeFromParent((DefaultMutableTreeNode)treePath.getLastPathComponent());
        if(file.delete()){
            JOptionPane.showMessageDialog(null,"File has been deleted");
            System.out.println("файл удален");
        } else { System.out.println("Файла не обнаружено");}

    }
    if (check ==2) {
        File file = new File(jlist.getSelectedValue().toString());


        myListModel.remove(jlist.getSelectedIndex());

        if(file.delete()){
            JOptionPane.showMessageDialog(null,"File has been deleted");
            System.out.println("файл удален");

        } else { System.out.println("Файла не обнаружено");}


    }
    }


    private static void newFile(int check, JTree jt, DefaultTreeModel defaultTreeModel, DefaultListModel<String> defaultListModel, ListMouseAdapter mouseAdapter) {
        String s = JOptionPane.showInputDialog("Write File name");
        if ((s != null) && (s.length() > 0)) {

            if (check == 1) {
                String sb = jt.getLastSelectedPathComponent().toString() +
                        File.separator +
                        s;
                File newFile = new File(sb);
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

            }
            if (check == 2) {
                String string = mouseAdapter.getFolder() +
                        File.separator +
                        s;
                File file = new File(string);
                defaultListModel.addElement(file.toString());
                try
                {
                    boolean created = file.createNewFile();
                    if(created)
                        System.out.println("File has been created");
                        jt.updateUI();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"Print something","Try again",JOptionPane.ERROR_MESSAGE);
        }
    }

}


