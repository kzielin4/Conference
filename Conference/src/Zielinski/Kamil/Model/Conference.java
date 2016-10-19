package Zielinski.Kamil.Model;

import java.util.ArrayList;

import Zielinski.Kamil.Model.Lecture.LectureType;

public class Conference
{

	private TimetableSkeleton timetableSkeleton;
	private ArrayList<Session> sessions;
	private ArrayList<Extract> extracts;
	private boolean isComplited;

	public TimetableSkeleton getTimetableSkeleton()
	{
		return timetableSkeleton;
	}

	public void setTimetableSkeleton(TimetableSkeleton timetableSkeleton)
	{
		this.timetableSkeleton = timetableSkeleton;
	}

	public ArrayList<Session> getSessions()
	{
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions)
	{
		this.sessions = sessions;
	}

	public ArrayList<Extract> getExtracts()
	{
		return extracts;
	}

	public void setExtracts(ArrayList<Extract> extracts)
	{
		this.extracts = extracts;
	}

	public Conference()
	{
		this.timetableSkeleton = new TimetableSkeleton();
		this.sessions = new ArrayList<Session>();
		this.extracts = new ArrayList<Extract>();
		this.isComplited = false;
	}

	public Conference(TimetableSkeleton timetableSkeleton, ArrayList<Session> sessions, ArrayList<Extract> extracts)
	{
		super();
		this.timetableSkeleton = timetableSkeleton;
		this.sessions = sessions;
		this.extracts = extracts;
		this.isComplited = false;
	}

	public double rateConference()
	{
		return 0;
	}

	public boolean isComplited()
	{
		return isComplited;
	}

	public void setComplited(boolean isComplited)
	{
		this.isComplited = isComplited;
	}
	
	public int countNormalLecture()
	{
		int sum=0;
		if(extracts.size()==0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType()==LectureType.N)
				{
					sum=sum+1;
				}
			}
		}
		return sum;
	}
	public int countPlenaryLecture()
	{
		int sum=0;
		if(extracts.size()==0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType()==LectureType.P)
				{
					sum=sum+1;
				}
			}
		}
		return sum;
	}

}
