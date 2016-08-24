package Zielinski.Kamil.Model;

import java.sql.Date;
import java.util.ArrayList;

public class Session
{
	private int idSession;
	private ArrayList<Lecture> lectures;
	private Date beginDate;
	private Date endDate;

	public Date getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
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

}
