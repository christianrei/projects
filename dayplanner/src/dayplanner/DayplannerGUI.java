package dayplanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * Class for the GUI that intializes all of the important variables and GUI
 * parts that are needed.
 * @author seeray
 */
public class DayplannerGUI extends JFrame {
    private Dayplanner activity = new Dayplanner();
    private ArrayList<String> results = new ArrayList();
    private JPanel menuPanel = new JPanel( );
    private JPanel mainPanel = new JPanel( );
    private JPanel buttonPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel featurePanel = new JPanel();
    private JPanel blankPanel = new JPanel();
    private JPanel typePanel = new JPanel();
    private JPanel titlePanel = new JPanel();
    private JPanel locationPanel = new JPanel();
    private JPanel commentPanel = new JPanel();
    private JLabel instruction = new JLabel();
    private JLabel label = new JLabel();
    private JLabel outputLabel = new JLabel();
    private String[] types = { 
        "Choose Activity", "Home", "School", "Other" 
    };
    private String[] commands = { 
        "Commands", "Add", "Search", "Quit" 
    };
    private JComboBox typeList = new JComboBox(types);
    private JComboBox commandsList = new JComboBox(commands);
    private JTextArea output = new JTextArea(8, 20);
    private JTextField title = null;
    private JTextField startTime = null;
    private JTextField endTime = null;
    private JTextField location = null;
    private JTextField comment = null;
    private String option = null;
    private String activityType = null;
    private String actionCommand;
    private boolean inputCheck;
    private int i;
    
