package dayplanner;
import java.util.ArrayList;
/*import java.util.Scanner; */
import java.util.HashMap;
/*import java.util.NoSuchElementException;*/
import java.io.*;
import java.io.BufferedReader;
import java.util.Collections;

/** DayPlanner class
 *
 * @author Fei Song
 *
 * A class that adds and searches activities.
 *
 */

public class Dayplanner {

	public static final int MAX_SIZE = 15;        /* Max size of the Array List. No more than 15 activities can be added */

	public static final String[] types = new String[]{"home", "school", "other", "h", "s", "o"};
	/**
	 * Activity arraylist and Hashmap creation as well as starting the GUI
	 */
        private ArrayList<Activity> activities = new ArrayList();
        private HashMap<String, ArrayList<Integer>> keys = new HashMap<String, ArrayList<Integer>>(15);
	private int activitySize;
        public static DayplannerGUI gui = new DayplannerGUI();
        
        /*
         * Dayplanner constructor to initialize activitySize
         */
	public Dayplanner() {
                activitySize = 0;
	}

	/**
         * Put the words of the title into the HashMap
         */
        public void put() {
            String[] words;
            int i;
            words = activities.get(activities.size()-1).getTitle().split(" ");
            for(i = 0; i < words.length; i++) {
                if(keys.get(words[i].toLowerCase()) == null) {
                    ArrayList<Integer> index = new ArrayList();
                    index.add(activities.size()-1);
                    keys.put(words[i].toLowerCase(), index);
                }
                else {
                    ArrayList<Integer> index = keys.get(words[i].toLowerCase());
                    index.add(activities.size()-1);
                }
            }
            
        }
        /**
         * Create a time object for valid input
         * @param line
         * @return 
         */
	private Time getTime(String line) {
               	String[] tokens = line.split("[ ,\n]+");
               	if (tokens.length != 5)
			return null;
		for (int i = 0; i < 5; i++ )
		    if (!tokens[i].matches("[-+]?[0-9]+"))
			return null;
                int year = Integer.parseInt(tokens[0]);
                int month = Integer.parseInt(tokens[1]);
                int day = Integer.parseInt(tokens[2]);
                int hour = Integer.parseInt(tokens[3]);
                int minute = Integer.parseInt(tokens[4]);
		if (Time.timeOK(year, month, day, hour, minute))
			return new Time(year, month, day, hour, minute);
		else
			return null;
	}
	
        /**
         * Adds an activity to the ArrayList if it is not full
         * @param activity 
         */
        private void addActivityToList(Activity activity){
            if(activitySize == MAX_SIZE){
                System.out.println("Activity list is full.");
            }
            else {
                  activities.add(activity);
                  put();
                  activitySize++;
            }
        }
	/**
         * Create an activity from the input and then add it to the ArrayList.
         * Return true if the input was proper and the activity was added. Return
         * false if the input was invalid.
         * @param input 
         */
	public boolean addActivity(String type, String title, String startTime, String endTime, String location, String comment) {
		Time startingTime = null;
                Time endingTime = null;
                startingTime = getTime(startTime);
                endingTime = getTime(endTime);
                if(startingTime.compareTo(endingTime) == 1) {
                    return false;
                }
                
		if (type != null && type.equalsIgnoreCase("Home") && startingTime != null && endingTime != null) {      // Add to list but of correct class type
			addActivityToList(new HomeActivity(title, startingTime, endingTime, comment));
                        return true;
                }
		else if (type != null && type.equalsIgnoreCase("School") && startingTime != null && endingTime != null) {
			addActivityToList(new SchoolActivity(title, startingTime, endingTime, comment));
                        return true;
                }
                else if (type != null && type.equalsIgnoreCase("Other") && startingTime != null && endingTime != null) {
			addActivityToList(new OtherActivity(title, startingTime, endingTime, location, comment));
                        return true;
                }
                else {
                    return false;
                }
	}

