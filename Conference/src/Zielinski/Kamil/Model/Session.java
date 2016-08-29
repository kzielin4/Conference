package Zielinski.Kamil.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Session
{

	public Session(int idSession, ArrayList<Lecture> lectures, Timestamp beginDate, Timestamp endDate, String sessionName)
	{
		super();
		this.idSession = idSession;
		this.lectures = lectures;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.sessionName = sessionName;
	}

	private int idSession;
	private ArrayList<Lecture> lectures;
	private Timestamp beginDate;
	private Timestamp endDate;
	private String sessionName;

	public Timestamp getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate)
	{
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
	}

	public void addLecture(Lecture lect)
	{
		this.lectures.add(lect);
	}

	public Lecture getLecture(int idx)
	{
		return this.lectures.get(idx);
	}

	public int getIdSession()
	{
		return idSession;
	}

	public void setIdSession(int idSession)
	{
		this.idSession = idSession;
	}

	public String getSessionName()
	{
		return sessionName;
	}

	public void setSessionName(String sessionName)
	{
		this.sessionName = sessionName;
	}

}