    /**
     * Constructor that creates the window and shows the initial message while
     * hiding things that are not needed right now so they can be made visible
     * later easily.
     */
    public DayplannerGUI() {
        super("Day Planner");   /* Title of GUI */
        setSize(600, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        menuPanel.setLayout(new BorderLayout());
        commandsList.setSelectedIndex(0);
        commandsList.addActionListener(new commandListener());
        menuPanel.add(commandsList, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.NORTH);

        mainPanel.setLayout(new BorderLayout());
        instruction.setText("<html>Welcome to my Day Planner.<br><br><br>Choose a command from the \"Commands\" "
                + "menu above for adding<br>an activity, searching activities, or quitting the program.<html>");
        instruction.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(instruction, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        
        inputPanel.setLayout(new BorderLayout());
        featurePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        featurePanel.setPreferredSize(new Dimension(475, 260));     
        featurePanel.setMaximumSize(featurePanel.getPreferredSize()); 
        featurePanel.setMinimumSize(featurePanel.getPreferredSize());
        featurePanel.add(label);
        blankPanel.add(new JLabel("                                                           "));       /* to put type on the line under to match the assignment gui */
        featurePanel.add(blankPanel);
        typePanel.add(new JLabel("Type:"));
        typeList.setSelectedIndex(0);
        typePanel.add(typeList);
        featurePanel.add(typePanel);
        title = new JTextField("", 30);
        titlePanel.add(new JLabel("Title:"));
        titlePanel.add(title);
        featurePanel.add(titlePanel);
        startTime = new JTextField("", 30);
        featurePanel.add(new JLabel("Enter a starting time (year, month, day, hour, minute)"));
        featurePanel.add(startTime);
        endTime = new JTextField("", 30);
        featurePanel.add(new JLabel("Enter an ending time (year, month, day, hour, minute)"));
        featurePanel.add(endTime);
        location = new JTextField("", 30);
        locationPanel.add(new JLabel("Location"));
        locationPanel.add(location);
        comment = new JTextField("", 30);
        commentPanel.add(new JLabel("Comment"));
        commentPanel.add(comment);
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        inputPanel.add(featurePanel, BorderLayout.WEST);
        
        buttonPanel.setLayout(new GridLayout(8,1));
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new buttonPress());
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new buttonPress());
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(resetButton);
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(enterButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        inputPanel.add(buttonPanel, BorderLayout.EAST);
      
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        JScrollPane scrolledText = new JScrollPane(output);
        output.setEditable(false);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputPanel.add(scrolledText, BorderLayout.SOUTH);
        mainPanel.add(outputPanel, BorderLayout.SOUTH);
        
        inputPanel.setVisible(false);
        outputPanel.setVisible(false);
        locationPanel.setVisible(false);
        setVisible(true);
    }
    
    /**
     * Returns the text currently in the title text box.
     * @return 
     */
    public String getTText() {
        return title.getText();
    }
    
    /**
     * Returns the text currently in the start time text box.
     * @return 
     */
    public String getSTText() {
        return startTime.getText();
    }
    
    /**
     * Returns the text currently in the end time text box.
     * @return 
     */
    public String getETText() {
        return endTime.getText();
    }
    
    /**
     * Returns the text currently in the comment text box.
     * @return 
     */
    public String getCText() {
        return comment.getText();
    }
    
    /**
     * Returns the text currently in the location text box.
     * @return 
     */
    public String getLText() {
        return location.getText();
    }
    
    /**
     * Returns the type that is currently in the combo box
     * @return 
     */
    public String getTSelection() {
        return activityType;
    }
    
    /**
     * If the activity chosen when adding is other location must appear
     * otherwise it should not so this rearranges the display when necessary.
     */
    private class typeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox typeBox = (JComboBox)e.getSource();
            activityType = (String)typeBox.getSelectedItem();
            if(activityType.equals("Other")) {
                location.setText("");
                locationPanel.setVisible(true);
                commentPanel.setVisible(true);
                featurePanel.revalidate();
                repaint();
            }
            else {
                location.setText("");
                locationPanel.setVisible(false);
                featurePanel.revalidate();
                repaint();
            }
        }
    }
    
    /**
     * Sets the window to the proper display that is chosen by the user.
     */
    private class commandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox options = (JComboBox)e.getSource();
            option = (String)options.getSelectedItem();
            if(option.equals("Add")) {
                mainPanel.remove(instruction);
                inputPanel.setVisible(true);
                outputPanel.setVisible(true);
                commandsList.setSelectedIndex(0);
                typeList.addActionListener(new typeListener());
                featurePanel.add(locationPanel);
                featurePanel.add(commentPanel);
                label.setText("Adding an activity:");
                outputLabel.setText("Messages:");
                output.setText("");
                featurePanel.revalidate();
                repaint();
            }
            else if(option.equals("Search")) {
                mainPanel.remove(instruction);
                inputPanel.setVisible(true);
                outputPanel.setVisible(true);
                commandsList.setSelectedIndex(0);
                typeList.removeActionListener(new typeListener());
                featurePanel.remove(commentPanel);
                featurePanel.remove(locationPanel);
                label.setText("Searching Activities:");
                outputLabel.setText("Search Results:");
                output.setText("");
                featurePanel.revalidate();
                repaint(); 
            }
            else if(option.equals("Quit")) {
                System.exit(0);
            }
        }
    }
    
    /**
     * What happens when the user presses one of the two buttons on screen.
     * This incorporates the dayplanner code. And creates or searches an
     * activity.
     */
    private class buttonPress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionCommand = e.getActionCommand();
            if (actionCommand.equals("Reset")) {
                title.setText("");
                startTime.setText("");
                endTime.setText("");
                comment.setText("");
                location.setText("");
            }
            else if (actionCommand.equals("Enter")) {
                if(option.equals("Add")) {
                    if(!title.getText().equals("")) {  /* title can't be blank as specified */
                        inputCheck = activity.addActivity(getTSelection(), getTText(), getSTText(), getETText(), getLText(), getCText());
                        if(inputCheck == false) {
                            output.append("Error: Input is invalid, activity not added.\n");
                        }
                        else if(inputCheck == true) {
                            output.append("The activity has been added to the Day Planner.\n");
                        }
                    }
                    else {
                        output.append("Error: Title must have an input.\n");
                    }
                }
                else if(option.equals("Search")) {
                    results = activity.searchActivity(getTSelection(), getTText(), getSTText(), getETText());
                    if(results == null) {
                        output.append("Error: Input is invalid, search cancelled.\n");
                    } 
                    else if(results.isEmpty()) {
                        output.append("There were no matched activities.\n");
                    } 
                    else {
                        for(i = 0; i < results.size(); i++) {
                            if(activityType.equals("Home")) {
                                output.append(activityType + ": ");
                            }
                            else if(activityType.equals("School")) {
                                output.append(activityType + ": ");
                            }
                            output.append(results.get(i) + "\n");
                        }
                    }
                }
            }
        }
    }
}


