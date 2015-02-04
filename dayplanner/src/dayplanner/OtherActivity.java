package dayplanner;
/** OtherActivity class
 *
 * @author Fei Song
 *
 * A class for representing and comparing other activities.
 *
 */

public class OtherActivity extends Activity {
	/**
     * OtherActivity constructor that sets the values in Activity
     * @param title
     * @param startingTime
     * @param endingTime
     * @param location
     * @param comment 
     */
	public OtherActivity(String title, Time startingTime, Time endingTime, String location, String comment) {
                super(title, startingTime, endingTime, location, comment, "other");
		if (valid(startingTime, endingTime)) {
			setTitle(title);
			setStartingTime(startingTime);
			setEndingTime(endingTime);
			setLocation(location);
			setComment(comment);
		} else {			
			System.out.println("Invalid times for OtherActivity");
			System.exit(0);
		}
	}
	
	/**
         * Overwritten toString method for OtherActivity due to location making
         * it different from the toString method in Activity
         * @return 
         */
        @Override
	public String toString() {
                String aTitle = getTitle();
		Time aStartingTime = getStartingTime();
                Time anEndingTime = getEndingTime();
                String aLocation = getLocation();
                String aComment = getComment();
		return aTitle + ": " + aStartingTime + " to " + anEndingTime + ", " + aLocation + ", " + aComment;	
	}
}
