package Zielinski.Kamil.Model;

import java.sql.Timestamp;

public class TimeUnit
{
	private Timestamp startTime;
	private int duration;
	private String timeUnitType;
	
	/*
	 * Tu mo¿na dodaæ pola dla zajêtego i wolnego miejsca i przydzielenie
	 * */
	public TimeUnit(Timestamp startTime, int duration, String timeUnitType)
	{
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.timeUnitType = timeUnitType;
	}

	public Timestamp getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Timestamp startTime)
	{
		this.startTime = startTime;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public String getTimeUnitType()
	{
		return timeUnitType;
	}

	public void setTimeUnitType(String timeUnitType)
	{
		this.timeUnitType = timeUnitType;
	}
}
