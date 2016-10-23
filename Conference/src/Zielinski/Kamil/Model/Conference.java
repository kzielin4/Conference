package Zielinski.Kamil.Model;

import java.sql.Timestamp;
import java.util.ArrayList;

import Zielinski.Kamil.Model.Lecture.LectureType;
import Zielinski.Kamil.Model.Session.SessionType;
import Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType;

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
		int sum = 0;
		if (extracts.size() == 0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType() == LectureType.N)
				{
					sum = sum + 1;
				}
			}
		}
		return sum;
	}

	public int countPlenaryLecture()
	{
		int sum = 0;
		if (extracts.size() == 0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType() == LectureType.P)
				{
					sum = sum + 1;
				}
			}
		}
		return sum;
	}

	public void initNormalSessions()
	{
		for (TimeUnit unit : timetableSkeleton.getTimeUnits())
		{
			if (unit.getUnitType() == EventType.SESSION)
			{
				sessions.add(new Session(sessions.size() - 1, unit.getStartTime(),unit.getEndTime(), unit.getMaxLectureInUnit(),
						SessionType.SESSION));
			}
		}
	}

	public int sessionSize()
	{
		return sessions.size();
	}

	public long fitFunction()
	{
		long fit = 0;
		return fit;
	}

	public ArrayList<Integer> getExtractIdList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Extract extract : extracts)
		{
			list.add(new Integer(extract.getIdExtract()));
		}
		return list;
	}

	public ArrayList<Boolean> getFalseList()
	{
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (Extract extract : extracts)
		{
			list.add(new Boolean(false));
		}
		return list;
	}
	public void clearId()
	{
		for (Session session : sessions)
		{
			session.getIdLectures().clear();
		}
	}
	public Timestamp getArrivalSpeakerTime(int idx)
	{
		return extracts.get(idx).getSpeaker().getArrivalDate();
	}
	public Timestamp getDepartureSpeakerTime(int idx)
	{
		return extracts.get(idx).getSpeaker().getDepartureDate();
	}
}
