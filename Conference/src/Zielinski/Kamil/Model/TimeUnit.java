package Zielinski.Kamil.Model;

import java.sql.Timestamp;

public class TimeUnit
{
	private Timestamp startTime;
	private long duration;
	private boolean isFree;
	private int sessionId;
	private String unitName;
	private Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType unitType;
	/*public enum EventType
	{
		SESSION, PLENARY, OTHER, ERROR
	}*/
	/*
	 * Tu mo¿na dodaæ pola dla zajêtego i wolnego miejsca i przydzielenie
	 */
	public TimeUnit(Timestamp startTime, long duration, String timeUnitType,Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType plenary)
	{
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.unitName = timeUnitType;
		this.isFree = true;
		this.unitType=plenary;
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
		this.sessionId=idxSession;
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
}
