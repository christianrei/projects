%{
    #include "data.h"
    void yyerror(char *s);
    extern int yylex();
    extern int yylineno;
    extern char* yytext;
    extern FILE *yyin;
    node * titleList = NULL;
    node * fieldList = NULL;
    node * buttonList = NULL;
    int type = 0;
    int error = 0;
    int location;
    int buttonCount = 0;
    int fieldCount = 0;
    int countCheck = 0;
    int countBCheck = 0;
    %}

%union  {int num; char * id;}
%start  PART
%token  FIELDS
%token  TITLE
%token  BUTTONS
%token  EQUAL
%token  QUOTE
%token  LISTL
%token  LISTR
%token  COMMA
%token  SEMI
%token <id> STRING
%type <id> PART LISTS ITEM MULTISTRING

%%

PART    :   TITLE EQUAL QUOTE MULTISTRING QUOTE SEMI  {type = 1;}
        |   PART TITLE EQUAL QUOTE MULTISTRING QUOTE SEMI  {type = 1;}
|   PART FIELDS EQUAL LISTL LISTS LISTR SEMI {type = 2;}
|   PART BUTTONS EQUAL LISTL LISTS LISTR SEMI {type = 3;}
|   PART CHECK EQUAL QUOTE MULTISTRING QUOTE SEMI {countCheck++;
    if(countCheck==fieldCount)type=4;}
;

LISTS   :   ITEM  {}
|   ITEM COMMA        {}
|   LISTS ITEM      {}
|   LISTS ITEM COMMA    {}
;

ITEM    :   QUOTE MULTISTRING QUOTE  {}
;

CHECK   :   STRING  {if(type == 3)
    {
        location = 0;
        location = findListName(fieldList, strdup(yytext));
        if(location == 0) {
            error = 1;
        }
    }
    else if(type == 4) {
        if(countBCheck == buttonCount) {
            type = 5;
        }
        else {
            location = 0;
            location = findListName(buttonList, strdup(yytext));
        }
        countBCheck++;
        if(location == 0) {
            error = 1;
        }
    }}
;

MULTISTRING :   STRING              {if(type==0)titleList->name = strdup(yytext);
    else if(type==1) {
        addToBack(fieldList,strdup(yytext));
        fieldCount++;
    }
    else if(type==2) {
        addToBack(buttonList,strdup(yytext));
        buttonCount++;
    }
    else if(type==3) {
        if(error==0)addToIndex(fieldList,location, strdup(yytext));
        else {
            printf("Name not found in list.\n");
        }
    }
    else if(type==4) {
        if(error==0)addToIndex(buttonList,location, strdup(yytext));
        else {
            printf("Name not found in list.\n");
        }
    }}
|   MULTISTRING STRING  {if(type==0)addToBack(titleList,strdup(yytext));
    else if(type==1) {
        addToBack(fieldList,strdup(yytext));
        fieldCount++;
    }
    else if(type==2) {
        addToBack(buttonList,strdup(yytext));
        buttonCount++;
    }}
;


%%

