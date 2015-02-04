package dayplanner;
/** HomeActivity class
 *
 * @author Fei Song
 *
 * A class for representing and comparing home activities.
 *
 */

public class HomeActivity extends Activity {
	
	 /**
     * HomeActivity constructor that sets the values in Activity
     * @param aTitle
     * @param aStartingTime
     * @param anEndingTime
     * @param aComment 
     */
         public HomeActivity(String aTitle, Time aStartingTime, Time anEndingTime, String aComment) {
                super(aTitle, aStartingTime, anEndingTime, aComment, "home");
		if (valid(aStartingTime, anEndingTime)) {
			setTitle(aTitle);
			setStartingTime(aStartingTime);
			setEndingTime(anEndingTime);
			setComment(aComment);
		} else {			
			System.out.println("Invalid times for Activity");
			System.exit(0);
		}
	}
}
