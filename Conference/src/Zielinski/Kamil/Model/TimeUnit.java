package Zielinski.Kamil.Model;

import java.sql.Timestamp;

import Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType;

public class TimeUnit
{
	private Timestamp startTime;
	private long duration;
	private boolean isFree;
	private int sessionId;
	private Settings settings;
	private String unitName;
	private int maxLectureInUnit;
	private Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType unitType;

	/*
	 * public enum EventType { SESSION, PLENARY, OTHER, ERROR }
	 */
	/*
	 * Tu mo¿na dodaæ pola dla zajêtego i wolnego miejsca i przydzielenie
	 */
	public TimeUnit(Timestamp startTime, long duration, String timeUnitType,
			Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType type)
	{
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.unitName = timeUnitType;
		this.isFree = true;
		this.unitType = type;
		this.settings = new Settings();
		setMaxLectureInUnit();
	}

	public int setMaxLectureInUnit()
    {
    	if(unitType==EventType.PLENARY)
    	{
    		if(getMinuteDuration()<settings.getMinutePerPlenaryLecture())
    		{
    			return 0;
    		}
    		else
    		{
    			maxLectureInUnit=getMinuteDuration()/settings.getMinutePerPlenaryLecture();
    			return maxLectureInUnit;
    		}
    	}
    	else if(unitType==EventType.SESSION)
		{
    		if(getMinuteDuration()<settings.getMinutePerLecture())
    		{
    			return 0;
    		}
    		else
    		{
    			maxLectureInUnit=getMinuteDuration()/settings.getMinutePerLecture();
    			return maxLectureInUnit;
    		}
		}
    	else
    	{
    		maxLectureInUnit=-1;
    		return -1;
    	}
    }

	public Timestamp getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Timestamp startTime)
	{
		this.startTime = startTime;
	}

	public long getDuration()
	{
		return duration;
	}

	public int getMinuteDuration()
	{
		return (int) (duration / 60000);
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public String getUnitName()
	{
		return unitName;
	}

	public void setUnitName(String timeUnitType)
	{
		this.unitName = timeUnitType;
	}

	public void setSession(int idxSession)
	{
		this.sessionId = idxSession;
	}

	public boolean isFree()
	{
		return isFree;
	}

	public void setFree(boolean isFree)
	{
		this.isFree = isFree;
	}

	public int getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(int sessionId)
	{
		this.sessionId = sessionId;
	}

	public Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType getUnitType()
	{
		return unitType;
	}

	public void setUnitType(Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType unitType)
	{
		this.unitType = unitType;
	}

	public int getMaxLectureInUnit()
	{
		return maxLectureInUnit;
	}

	
}