int main(int argc, char * argv[]) {
    node * temp;
    FILE * fp;
    FILE * fpi;
    FILE * parseMe;
    int i;
    char fileName[25];
    char fileExt[30];
    char * initialize;
    char guiFile[56];
    char guiName[50];
    char interfaceName[70];
    fieldList = malloc(sizeof(node));
    buttonList = malloc(sizeof(node));
    titleList = malloc(sizeof(node));
    titleList->name = malloc(sizeof(char)*25);
    titleList->val = malloc(sizeof(char)*25);
    titleList->next = NULL;
    fieldList->name = malloc(sizeof(char)*25);
    fieldList->val = malloc(sizeof(char)*25);
    fieldList->next = NULL;
    buttonList->name = malloc(sizeof(char)*25);
    buttonList->val = malloc(sizeof(char)*25);
    buttonList->next = NULL;
    initialize = "";
    strcpy(fileExt, initialize);
    strcpy(fileName, initialize);
    strcpy(fileName, argv[1]);
    strcpy(fileExt, fileName);
    strcat(fileExt, ".config");
    parseMe = fopen(fileExt, "r");
    if(!parseMe) {
        printf("File could not be opened.\n");
        return 0;
    }
    yyin = parseMe;
    do {
        yyparse();
    } while(!feof(yyin));
    printList(fieldList);
    printList(buttonList);
    temp = titleList;
    strcpy(guiName, initialize);
    strcpy(guiFile, initialize);
    strcpy(interfaceName, initialize);
    while(temp != NULL) {
        strcat(guiName, temp->name);
        if(temp->next != NULL) {
            strcat(guiName, " ");
        }
        temp = temp->next;
    }
    strcat(interfaceName, fileName);
    strcat(interfaceName, "FieldEdit.java");
    temp = fieldList;
    if(temp->next == NULL) {
        error = 1;
    }
    temp = buttonList;
    if(temp->next == NULL) {
        error = 1;
    }
    if(error == 0) {
        fpi = fopen(interfaceName, "w");
        fprintf(fpi, "public interface %sFieldEdit {\n", fileName);
        temp = fieldList;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fpi, "\tpublic String getDC%s();\n", temp->name);
            temp = temp->next;
        }
        temp = fieldList;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fpi, "\tpublic void setDC%s(String info);\n", temp->name);
            temp = temp->next;
        }
        fprintf(fpi, "}\n");
        fclose(fpi);
        strcat(guiFile, fileName);
        strcat(guiFile, ".java");
        fp = fopen(guiFile, "w");
        if(fp == NULL) {
            printf("Error writing file\n");
            return 1;
        }
        fprintf(fp, "import javax.swing.*;\n");
        fprintf(fp, "import java.awt.*;\n");
        fprintf(fp, "import java.awt.event.*;\n");
        fprintf(fp, "import java.io.BufferedReader;\n");
        fprintf(fp, "import javax.swing.border.BevelBorder;\n");
        fprintf(fp, "import javax.swing.event.*;\n");
        fprintf(fp, "import java.util.ArrayList;\n");
        fprintf(fp, "\n");
        fprintf(fp, "/**\n");
        fprintf(fp, "*\n");
        fprintf(fp, "* @author crei\n");
        fprintf(fp, "*\n");
        fprintf(fp, "*\n");
        fprintf(fp, "*\n");
        fprintf(fp, "/**\n");
        fprintf(fp, "*\n");
        fprintf(fp, "* The template for what will be a GUI that gets generated \n");
        fprintf(fp, "*/\n");
        fprintf(fp, "public class %s extends JFrame implements %sFieldEdit {\n", fileName, fileName);
        fprintf(fp, "\tprivate JFrame frame = new JFrame();\n");
        fprintf(fp, "\tprivate JPanel mainPanel = new JPanel();\n");
        fprintf(fp, "\tprivate JPanel upperPanel = new JPanel();\n");
        fprintf(fp, "\tprivate JPanel centrePanel = new JPanel();\n");
        fprintf(fp, "\tprivate JTextArea text = new JTextArea(16, 20);\n");
        fprintf(fp, "\tprivate ArrayList<JTextField> fieldArray = new ArrayList<JTextField>();\n");
        fprintf(fp, "\tprivate int i = 0;\n");
        fprintf(fp, "\t/* Takes the title that is parsed to be the name of the frame as well as ArrayLists for what goes on\n");
        fprintf(fp, "\tthe GUI */\n");
        fprintf(fp, "\tpublic %s (String title, ArrayList<String> fieldValue, ArrayList<String> buttonValue, ArrayList<String> fieldTypes, ArrayList<String> buttonTypes) {\n", fileName);
        fprintf(fp, "\t\tsuper(title);\n");
        fprintf(fp, "\t\tsetSize(500, 600);\n");
        fprintf(fp, "\t\tsetDefaultCloseOperation(EXIT_ON_CLOSE);\n");
        fprintf(fp, "\t\tmainPanel.setLayout(new BorderLayout());\n");
        fprintf(fp, "\t\tfor(i = 0; i < fieldValue.size(); i++) {\n");
        fprintf(fp, "\t\t\tJLabel name = new JLabel(fieldValue.get(i));\n");
        fprintf(fp, "\t\t\tupperPanel.setLayout(new GridLayout(8,2));\n");
        fprintf(fp, "\t\t\tupperPanel.add(name);\n");
        fprintf(fp, "\t\t\tJTextField field = new JTextField();\n");
        fprintf(fp, "\t\t\tfield.setColumns(30);\n");
        fprintf(fp, "\t\t\tupperPanel.add(field);\n");
        fprintf(fp, "\t\t\tfieldArray.add(field);\n");
        fprintf(fp, "\t\t}\n");
        fprintf(fp, "\t\tJButton[] button = new JButton[buttonValue.size()];\n");
        fprintf(fp, "\t\tcentrePanel.setLayout(new FlowLayout(FlowLayout.LEFT));\n");
        temp = buttonList;
        i = 0;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fp, "\t\tbutton[%d] = new JButton(buttonValue.get(%d));\n", i, i);
            fprintf(fp, "\t\tbutton[%d].addActionListener(new %s());\n", i,temp->val);
            fprintf(fp, "\t\tcentrePanel.add(button[%d]);\n", i);
            i++;
            temp = temp->next;
        }
        fprintf(fp, "\t\tJScrollPane scrolledText = new JScrollPane(text);\n");
        fprintf(fp, "\t\ttext.setEditable(false);\n");
        fprintf(fp, "\t\ttext.setText(\"\");\n");
        fprintf(fp, "\t\tscrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);\n");
        fprintf(fp, "\t\tscrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\" \"));\n");
        fprintf(fp, "\t\tcentrePanel.add(new JLabel(\"Status\"));\n");
        fprintf(fp, "\t\tmainPanel.add(scrolledText, BorderLayout.SOUTH);\n");
        fprintf(fp, "\t\tmainPanel.add(centrePanel, BorderLayout.CENTER);\n");
        fprintf(fp, "\t\tmainPanel.add(upperPanel, BorderLayout.NORTH);\n");
        fprintf(fp, "\t\tadd(mainPanel);\n");
        fprintf(fp, "\t\tsetVisible(true);\n");
        fprintf(fp, "\t}\n");
        fprintf(fp, "\tpublic static void main(String[] args) {\n");
        fprintf(fp, "\t\tArrayList<String> fieldValue = new ArrayList<String>();\n");
        fprintf(fp, "\t\tArrayList<String> fieldType = new ArrayList<String>();\n");
        fprintf(fp, "\t\tArrayList<String> buttonValue = new ArrayList<String>();\n");
        fprintf(fp, "\t\tArrayList<String> buttonType = new ArrayList<String>();\n");
        temp = fieldList;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fp, "\t\tfieldValue.add(\"%s\");\n", temp->name);
            fprintf(fp, "\t\tfieldType.add(\"%s\");\n", temp->val);
            temp = temp->next;
        }
        temp = buttonList;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fp, "\t\tbuttonValue.add(\"%s\");\n", temp->name);
            fprintf(fp, "\t\tbuttonType.add(\"%s\");\n", temp->val);
            temp = temp->next;
        }
        fprintf(fp, "\t\t%s gui = new %s(\"%s\", fieldValue, buttonValue,  fieldType, buttonType);", fileName, fileName, guiName);
        fprintf(fp, "\t}\n");
        fprintf(fp, "\n");
        temp = fieldList;
        i = 0;
        temp = temp->next;
        while(temp != NULL) {
            fprintf(fp, "\tpublic String getDC%s {\n", temp->name);
            fprintf(fp, "\t\treturn fieldArray.get(%d).getText()\n", i);
            fprintf(fp, "\t}\n");
            fprintf(fp, "\tpublic void setDC%s(String info) {\n", temp->name);
            fprintf(fp, "\t\tfieldArray.get(%d).setText()\n", i);
            fprintf(fp, "\t}\n");
            i++;
            temp = temp->next;
        }
        fprintf(fp, "\tpublic void appendToStatusArea(String info) {\n");
        fprintf(fp, "\t\ttext.append(info);\n");
        fprintf(fp, "\t}\n");
        fprintf(fp, "}\n");
        fclose(fp);
    }
    else {
        printf("Parse Error\n\n");
    }
    destroy(titleList);
    destroy(fieldList);
    destroy(buttonList);
    return 0;
}

void yyerror(char *s) {
    fprintf(stderr, "%s\n", yytext);
    printf("%d\n", yylineno);
}