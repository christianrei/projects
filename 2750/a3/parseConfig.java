import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
/**
*
* @author crei
*/

/* Java file to parse what is typed in the config file and use the JNI c functions to create a GUI */
public class parseConfig {
    private String guiTitle;
	public parseConfig(String content, String fileName) {
		JNILIB jni = new JNILIB();
		int check = 1;
		int size;
		int i = 0;
		String value;
		String j;
        String editor;
		
		ArrayList<String> fieldValue = new ArrayList<String>();
		ArrayList<String> buttonValue = new ArrayList<String>();
		ArrayList<String> fieldTypes = new ArrayList<String>();
		ArrayList<String> buttonTypes = new ArrayList<String>();
        
        ArrayList<String> fieldEdit = new ArrayList<String>();
        ArrayList<String> buttonEdit = new ArrayList<String>();
        
		jni.pm_create(10);
		check = jni.pm_manage("title", 3, 1);
		check = jni.pm_manage("fields", 4, 1);
		check = jni.pm_manage("buttons", 4, 1);
		check = jni.pm_parseFrom(content, '#');
        if(check == 0) {
            System.out.println("Parse Error.");
            return;
        }
		guiTitle = jni.pm_getValue("title");
		value = jni.pm_getValue("fields");
		fieldValue.add(value);
		while(value != null) {
			if(value == null) {
				break;
			}
			value = jni.pm_getValue("fields");
			fieldValue.add(value);
		}
		i = 0;
		value = jni.pm_getValue("buttons");
		buttonValue.add(value);
		while(value != null) {
			if(value == null) {
				break;
			}
			value = jni.pm_getValue("buttons");
			buttonValue.add(value);
		}
		fieldValue.remove(fieldValue.size()-1);
		buttonValue.remove(buttonValue.size()-1);
		JNILIB jni2 = new JNILIB();
		for(i = 0; i < fieldValue.size(); i++) {
			j = fieldValue.get(i);
            System.out.println(j);
			check = jni2.pm_manage(j, 3, 1);
		}
		for(i = 0; i < buttonValue.size(); i++) {
			j = buttonValue.get(i);
			check = jni2.pm_manage(j, 3, 1);
		}
		check = jni2.pm_parseFrom(content, '#');
        if(check == 0) {
            System.out.println("Parse Error.");
            return;
        }
		for(i = 0; i < fieldValue.size(); i++) {
			value = jni2.pm_getValue(fieldValue.get(i));
            editor = "\"" + value;
            editor = editor + "\"";
			fieldTypes.add(value);
		}
        //System.out.println(fieldTypes);
		for(i = 0; i < buttonValue.size(); i++) {
			value = jni2.pm_getValue(buttonValue.get(i));
            editor = "\"" + value;
            editor = editor + "\"";
			buttonTypes.add(value);
		}
        
        for(i = 0; i < fieldValue.size(); i++) {
            editor = "\"" + fieldValue.get(i);
            editor = editor + "\"";
            fieldEdit.add(editor);
        }
        for(i = 0; i < buttonValue.size(); i++) {
            editor = "\"" + buttonValue.get(i);
            editor = editor + "\"";
            buttonEdit.add(editor);
        }
        //System.out.println(buttonTypes);
        try {
            PrintWriter writer = new PrintWriter(fileName + "FieldEdit.java", "UTF-8");
            writer.println("");
            writer.println("public interface " + fileName + "FieldEdit {");
            for(i = 0; i < fieldValue.size(); i++) {
                writer.println("\tpublic String getDC" + fieldValue.get(i) + "();");
                writer.println("\tpublic void setDC" + fieldValue.get(i) + "(String info);");
            }
            writer.println("\tpublic void appendToStatusArea(String info);");
            writer.println("}");
            writer.close();
        } catch(Exception e2) {
            System.out.println("Error creating interface.");
        }
        
        try {
            PrintWriter pw = new PrintWriter(fileName + ".java", "UTF-8");
            pw.println("import javax.swing.*;");
            pw.println("import java.awt.*;");
            pw.println("import java.awt.event.*;");
            pw.println("import java.io.BufferedReader;");
            pw.println("import javax.swing.border.BevelBorder;");
            pw.println("import javax.swing.event.*;");
            pw.println("import java.util.ArrayList;");
            pw.println("");
            pw.println("/**");
            pw.println("*");
            pw.println("* @author crei");
            pw.println("*");
            pw.println("*/");
            pw.println("");
            pw.println("/**");
            pw.println("*");
            pw.println("* The template for what will be a GUI that gets generated ");
            pw.println("*/");
            pw.println("public class " + fileName + " extends JFrame implements " + fileName +"FieldEdit {");
            pw.println("\tprivate JFrame frame = new JFrame();");
            pw.println("\tprivate JPanel mainPanel = new JPanel();");
            pw.println("\tprivate JPanel upperPanel = new JPanel();");
            pw.println("\tprivate JPanel centrePanel = new JPanel();");
            pw.println("\tprivate JTextArea text = new JTextArea(16, 20);");
            pw.println("\tprivate ArrayList<JTextField> fieldArray = new ArrayList<JTextField>();");
            pw.println("\tprivate int i = 0;");
            pw.println("\t/* Takes the title that is parsed to be the name of the frame as well as ArrayLists for what goes on");
            pw.println("\tthe GUI */");
            pw.println("\tpublic " + fileName + "(String title, ArrayList<String> fieldValue, ArrayList<String> buttonValue, ArrayList<String> fieldTypes, ArrayList<String> buttonTypes) {");
            pw.println("\t\tsuper(title);");
            pw.println("\t\tsetSize(500, 600);");
            pw.println("\t\tsetDefaultCloseOperation(EXIT_ON_CLOSE);");
            pw.println("\t\tmainPanel.setLayout(new BorderLayout());");
            pw.println("\t\tfor(i = 0; i < fieldValue.size(); i++) {");
            pw.println("\t\t\tJLabel name = new JLabel(fieldValue.get(i));");
            pw.println("\t\t\tupperPanel.setLayout(new GridLayout(8,2));");
            pw.println("\t\t\tupperPanel.add(name);");
            pw.println("\t\t\tJTextField field = new JTextField();");
            pw.println("\t\t\tfield.setColumns(30);");
            pw.println("\t\t\tupperPanel.add(field);");
            pw.println("\t\t\tfieldArray.add(field);");
            pw.println("\t\t}");
            pw.println("\t\tJButton[] button = new JButton[buttonValue.size()];");
            pw.println("\t\tcentrePanel.setLayout(new FlowLayout(FlowLayout.LEFT));");
            for(i = 0; i < buttonTypes.size(); i++) {
                pw.println("\t\tbutton[" + i + "] = new JButton(buttonValue.get(" + i + "));");
                pw.println("\t\tbutton[" + i + "].addActionListener(new " + buttonTypes.get(i) + "());");
                pw.println("\t\tcentrePanel.add(button[" + i + "]);");
            }
            pw.println("\t\tJScrollPane scrolledText = new JScrollPane(text);");
            pw.println("\t\ttext.setEditable(false);");
            pw.println("\t\ttext.setText(\"\");");
            pw.println("\t\tscrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);");
            pw.println("\t\tscrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\" \"));");
            pw.println("\t\tcentrePanel.add(new JLabel(\"Status\"));");
            pw.println("\t\tmainPanel.add(scrolledText, BorderLayout.SOUTH);");
            pw.println("\t\tmainPanel.add(centrePanel, BorderLayout.CENTER);");
            pw.println("\t\tmainPanel.add(upperPanel, BorderLayout.NORTH);");
            pw.println("\t\tadd(mainPanel);");
            pw.println("\t\tsetVisible(true);");
            pw.println("\t}");
            pw.println("\tpublic static void main(String[] args) {");
            pw.println("\t\tArrayList<String> fieldValue = new ArrayList<String>();");
            for(i = 0; i < fieldValue.size(); i++) {
                pw.println("\t\tfieldValue.add(" + fieldEdit.get(i) + ");");
            }
            pw.println("\t\tArrayList<String> buttonValue = new ArrayList<String>();");
            for(i = 0; i < buttonValue.size(); i++) {
                pw.println("\t\tbuttonValue.add(" + buttonEdit.get(i) + ");");
            }
            pw.println("\t\tArrayList<String> fieldType = new ArrayList<String>();");
            for(i = 0; i < fieldTypes.size(); i++) {
                pw.println("\t\tfieldType.add(\"" + fieldTypes.get(i) + "\");");
            }
            pw.println("\t\tArrayList<String> buttonType = new ArrayList<String>();");
            for(i = 0; i < buttonTypes.size(); i++) {
                pw.println("\t\tbuttonType.add(\"" + buttonTypes.get(i) + "\");");
            }
            pw.println("\t\t"+ fileName +" gui = new " + fileName +"(\""+ guiTitle + "\", fieldValue, buttonValue,  fieldType, buttonType);");
            pw.println("\t}");
            pw.println("");
            for(i = 0; i < fieldValue.size(); i++) {
                pw.println("\tpublic String getDC" + fieldValue.get(i) + "() {");
                pw.println("\t\treturn fieldArray.get("+ i +").getText();");
                pw.println("\t}");
                pw.println("\tpublic void setDC" + fieldValue.get(i) + "(String info) {");
                pw.println("\t\tfieldArray.get("+ i +").setText(info);");
                pw.println("\t}");
            }
            pw.println("\tpublic void appendToStatusArea(String info) {");
            pw.println("\t\ttext.append(info);");
            pw.println("\t}");
            pw.println("}");
            pw.close();
        } catch(Exception e) {
            System.out.println("Error writing file.");
        }
		/*check = PM_destroy();
		if(check == 0) {
			System.out.println("Error");
		}*/
	}
}