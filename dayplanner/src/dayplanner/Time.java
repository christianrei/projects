package dayplanner;
/** Time class 
 * 
 * @author Fei Song
 *
 * A class for representing and comparing different times.
 *
 */
public class Time {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	/**
         * Create a time object with all the required elements
         * @param year
         * @param month
         * @param day
         * @param hour
         * @param minute 
         */
	public Time(int year, int month, int day, int hour, int minute) {
		if (timeOK(year, month, day, hour, minute)) {
			this.year = year;
			this.month = month;
			this.day = day;
			this.hour = hour;
			this.minute = minute;
		} else {
			System.out.println("Fatal Error for Time");
			System.exit(0);
		}
	}
	
	/**
         * Time constructor that initializes the values
         */
	public Time() {
		year = 0;
		month = 0;
		day = 0;
		hour = 0;
		minute = 0;
	}
	
	/**
         * Set the value for year
         * @param year 
         */
	public void setYear(int year) {
		if (year >= 0)
			this.year = year;
		else {
			System.out.println("Invalid value for year: " + year);
			System.exit(0);
		}
	}
	
	/**
         * Set the value for month
         * @param month 
         */
	public void setMonth(int month)	{
		if (month >= 0)
			this.month = month;
		else {
			System.out.println("Invalid value for month: " + month);
			System.exit(0);
		}
	}
	
	/**
         * Set the value for day
         * @param day 
         */
	public void setDay(int day) {
		if (day >= 0) 
			this.day = day;
		else {
			System.out.println("Invalid value for day: " + day);
			System.exit(0);
		}
	}
	
	/**
         * Set the value for hour
         * @param hour 
         */
	public void setHour(int hour) {
		if (hour >= 0)
			this.hour = hour;
		else {
			System.out.println("Invalid value for hour: " + hour);
			System.exit(0);
		}
	}
	
	/**
         * Set the value for minute
         * @param minute 
         */
	public void setMinute(int minute) {
		if (minute >= 0)
			this.minute = minute;
		else {
			System.out.println("Invalid value for minute: " + minute);
			System.exit(0);
		}
	}
	
	/**
         * Check for equality with a given time object
         * @param other
         * @return 
         */
	public boolean equals(Time other) {
		if (other == null)
			return false;
		else 
			return year == other.year &&
				month == other.month &&
				day == other.day &&
				hour == other.hour &&
				minute == other.minute;
	}
	
	/**
         * Compare two time objects for ordering
         * @param other
         * @return 
         */
	public int compareTo(Time other) {
		if (year < other.year)
			return -1;
		else if (year > other.year)
			return 1;
		else if (month < other.month)
			return -1;
		else if (month > other.month)
			return 1;
		else if (day < other.day)
			return -1;
		else if (day > other.day)
			return 1;
		else if (hour < other.hour)
			return -1;
		else if (hour > other.hour)
			return 1;
		else if (minute < other.minute)
			return -1;
		else if (minute > other.minute)
			return 1;
		else
			return 0;
	}
	
	/**
         * Get the year value
         * @return 
         */
	public int getYear() {
		return year;
	}
	
	/**
         * Get the month value
         * @return 
         */
	public int getMonth() {
		return month;
	}
	
	/** 
         * Get the day value
         * @return 
         */
	public int getDay() {
		return day;
	}
	
	/**
         * Get the hour value
         * @return 
         */
	public int getHour() {
		return hour;
	}
	
	/**
         * Get the value minute
         * @return 
         */
	public int getMinute() {
		return minute;
	}
	
	/**
         * Return the value of Time in String form
         * @return 
         */
        @Override
	public String toString() {
		return hour + ":" + minute + ", " + month + "/" + day + "/" + year;
	} 
	
	/**
         * Checking to see if the given time is valid
         * @param year
         * @param month
         * @param day
         * @param hour
         * @param minute
         * @return 
         */
	static public boolean timeOK(int year, int month, int day, int hour, int minute) {
                if(day > 29 && month == 2) {
                    return false;
                }
		return (year >= 0) && (month >= 0) && (month <= 12) && (day >= 0) && (day <= 31) &&
		       (hour >= 0) && (hour <= 23) && (minute >= 0) && (minute <= 59);
	}
}
