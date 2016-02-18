import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import static javax.swing.border.TitledBorder.BOTTOM;
import static javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import java.io.InputStreamReader;

/**
 *
 * @author crei
 */

/* The intial text editor GUI to create, edit and save .config files */
public class Dialogc extends JFrame {
    private JMenuBar menu = new JMenuBar();
    private JMenu test = new JMenu();
    private JFrame frame = new JFrame();
    private JTextArea text = new JTextArea(8, 20);
    private JPanel mainPanel = new JPanel();
    private JPanel projectPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel update = new JLabel();
    private JLabel currentLabel = new JLabel("Current Project:");
    private ImageIcon stop = new ImageIcon("stop.jpeg");
    private ImageIcon play = new ImageIcon("play.png");
    private ImageIcon newF = new ImageIcon("new.jpeg");
    private ImageIcon open = new ImageIcon("file.png");
    private ImageIcon save = new ImageIcon("save.jpg");
    private ImageIcon sas = new ImageIcon("sas.jpeg");
    private JMenuItem newSelect = new JMenuItem("New");
    private JMenuItem openSelect = new JMenuItem("Open");
    private JMenuItem saveSelect = new JMenuItem("Save");
    private JMenuItem saveasSelect = new JMenuItem("Save As");
    private JMenuItem exitSelect = new JMenuItem("Quit");
    private JMenuItem compileSelect = new JMenuItem("Compile");
    private JMenuItem compilerunSelect = new JMenuItem("Compile and Run");
    private JMenuItem javaCompileSelect = new JMenuItem("Java Compiler");
    private JMenuItem compileOptionSelect = new JMenuItem("Compile options");
    private JMenuItem javaRunSelect = new JMenuItem("Java Run-time");
    private JMenuItem runtimeSelect = new JMenuItem("Run-time options");
    private JMenuItem directorySelect = new JMenuItem("Working Directory");
    private JMenuItem helpSelect = new JMenuItem("Help");
    private JMenuItem aboutSelect = new JMenuItem("About");
    private JButton stopButton = new JButton(stop);
    private JButton playButton = new JButton(play);
    private JButton openButton = new JButton(open);
    private JButton newButton = new JButton(newF);
    private JButton saveButton = new JButton(save);
    private JButton saveasButton = new JButton(sas);
    private JRadioButtonMenuItem rbLY = new JRadioButtonMenuItem("Lex and Yacc Compiler");
    private JRadioButtonMenuItem rbIDE = new JRadioButtonMenuItem("IDE Compiler");
    private int changed = 0;
    private int newFile = 1;
    private int hasName = 0;
    private int compileType = 0;
    private int choice;
    private int i;
    private String fileName = "untitled.config";
    private String noExt = "untitled";
    private String compileOptions = "";
    private String javaCompiler = "javac";
    private String javarunOption = "java";
    private String directory = ".";
    private String runtimeOption = "";
    private String input;
    private String extension;
    public Dialogc() {
        super("Dialogc");
        setSize(625, 725);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(newFile == 1) {
                    System.exit(0);
                }
                else {
                    if(changed == 1) {
                        Object[] yesNo = { "Yes, save", "No, quit now", "Cancel" };
                        choice = JOptionPane.showOptionDialog(frame, "The file has been modified, \n" + "Would you like to save?", "Are you sure you want to quit?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesNo, yesNo[2]);
                        if(choice == 1) {
                            System.exit(0);
                        }
                        else if(choice == 0) {
                            saveFile(fileName);
                            System.exit(0);
                        }
                    }
                    else {
                        System.exit(0);
                    }
                }
            }
        });
        setLayout(new BorderLayout());
        update.setText("untitled.config");
        setJMenuBar(menu); /* set to frame */
        JMenu fileMenu = new JMenu("File");
        JMenu compileMenu = new JMenu("Compile");
        JMenu configMenu = new JMenu("Config");
        JMenu helpMenu = new JMenu("Help");
        menu.add(fileMenu);
        menu.add(compileMenu);
        menu.add(configMenu);
        menu.add(helpMenu);
        
        /* Keyboard shortcuts. Hold control and the appropriate button when not entering text. */
        newSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveasSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        exitSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        compileSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        compilerunSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        javaCompileSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        compileOptionSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        javaRunSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        runtimeSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        directorySelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        helpSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        aboutSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        
        rbLY.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        rbIDE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        
        fileMenu.add(newSelect);
        fileMenu.add(openSelect);
        fileMenu.add(saveSelect);
        fileMenu.add(saveasSelect);
        fileMenu.add(exitSelect);
        newSelect.addActionListener(new filePress());
        openSelect.addActionListener(new filePress());
        saveSelect.addActionListener(new filePress());
        saveasSelect.addActionListener(new filePress());
        exitSelect.addActionListener(new filePress());
        compileMenu.add(compileSelect);
        compileMenu.add(compilerunSelect);
        compileSelect.addActionListener(new compilePress());
        compilerunSelect.addActionListener(new compilePress());
        configMenu.add(javaCompileSelect);
        configMenu.add(compileOptionSelect);
        configMenu.add(javaRunSelect);
        configMenu.add(runtimeSelect);
        configMenu.add(directorySelect);
        javaCompileSelect.addActionListener(new configPress());
        compileOptionSelect.addActionListener(new configPress());
        javaRunSelect.addActionListener(new configPress());
        runtimeSelect.addActionListener(new configPress());
        directorySelect.addActionListener(new configPress());
        helpMenu.add(helpSelect);
        helpMenu.add(aboutSelect);
        helpSelect.addActionListener(new helpPress());
        aboutSelect.addActionListener(new helpPress());
        ButtonGroup group = new ButtonGroup();
        
        configMenu.addSeparator();   /* not working??? */
        rbLY.setSelected(true);
        group.add(rbLY);
        configMenu.add(rbLY);
        rbLY.addActionListener(new configPress());
        
        group.add(rbIDE);
        configMenu.add(rbIDE);
        rbIDE.addActionListener(new configPress());
        
        newButton.addActionListener(new buttonPress());
        openButton.addActionListener(new buttonPress());
        stopButton.addActionListener(new buttonPress());
        playButton.addActionListener(new buttonPress());
        saveButton.addActionListener(new buttonPress());
        saveasButton.addActionListener(new buttonPress());
        
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setPreferredSize(new Dimension(600, 75));
        buttonPanel.add(newButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveasButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.setVisible(true);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.setVisible(true);
        add(mainPanel);
        
        JScrollPane scrolledText = new JScrollPane(text);
        text.setEditable(true);
        text.setText("");
        input = text.getText();
        Document document = text.getDocument();
        document.addDocumentListener(new textListener());
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrolledText, BorderLayout.CENTER);
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), fileName, DEFAULT_JUSTIFICATION, BOTTOM));
        
        projectPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        projectPanel.add(currentLabel);
        projectPanel.add(update);
        mainPanel.add(projectPanel, BorderLayout.SOUTH);
    }
    
    /* Function to save a file in the directory, adjust its name properly and change neccessary values */
    void saveFile(String name) {  /* change must always become one if something done. */
        try {
            i = fileName.lastIndexOf('.');
            if(i > 0) {
                extension = fileName.substring(i);
                if(!".config".equals(extension)) {
                    noExt = fileName;
                    fileName = fileName + ".config";
                }
                else {
                    System.out.println("Check for extra periods");
                }
            }
            else if(i == -1) {
                noExt = fileName;
                fileName = fileName + ".config";
            }
            File f = new File(fileName);
            FileWriter saved = new FileWriter(f);
            text.write(saved);
        } catch(IOException e) {
            System.out.println("Error opening file");
        }
        changed = 0;
        hasName = 1;
        newFile = 0;
        buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), fileName, DEFAULT_JUSTIFICATION, BOTTOM));
        update.setText(fileName);
    }
    
    /* Compiles and runs the generated program using ParameterManager parsing */
    void compileAndRunIDE() {
        if(changed == 0 && newFile == 0) {
            System.out.println(noExt);
            parseConfig parse = new parseConfig(text.getText(), noExt);
            try {
                Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " + noExt + ".java");
                BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                pro.waitFor();
                Process pro2 = Runtime.getRuntime().exec(javarunOption + " " + runtimeOption + " " + noExt);
            } catch(Exception e2) {
                System.out.println("Compile Error.");
            }
        }
        else {
            fileName = (String)JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",JOptionPane.PLAIN_MESSAGE, save, null, fileName);
            saveFile(fileName);
            parseConfig parse = new parseConfig(text.getText(), noExt);
            try {
                Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " + noExt + ".java");
                BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                pro.waitFor();
                Process pro2 = Runtime.getRuntime().exec(javarunOption + " " + runtimeOption + " " + noExt);
            } catch(Exception e2) {
                System.out.println("Compile Error.");
            }
        }
    }
    
    /* Compiles and runs the generated program using lex and yacc parsing */
    void compileAndRunLY() {
         if(changed == 0 && newFile == 0) {
             try {
                 Process parse = Runtime.getRuntime().exec("./yadc " + noExt);
                 parse.waitFor();
                 Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " + noExt + ".java");
                 BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                 String line = null;
                 while ((line = in.readLine()) != null) {
                     System.out.println(line);
                 }
                 pro.waitFor();
                 Process pro2 = Runtime.getRuntime().exec(javarunOption + " " + runtimeOption + " " + noExt);
             } catch(Exception e2) {
                 System.out.println("Compile Error.");
             }
         }
         else {
             fileName = (String)JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",JOptionPane.PLAIN_MESSAGE, save, null, fileName);
             saveFile(fileName);
             try {
                 Process parse = Runtime.getRuntime().exec("./yadc " + noExt);
                 parse.waitFor();
                 Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " + noExt + ".java");
                 BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                 String line = null;
                 while ((line = in.readLine()) != null) {
                     System.out.println(line);
                 }
                 pro.waitFor();
                 Process pro2 = Runtime.getRuntime().exec(javarunOption + " " + runtimeOption + " " + noExt);
             } catch(Exception e2) {
                 System.out.println("Compile Error.");
             }
         }
    }
    
    public static boolean setDirectory(String directory)
    {
        boolean result = false;
        File newDirectory;
        newDirectory = new File(directory).getAbsoluteFile();
        if(newDirectory.exists()) {
            result = (System.setProperty("user.dir", newDirectory.getAbsolutePath()) != null);
        }
        return result;
    }
    
    /* Listener for when anything happens to the text area */
    private class textListener implements DocumentListener {
        @Override                          /*Must use changedUpdate because DocumentListener is are abstract*/
        public void changedUpdate(DocumentEvent e) {
            System.out.println(e);
            changed = 1;
            newFile = 0;
            update.setText(fileName + "[modified]");
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            changed = 1;
            newFile = 0;
            update.setText(fileName + "[modified]");
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            if(hasName != 0) {
                changed = 1;
                newFile = 0;
                update.setText(fileName + "[modified]");
            }
        }
    }
    
    /* The code to make the buttons do the appropriate actions when clicked. */
    private class buttonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //actionCommand = e.getActionCommand();
            if(e.getSource() == newButton) {
                buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "untitled.config", DEFAULT_JUSTIFICATION, BOTTOM));
                update.setText("untitled.config");
                newFile = 1;
                changed = 0;
                hasName = 0;
                text.setText("");
            }
            else if(e.getSource() == playButton) {   /* RUNS */
                if(compileType == 1) {
                    compileAndRunIDE();
                }
                else {
                    compileAndRunLY();
                }
            }
            else if(e.getSource() == stopButton) {   /* stop running */
                if(changed == 0) {
                    System.exit(0);
                }
                else {
                    Object[] yesNo = { "Yes, save", "No, quit now", "Cancel" };
                    choice = JOptionPane.showOptionDialog(frame, "The file has been modified, \n"
                                                          + "Would you like to save?", "Are you sure you want to quit?",
                                                          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                          yesNo, yesNo[2]);
                    if(choice == 1) {
                        System.exit(0);
                    }
                    else if(choice == 0) {
                        if(newFile == 1 || hasName == 0) {
                            fileName = JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project", JOptionPane.PLAIN_MESSAGE);
                            saveFile(fileName);
                        }
                        else {
                            saveFile(fileName);
                        }
                    }
                }
            }
            else if(e.getSource() == openButton) {    /* open save and saveas are same as in file */
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Config files", "config");
                chooser.setFileFilter(filter);
                int result = chooser.showOpenDialog(frame);
                if(result == JFileChooser.APPROVE_OPTION) {
                    fileName = chooser.getSelectedFile().getName();
                    i = fileName.indexOf(".");
                    noExt = fileName.substring(0, i);
                }
                try {
                    FileReader reader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    text.read(bufferedReader, null);
                    bufferedReader.close();
                } catch(Exception e2) {
                    System.out.println("Error file not found or IOException");
                }
                buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), fileName, DEFAULT_JUSTIFICATION, BOTTOM));
                update.setText(fileName);
                newFile = 0;
                hasName = 1;
                changed = 0;
            }
            else if(e.getSource() == saveButton) {
                if(newFile == 1 || hasName == 0) {
                    fileName = JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",
                                                           JOptionPane.PLAIN_MESSAGE);
                    saveFile(fileName);
                }
                else {
                    saveFile(fileName);
                }
            }
            else if(e.getSource() == saveasButton) {
                fileName = (String)JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",
                                                               JOptionPane.PLAIN_MESSAGE, save, null, fileName);
                saveFile(fileName);
            }
        }
    }
    
    /* The code for what the options under the file menu actually do. */
    private class filePress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == newSelect) {
                buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "untitled.config",DEFAULT_JUSTIFICATION, BOTTOM));
                update.setText("untitled.config");
                newFile = 1;
                hasName = 0;
                changed = 0;
                text.setText("");
            }
            else if(e.getSource() == openSelect) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Config files", "config");
                chooser.setFileFilter(filter);
                int result = chooser.showOpenDialog(frame);
                if(result == JFileChooser.APPROVE_OPTION) {
                    fileName = chooser.getSelectedFile().getName();
                    i = fileName.indexOf(".");
                    noExt = fileName.substring(0, i);
                }
                try {
                    FileReader reader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    text.read(bufferedReader, null);
                    bufferedReader.close();
                } catch(Exception e2) {
                    System.out.println("File not opened or IOException");
                }
                buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), fileName, DEFAULT_JUSTIFICATION, BOTTOM));
                update.setText(fileName);
                newFile = 0;
                hasName = 1;
                changed = 0;
            }
            else if(e.getSource() == saveSelect) {
                if(newFile == 1 || hasName == 0) {
                    fileName = JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",
                                                           JOptionPane.PLAIN_MESSAGE);
                    saveFile(fileName);
                }
                else {
                    saveFile(fileName);
                }
            }
            else if(e.getSource() == saveasSelect) {
                fileName = (String)JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",
                                                               JOptionPane.PLAIN_MESSAGE, save, null, fileName);
                saveFile(fileName);
            }
            else if(e.getSource() == exitSelect) {
                if(changed == 0) {
                    System.exit(0);
                }
                else {
                    Object[] yesNo = { "Yes, save", "No, quit now", "Cancel" };
                    choice = JOptionPane.showOptionDialog(frame, "The file has been modified, \n"
                                                          + "Would you like to save?", "Are you sure you want to quit?",
                                                          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                                          yesNo, yesNo[2]);
                    if(choice == 1) {
                        System.exit(0);
                    }
                    else if(choice == 0) {
                        if(newFile == 1 || hasName == 0) {
                            fileName = JOptionPane.showInputDialog(frame, "Enter the project name:",
                                                                   "Save Project", JOptionPane.PLAIN_MESSAGE);
                            saveFile(fileName);
                        }
                        else {
                            saveFile(fileName);
                        }
                    }
                }
            }
        }
    }
    private class compilePress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == compileSelect) {         /* ONLY COMPILES */
                if(compileType == 1) {
                    if(changed == 0 && newFile == 0) {
                        parseConfig parse = new parseConfig(text.getText(), noExt);
                        try {
                            Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " + noExt + ".java");
                            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                            String line = null;
                            while((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            pro.waitFor();
                        }
                        catch(Exception e2) {
                            System.out.println("Compile error");
                        }
                    }
                    else {
                        fileName = (String)JOptionPane.showInputDialog(frame, "Enter the project name:", "Save Project",JOptionPane.PLAIN_MESSAGE, save, null, fileName);
                        saveFile(fileName);
                        parseConfig parse = new parseConfig(text.getText(), noExt);
                        try {
                            Process pro = Runtime.getRuntime().exec(javaCompiler + " " + compileOptions + " " +     noExt + ".java");
                            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                            String line = null;
                            while((line = in.readLine()) != null) {
                                System.out.println(line);
                            }
                            pro.waitFor();
                        }
                        catch(Exception e2) {
                            System.out.println("Compile error");
                        }
                    }
                }
                else {
                    /*
                     Process parse = Runtime.getRuntime().exec("./yadc " + noExt);
                     parse.waitFor();
                     */
                }
            }
            else if(e.getSource() == compilerunSelect) {  /* RUNS */
                if(compileType == 1) {
                    compileAndRunIDE();
                }
                else {
                    compileAndRunLY();
                }
            }
        }
    }
    private class configPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == javaCompileSelect) {
                javaCompiler = (String)JOptionPane.showInputDialog(frame, "Enter the java compiler:", "Compiler path",JOptionPane.PLAIN_MESSAGE, open, null, javaCompiler);
            }
            else if(e.getSource() == javaRunSelect) {
                javarunOption = (String)JOptionPane.showInputDialog(frame, "Enter run command:", "Java Run-time",JOptionPane.PLAIN_MESSAGE, play, null, javarunOption);
            }
            else if(e.getSource() == compileOptionSelect) {
                compileOptions = (String)JOptionPane.showInputDialog(frame, "Enter compiler options:", "Compile Options",JOptionPane.PLAIN_MESSAGE, play, null, compileOptions);
            }
            else if(e.getSource() == directorySelect) {
                boolean success = true;
                directory = (String)JOptionPane.showInputDialog(frame, "Enter working directory:", "Working Directory",JOptionPane.PLAIN_MESSAGE, play, null, directory);
                success = setDirectory(directory);
                if(success == false) {
                    JOptionPane.showMessageDialog(frame, "Directory was not found.");
                }
            }
            else if(e.getSource() == runtimeSelect) {
                runtimeOption = (String)JOptionPane.showInputDialog(frame, "Enter run options:", "Runtime Options",JOptionPane.PLAIN_MESSAGE, play, null, runtimeOption);
            }
            else if(e.getSource() == rbIDE) {
                compileType = 1;
            }
            else if(e.getSource() == rbLY) {
                compileType = 0;
            }
        }
    }
    private class helpPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == helpSelect) {
                JOptionPane.showMessageDialog(frame, "None of the config functions have been done. Strange errors" +
                                              " with JNI that required changing \n of A1 to fix so my program is also unable to create the generated" +
                                              "gui. Assignment 1 was fixed to \n pass all tests" +
                                              "but still there was issues with the assignment 1 functions. The program was coded" +
                                              "entirely on MAC OS\n but I have included the location of jni.h for linux in my makefile." +
                                              "I began to create and\n have included the template for the gui that" +
                                              "would be generated. The interface was also not generated.");  
            }
            else if(e.getSource() == aboutSelect) {
                JOptionPane.showMessageDialog(frame, "Name: Christian Rei \nStudent Id:"
                                              + "0832859 \nDate: February 13, 2015\nCIS*2750 Assignment 2");  
            }
        }
    }
    /**
     * Calls the constructor and activates the initial text editor GUI
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dialogc gui = new Dialogc();
        gui.setVisible(true);
    }
}
