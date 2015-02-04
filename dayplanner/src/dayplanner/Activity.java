/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dayplanner;

/**
 * Super class for the different types of activities
 * @author seeray
 */
public class Activity {
    private String title;        // short description for the activity
    private Time startingTime;   // starting time of the activity
    private Time endingTime;     // ending time of the activity
    private String comment;      // additional details about the activity
    private String location;     // location of the activity for other activities
    private String type;         // the type of the activity in string format
    
    /**
     * Activity constructor for home and school activities. Sets the values
     * @param title
     * @param startingTime
     * @param endingTime
     * @param comment
     * @param type 
     */
    public Activity(String title, Time startingTime, Time endingTime, String comment, String type) {
        this.title = title;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.comment = comment;
        this.type = type;
    }
    /**
     * Activity constructor for other activities. Sets the values
     * @param title
     * @param startingTime
     * @param endingTime
     * @param location
     * @param comment
     * @param type 
     */
    public Activity(String title, Time startingTime, Time endingTime, String location, String comment, String type) {
        this.title = title;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.location = location;
        this.comment = comment;
        this.type = type;
    }
    /**
     * Returns true if the startingTime and endingTime are not null
     * @param startingTime
     * @param endingTime
     * @return 
     */
    public static boolean valid(Time startingTime, Time endingTime) {
		return (startingTime != null) && (endingTime != null);
    }

	/**
     * Set value for title
     * @param title 
     */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
         * Set value for startingTime
         * @param startingTime 
         */
	public void setStartingTime(Time startingTime) {
		if (endingTime == null) {
			System.out.println("Invalid starting time");
			System.exit(0);  
		} else
		this.startingTime = startingTime; 
	}
	
	/**
         * Set value for endingTime
         * @param endingTime 
         */
	public void setEndingTime(Time endingTime) {
		if (startingTime == null) {
			System.out.println("Invalid ending time");
			System.exit(0);
		}
		this.endingTime = endingTime;
	}
	
	/**
         * Set value for comment
         * @param comment 
         */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
         * Get value of title
         * @return 
         */
	public String getTitle() {
		return title;
	}
	
	/**
         * Get value of startingTime
         * @return 
         */
	public Time getStartingTime() {
		return startingTime;
	}
	
	/**
         * Get value of endingTime
         * @return 
         */
	public Time getEndingTime() {
		return endingTime;
	}
	
	/**
         * Get value of comment
         * @return 
         */
	public String getComment() {
		return comment;
	}
        
	/**
         * Set location value
         * @param location 
         */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
         * Get value of location
         * @return 
         */
	public String getLocation() {
		return location;
	}
	
        /**
         * Get the type of the Activity subclass in String format
         * @return 
         */
	public String getType() {
		return type;
	}
        /**
         * Check the equality of two Activities
         * @param other
         * @return 
         */
	public boolean equals(Activity other) {
		if (other == null)
			return false;
		else 
			return title.equals(other.title) &&
			       startingTime.equals(other.startingTime) &&
			       endingTime.equals(other.endingTime) &&
			       comment.equals(other.comment);
	}
	/**
         * Display content of the activity in a string
         * @return 
         */
    @Override
	public String toString() {
		return title + ": " + startingTime + " to " + endingTime + ", " + comment;	
	}
}
