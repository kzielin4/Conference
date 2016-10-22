package Zielinski.Kamil.Model;

import java.util.ArrayList;

import Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType;

public class TimetableSkeleton
{
	public TimetableSkeleton(TimetableSkeleton skeleton)
	{
		super();
		this.timeUnits = skeleton.getTimeUnits();
	}

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

	public int countSessionUnits()
	{
		int sum = 0;
		if (timeUnits.size() == 0)
		{
			return 0;
		}
		else
		{
			for (TimeUnit timeUnit : timeUnits)
			{
				if (timeUnit.getUnitType() == EventType.SESSION)
				{
					sum += 1;
				}
			}
		}
		return sum;
	}

	public int countPlenaryUnits()
	{
		int sum = 0;
		if (timeUnits.size() == 0)
		{
			return 0;
		}
		else
		{
			for (TimeUnit timeUnit : timeUnits)
			{
				if (timeUnit.getUnitType() == EventType.PLENARY)
				{
					sum += 1;
				}
			}
		}
		return sum;
	}

	public ArrayList<TimeUnit> getTimeUnits()
	{
		return timeUnits;
	}

	public void setTimeUnits(ArrayList<TimeUnit> timeUnits)
	{
		this.timeUnits = timeUnits;
	}
	public int countMaxNormalLectureInUnits()
	{
		int sum = 0;
		if (timeUnits.size() == 0)
		{
			return 0;
		}
		else
		{
			for (TimeUnit timeUnit : timeUnits)
			{
				if (timeUnit.getUnitType() == EventType.SESSION)
				{
					sum = sum + timeUnit.getMaxLectureInUnit();
				}
			}
		}
		return sum;
	}
	public int countMaxPlenaryLectureInUnits()
	{
		int sum = 0;
		if (timeUnits.size() == 0)
		{
			return 0;
		}
		else
		{
			for (TimeUnit timeUnit : timeUnits)
			{
				if (timeUnit.getUnitType() == EventType.SESSION)
				{
					sum = sum + timeUnit.getMaxLectureInUnit();
				}
			}
		}
		return sum;
	}
}
