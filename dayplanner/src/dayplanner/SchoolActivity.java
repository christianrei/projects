package dayplanner;
/** SchoolActivity class
 *
 * @author Fei Song
 *
 * A class for representing and comparing school activities.
 *
 */

public class SchoolActivity extends Activity {
	/**
     * SchoolActivity constructor that sets the values in Activity
     * @param aTitle
     * @param aStartingTime
     * @param anEndingTime
     * @param aComment 
     */
	public SchoolActivity(String aTitle, Time aStartingTime, Time anEndingTime, String aComment) {
                super(aTitle, aStartingTime, anEndingTime, aComment, "school");
		if (valid(aStartingTime, anEndingTime)) {
			setTitle(aTitle);
			setStartingTime(aStartingTime);
			setEndingTime(anEndingTime);
			setComment(aComment);
		} else {			
			System.out.println("Invalid times for SchoolActivity");
			System.exit(0);
		}
	}
}