	/**
         * Search for all activities that satisfy a search request using a hashmap
         * that only searches through the activities that contain the keywords to
         * make the search more efficient. Returns to searchActivity which returns
         * to the GUI.
         * @param startingTime
         * @param endingTime
         * @param keywords
         * @param type 
         */
	private ArrayList<String> searchActivities(Time startingTime, Time endingTime, String[] keywords, String type) {
		ArrayList<Integer> info = new ArrayList();        // Array list to store where keywords are found
                ArrayList<Integer> pairs = new ArrayList();       // store the matches between keywords that are found
                ArrayList<String> matches = new ArrayList();
                int i;
                int j;
                int check;
                String aKey;
                for(i = 0; i < keywords.length; i++) {
                    for(String name: keys.keySet()) {
                        aKey = name;
                        if(aKey.equalsIgnoreCase(keywords[i])) {
                            for(j = 0; j < keys.get(name).size(); j++) {
                                info.add(keys.get(name).get(j));
                            }
                        } 
                    }
                }
                Collections.sort(info);
                check = 0;
                for(i = 0; i < info.size(); i++) {
                    for(j = i; j < info.size(); j++) {
                        if(info.get(i) == info.get(j)) {
                            check++;
                            if(check == keywords.length) {
                                pairs.add(info.get(i));
                            }
                        }
                        else {
                           check = 0;
                        }
                    }
                }
                for (i = 0; i < pairs.size(); i++) {
                    if(activities.get(pairs.get(i)).getType().equalsIgnoreCase(type)){
			if ((startingTime == null || activities.get(pairs.get(i)).getStartingTime().compareTo(startingTime) >= 0) &&
			    (endingTime == null || activities.get(pairs.get(i)).getEndingTime().compareTo(endingTime) <= 0)) {
                                matches.add(activities.get(pairs.get(i)).toString());
                        }
                    }
                }
                return matches;
        }
	/**
         * Checks if GUI input is correct then passes correct information to be
         * searched for. Returns null if the input was not correct.
         * @param type
         * @param title
         * @param startTime
         * @param endTime
         * @return 
         */
	public ArrayList<String> searchActivity(String type, String title, String startTime, String endTime) {
                ArrayList<String> matches = new ArrayList();
                Time startingTime = null;
                Time endingTime = null;
                String[] keywords = null;
                startingTime = getTime(startTime);
		endingTime = getTime(endTime);
		if (!title.isEmpty()) {
                    keywords = title.split("[ ,\n]+");
                }
                if(type == null) {
                    matches = null;
                    return matches;
                }
                else if(type.equals("Home")) {
                    type = "home";
                }
                else if(type.equals("School")) {
                    type = "school";
                }
                else if(type.equals("Other")) {
                    type = "other";
                }
                if(startingTime == null || endingTime == null || title.equals("") || type == null) {
                    matches = null;
                    return matches;
                }
                else {
                    matches = searchActivities(startingTime, endingTime, keywords, type);
                    return matches;
                }
	}
	/**
         * Formats the information from the file properly and adds the activity to the ArrayList
         * @param type
         * @param title
         * @param start
         * @param end
         * @param comment 
         */
        public void fileActivityAdd(String type, String title, String start, String end, String comment){
            Time startingTime = null;
            Time endingTime = null;
            start = start.substring(9, start.length()-1);
            end = end.substring(7, end.length()-1);
            startingTime = getTime(start);
            endingTime = getTime(end);
            title = title.substring(9, title.length()-1);
            comment = comment.substring(11, comment.length()-1);
            if(type.charAt(8) == 's') {
                addActivityToList(new SchoolActivity(title, startingTime, endingTime, comment));
            }
            else if(type.charAt(8) == 'h') {
                addActivityToList(new HomeActivity(title, startingTime, endingTime, comment));
            }
        }
        /**
         * Formats the information from the file properly and adds the activity to the ArrayList but specific to OtheActivity
         * to add location and the type is not needed
         * @param location
         * @param title
         * @param start
         * @param end
         * @param comment 
         */
        public void fileOtherActivityAdd(String location, String title, String start, String end, String comment){
            Time startingTime = null;
            Time endingTime = null;
            start = start.substring(9, start.length()-1);
            end = end.substring(7, end.length()-1);
            startingTime = getTime(start);
            endingTime = getTime(end);
            title = title.substring(9, title.length()-1);
            comment = comment.substring(11, comment.length()-1);
            location = location.substring(12, location.length()-1);
            addActivityToList(new OtherActivity(title, startingTime, endingTime, location, comment));
        }
        /**
         * Returns the line count of the file
         * @return 
         */
        public int getLineCount(){
            int lines = 0;
            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader("file.txt"));
                
            } catch(FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(0);
            }
            try {
                while(reader.readLine() != null) {
                    lines++;
                }
                reader.close();
            } catch(IOException e) {
                System.exit(0);
            }
            return lines;
        }
        /**
         * Returns the line count of the file if the file is taken from the command line
         * so the command line argument must be passed
         * @param arg
         * @return 
         */
        public int getLineCount(String arg){
            int lines = 0;
            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader(arg));
                
            } catch(FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(0);
            }
            try {
                while(reader.readLine() != null) {
                    lines++;
                }
                reader.close();
            } catch(IOException e) {
                System.exit(0);
            }
            return lines;
        }
        /**
         * Main runs the program menu and takes the information from the file
         * as well as writing the new activities to the file when done
         * @param args 
         */
	public static void main(String[] args) {
                
                /*int i;
                int lineCount;
		Scanner input = new Scanner(System.in);
		Dayplanner planner = new Dayplanner();
                Scanner inputStream = null;
                if(args.length == 2) {
                    lineCount = planner.getLineCount(args[0]);
                }
                else {
                    lineCount = planner.getLineCount();
                }
                try {
                    if(args.length == 2) {
                        inputStream = new Scanner(new FileInputStream(args[0]));
                    }
                    else {
                        inputStream = new Scanner(new FileInputStream("file.txt"));   
                    } 
                } catch(FileNotFoundException e) {
                    System.out.println("Error opening the file.");
                    System.exit(0);
                }
                for(i = 0; i < lineCount; i++){   // amount of lines in file
                    String type = inputStream.nextLine();
                    String title = inputStream.nextLine();
                    String start = inputStream.nextLine();
                    String end = inputStream.nextLine();
                    String location = "";              // needed to initialize location even when not used
                    if(type.charAt(8) == 'o'){
                        location = inputStream.nextLine();
                    }
                    String comment = inputStream.nextLine();
                    if(type.charAt(8) == 'o'){
                        planner.fileOtherActivityAdd(location, title, start, end, comment);
                    }
                    else if(type.charAt(8) == 'h'){
                        planner.fileActivityAdd(type, title, start, end, comment);
                    }
                    else if(type.charAt(8) == 's'){
                        planner.fileActivityAdd(type, title, start, end, comment);
                    }
                    try {
                        String blank = inputStream.nextLine();
                    } catch(NoSuchElementException e) {
                        System.out.println("File done loading.");
                        break;
                    }
                }
                inputStream.close();
		String command = "";
		do {                          // The actual program menu that adds and searches
			System.out.print("Enter add, search, or quit> ");
			command = input.nextLine();
			if (command.equalsIgnoreCase("add") || command.equalsIgnoreCase("a"))
				planner.addActivity(input);
			else if (command.equalsIgnoreCase("search") || command.equalsIgnoreCase("s"))
				planner.searchActivity(input);			
		} while (!command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q"));
                int actQuit = planner.activities.size();          // Amount of activities after activities have been added
                if(args.length == 2) {      // If they gave an input file the output file will be args[1]
                    try {                                             // Write new activities to the file
                        PrintWriter outputStream = new PrintWriter(args[1], "UTF-8");
                        for(i = 0; i < actQuit; i++) {
                            if(planner.activities.get(i) instanceof OtherActivity) {
                                outputStream.println("type = \"" + "other" + "\"");
                            }
                            else if(planner.activities.get(i) instanceof HomeActivity) {
                                outputStream.println("type = \"" + "home" + "\"");
                            }
                            else if(planner.activities.get(i) instanceof SchoolActivity) {
                                outputStream.println("type = \"" + "school" + "\"");
                            }
                            outputStream.println("title = \"" + planner.activities.get(i).getTitle() + "\"");
                            outputStream.println("start = \"" + planner.activities.get(i).getStartingTime().getYear() + "," + planner.activities.get(i).getStartingTime().getMonth() + "," + planner.activities.get(i).getStartingTime().getDay() + "," + planner.activities.get(i).getStartingTime().getHour() + "," + planner.activities.get(i).getStartingTime().getMinute() + "\"");
                            outputStream.println("end = \"" + planner.activities.get(i).getEndingTime().getYear() + "," + planner.activities.get(i).getEndingTime().getMonth() + "," + planner.activities.get(i).getEndingTime().getDay() + "," + planner.activities.get(i).getEndingTime().getHour() + "," + planner.activities.get(i).getEndingTime().getMinute() + "\"");
                            if(planner.activities.get(i) instanceof OtherActivity) {
                                outputStream.println("location = \"" + planner.activities.get(i).getLocation() + "\"");
                            }
                            outputStream.println("comment = \"" + planner.activities.get(i).getComment() + "\"");
                            if(i != (actQuit-1)) {
                                outputStream.println("");
                            }
                        }
                        outputStream.close();
                    } catch(IOException e) {
                        System.out.println("Error opening the file.");
                        System.exit(0);
                    }
                }
                if(args.length == 1) {          // If they did not give an input file the output file will be args[0]
                    try {                                             // Write new activities to the file
                        PrintWriter outputStream = new PrintWriter(args[0], "UTF-8");
                        for(i = 0; i < actQuit; i++) {
                            if(planner.activities.get(i) instanceof OtherActivity) {
                                outputStream.println("type = \"" + "other" + "\"");
                            }
                            else if(planner.activities.get(i) instanceof HomeActivity) {
                                outputStream.println("type = \"" + "home" + "\"");
                            }
                            else if(planner.activities.get(i) instanceof SchoolActivity) {
                                outputStream.println("type = \"" + "school" + "\"");
                            }
                            outputStream.println("title = \"" + planner.activities.get(i).getTitle() + "\"");
                            outputStream.println("start = \"" + planner.activities.get(i).getStartingTime().getYear() + "," + planner.activities.get(i).getStartingTime().getMonth() + "," + planner.activities.get(i).getStartingTime().getDay() + "," + planner.activities.get(i).getStartingTime().getHour() + "," + planner.activities.get(i).getStartingTime().getMinute() + "\"");
                            outputStream.println("end = \"" + planner.activities.get(i).getEndingTime().getYear() + "," + planner.activities.get(i).getEndingTime().getMonth() + "," + planner.activities.get(i).getEndingTime().getDay() + "," + planner.activities.get(i).getEndingTime().getHour() + "," + planner.activities.get(i).getEndingTime().getMinute() + "\"");
                            if(planner.activities.get(i) instanceof OtherActivity) {
                                outputStream.println("location = \"" + planner.activities.get(i).getLocation() + "\"");
                            }
                            outputStream.println("comment = \"" + planner.activities.get(i).getComment() + "\"");
                            if(i != (actQuit-1)) {
                                outputStream.println("");
                            }
                        }
                        outputStream.close();
                    } catch(IOException e) {
                        System.out.println("Error opening the file.");
                        System.exit(0);
                    }
                }*/
	}
}
