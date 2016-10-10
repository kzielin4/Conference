package Zielinski.Kamil.Model;

import java.util.ArrayList;

public class TimetableSkeleton
{
	// list of time blocks
	private ArrayList<TimeUnit> timeUnits;
    /**
     * Main constructor
     */
	public TimetableSkeleton()
	{
		timeUnits = new ArrayList<TimeUnit>();
	}

	/**
	 * Function witch returns timeUnit with given index
	 * 
	 * @param idx
	 * @return timeUnit witk given index
	 */
	public TimeUnit getTimeUnit(int idx)
	{
		return timeUnits.get(idx);
	}

	/**
	 * Function add timeUnit to timeUnits list
	 * 
	 * @param unit
	 */
	public void addTimeUnit(TimeUnit unit)
	{
		timeUnits.add(unit);
	}

	/**
	 * Function assign session to a block of time
	 * 
	 * @param idxUnit
	 * @param idxSession
	 */
	public void setSession(int idxUnit, int idxSession)
	{
		timeUnits.get(idxUnit).setSession(idxSession);
	}
}
