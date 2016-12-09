package Zielinski.Kamil.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Session
{
	public Session(int idSession, Timestamp beginDate,Timestamp endDate, int maxAmmountLectureInSession,
			SessionType type, String name)
	{
		super();
		this.idSession = idSession;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.maxAmmountLectureInSession = maxAmmountLectureInSession;
		this.idLectures = new ArrayList<Integer>();
		this.type = type;
		this.sessionName=name;
	}
	
	public Session(int idSession, Timestamp beginDate,Timestamp endDate,SessionType type, String name)
	{
		super();
		this.idSession = idSession;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.idLectures = new ArrayList<Integer>();
		this.type = type;
		this.sessionName=name;
	}


	private int idSession;
	private ArrayList<Integer> idLectures;
	private Timestamp beginDate;
	private Timestamp endDate;
	private String sessionName;
	private SessionType type;
	private int maxAmmountLectureInSession;

	public enum SessionType
	{
		SESSION, PLENARY
	};

	public Timestamp getBeginDate()
	{
		return beginDate;
	}

	public void addIdLectures(Integer id)
	{
		idLectures.add(id);
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

	public int getMaxAmmountLectureInSession()
	{
		return maxAmmountLectureInSession;
	}

	public void setMaxAmmountLectureInSession(int maxAmmountLectureInSession)
	{
		this.maxAmmountLectureInSession = maxAmmountLectureInSession;
	}

	public int ammountOfAssignedLectures()
	{
		return idLectures.size();
	}

	public ArrayList<Integer> getIdLectures()
	{
		return idLectures;
	}

	public void setIdLectures(ArrayList<Integer> idLectures)
	{
		this.idLectures = idLectures;
	}
    public void printSession()
    {
    	int i=0;
    	System.out.println("-------");
    	for (Integer integer : idLectures)
		{
			System.out.println(integer);
		}
    }

	public SessionType getType()
	{
		return type;
	}

	public void setType(SessionType type)
	{
		this.type = type;
	}
	
	public void clearIdLectures()
	{
		idLectures.clear();
	}
  
}
