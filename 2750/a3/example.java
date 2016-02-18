import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;
import java.util.ArrayList;

/**
*
* @author crei
*
*
*
/**
*
* The template for what will be a GUI that gets generated 
*/
public class example extends JFrame implements exampleFieldEdit {
	private JFrame frame = new JFrame();
	private JPanel mainPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	private JPanel centrePanel = new JPanel();
	private JTextArea text = new JTextArea(16, 20);
	private ArrayList<JTextField> fieldArray = new ArrayList<JTextField>();
	private int i = 0;
	/* Takes the title that is parsed to be the name of the frame as well as ArrayLists for what goes on
	the GUI */
	public example (String title, ArrayList<String> fieldValue, ArrayList<String> buttonValue, ArrayList<String> fieldTypes, ArrayList<String> buttonTypes) {
		super(title);
		setSize(500, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel.setLayout(new BorderLayout());
		for(i = 0; i < fieldValue.size(); i++) {
			JLabel name = new JLabel(fieldValue.get(i));
			upperPanel.setLayout(new GridLayout(8,2));
			upperPanel.add(name);
			JTextField field = new JTextField();
			field.setColumns(30);
			upperPanel.add(field);
			fieldArray.add(field);
		}
		JButton[] button = new JButton[buttonValue.size()];
		centrePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		button[0] = new JButton(buttonValue.get(0));
		button[0].addActionListener(new AddListener());
		centrePanel.add(button[0]);
		button[1] = new JButton(buttonValue.get(1));
		button[1].addActionListener(new UpdateListener());
		centrePanel.add(button[1]);
		button[2] = new JButton(buttonValue.get(2));
		button[2].addActionListener(new DeleteListener());
		centrePanel.add(button[2]);
		button[3] = new JButton(buttonValue.get(3));
		button[3].addActionListener(new QueryListener());
		centrePanel.add(button[3]);
		JScrollPane scrolledText = new JScrollPane(text);
		text.setEditable(false);
		text.setText("");
		scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel(" "));
		centrePanel.add(new JLabel("Status"));
		mainPanel.add(scrolledText, BorderLayout.SOUTH);
		mainPanel.add(centrePanel, BorderLayout.CENTER);
		mainPanel.add(upperPanel, BorderLayout.NORTH);
		add(mainPanel);
		setVisible(true);
	}
	public static void main(String[] args) {
		ArrayList<String> fieldValue = new ArrayList<String>();
		ArrayList<String> fieldType = new ArrayList<String>();
		ArrayList<String> buttonValue = new ArrayList<String>();
		ArrayList<String> buttonType = new ArrayList<String>();
		fieldValue.add("Name");
		fieldType.add("string");
		fieldValue.add("Student_ID");
		fieldType.add("integer");
		fieldValue.add("A1");
		fieldType.add("integer");
		fieldValue.add("A2");
		fieldType.add("integer");
		fieldValue.add("A3");
		fieldType.add("integer");
		fieldValue.add("A4");
		fieldType.add("integer");
		fieldValue.add("Average");
		fieldType.add("double");
		buttonValue.add("Add");
		buttonType.add("AddListener");
		buttonValue.add("Update");
		buttonType.add("UpdateListener");
		buttonValue.add("Delete");
		buttonType.add("DeleteListener");
		buttonValue.add("Query");
		buttonType.add("QueryListener");
		example gui = new example("compiled_example", fieldValue, buttonValue,  fieldType, buttonType);	}

	public String getDCName {
		return fieldArray.get(0).getText()
	}
	public void setDCName(String info) {
		fieldArray.get(0).setText()
	}
	public String getDCStudent_ID {
		return fieldArray.get(1).getText()
	}
	public void setDCStudent_ID(String info) {
		fieldArray.get(1).setText()
	}
	public String getDCA1 {
		return fieldArray.get(2).getText()
	}
	public void setDCA1(String info) {
		fieldArray.get(2).setText()
	}
	public String getDCA2 {
		return fieldArray.get(3).getText()
	}
	public void setDCA2(String info) {
		fieldArray.get(3).setText()
	}
	public String getDCA3 {
		return fieldArray.get(4).getText()
	}
	public void setDCA3(String info) {
		fieldArray.get(4).setText()
	}
	public String getDCA4 {
		return fieldArray.get(5).getText()
	}
	public void setDCA4(String info) {
		fieldArray.get(5).setText()
	}
	public String getDCAverage {
		return fieldArray.get(6).getText()
	}
	public void setDCAverage(String info) {
		fieldArray.get(6).setText()
	}
	public void appendToStatusArea(String info) {
		text.append(info);
	}
}
