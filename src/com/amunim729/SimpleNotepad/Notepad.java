package com.amunim729.SimpleNotepad;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.http.WebSocket;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad implements ActionListener {

    JFrame frame;
    JTextArea note;
    Font font;
    File fileContainer;

    public void makeInstance() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        font = new Font("Arial", Font.PLAIN, 15);
        note = new JTextArea();
        // MENU
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("help");

        JScrollPane scrollText = new JScrollPane(note);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollText.setVisible(true);
        note.setFont(font);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);

        frame.getContentPane().add(scrollText);
        // FILE MENU
        JMenuItem new_ = new JMenuItem("New");
        new_.addActionListener(this);
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this);
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(new_); file.add(open); file.add(save); file.addSeparator(); file.add(exit);
        // EDIT MENU
        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        edit.add(cut); edit.add(copy); edit.add(paste);

        // HELP MENU
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(this);
        help.add(about);

        menuBar.add(file); menuBar.add(edit); menuBar.add(help);
        frame.setJMenuBar(menuBar);

        ImageIcon image = new ImageIcon("C:\\Users\\Abdulmunim\\Desktop\\Java\\SimpleNotepad\\src\\com\\amunim729\\SimpleNotepad\\img.png");
        frame.setIconImage(image.getImage());

//        frame.addWindowListener(this);
        frame.setTitle("Untitled Text");
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        if (e.getActionCommand().equals("New")) {
            // new
            frame.setTitle("NewDocument.txt - Notepad");

            note.setText("");
            fileContainer = null;
        } else if (e.getActionCommand().equals("Open")) {
            int status = fileChooser.showDialog(null, "Open");
            if (status == JFileChooser.APPROVE_OPTION) {
                try  {
                    File openedFile = fileChooser.getSelectedFile();
                    openFile(openedFile.getAbsolutePath());
                    frame.setTitle(openedFile.getName() + " - Notepad");
                    fileContainer = openedFile;

                } catch (IOException i) {}
            }
        } else if (e.getActionCommand().equals("Save")) {
            if (fileContainer != null) {
                fileChooser.setCurrentDirectory(fileContainer);
                fileChooser.setSelectedFile(fileContainer);
                File openedFile = fileChooser.getSelectedFile();
                try { saveFile(openedFile.getAbsolutePath()); } catch (IOException i){}

            } else {
                fileChooser.setSelectedFile(new File("New Document.txt"));
            int status = fileChooser.showDialog(null, "Save");
            if (status == JFileChooser.APPROVE_OPTION) {
                try {
                File openedFile = fileChooser.getSelectedFile();
                saveFile(openedFile.getAbsolutePath());
                frame.setTitle(openedFile.getName() + " - Notepad");
                fileContainer = openedFile;
                } catch (IOException i) {
                }
            }
            }
        } else if (e.getActionCommand().equals("Exit")) {
            Exit();
        } else if (e.getActionCommand().equals("Copy")) {
            note.copy();
        } else if (e.getActionCommand().equals("Paste")) {
            note.paste();
        } else if(e.getActionCommand().equals("Cut")) {
            note.cut();
        } else if (e.getActionCommand().equals("About")) {
            JOptionPane.showMessageDialog(frame, "Version: 1.0.0 \nMade by: Abdulmunim Jundurahman, as a swing and file I/O in java practice", "About Notepad", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void openFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String l;
        note.setText("");
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while ((l=reader.readLine()) != null){
            note.setText(note.getText() + l + "\r\n");
        }
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        reader.close();
    }
    public void saveFile(String path) throws IOException {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream o = new DataOutputStream(new FileOutputStream(path));
        o.writeBytes(note.getText());
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void Exit() {
        System.exit(0);
    }
}
