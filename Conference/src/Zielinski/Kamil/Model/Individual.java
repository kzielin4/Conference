package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Random;

public class Individual
{
	private ArrayList<Integer> idExtracts;
	private ArrayList<Boolean> isAssigned;
	private ArrayList<Session> sessions;
	private ArrayList<Integer> sessionToExtractAssigned;
	private ArrayList<Extract> extracts;

	public Individual(final ArrayList<Integer> idExtracts, final ArrayList<Session> sessions,
			final ArrayList<Extract> extracts, ArrayList<Integer> sessionToAssigned)
	{

		this.idExtracts = new ArrayList<Integer>(idExtracts);
		// this.isAssigned = new ArrayList<Boolean>(isAssigned);
		this.sessions = new ArrayList<Session>();
		this.sessionToExtractAssigned = new ArrayList<Integer>();
		this.extracts = new ArrayList<Extract>(extracts);
		// this.sessions=(ArrayList<Session>) sessions.clone();
		for (Session session : sessions)
		{
			this.sessions.add(new Session(session.getIdSession(), session.getBeginDate(), session.getEndDate(),
					session.getMaxAmmountLectureInSession(), session.getType()));
		}
		this.sessionToExtractAssigned = new ArrayList<Integer>();
		if (sessionToAssigned != null)
		{
			for (Integer integer : sessionToAssigned)
			{
				sessionToExtractAssigned.add(integer);
			}
		}
	}

	public ArrayList<Integer> getIdExtracts()
	{
		return idExtracts;
	}

	public void setIdExtracts(ArrayList<Integer> idExtracts)
	{
		this.idExtracts = idExtracts;
	}

	public ArrayList<Boolean> getIsAssigned()
	{
		return isAssigned;
	}

	public void setIsAssigned(ArrayList<Boolean> isAssigned)
	{
		this.isAssigned = isAssigned;
	}

	public ArrayList<Session> getSessions()
	{
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions)
	{
		this.sessions = sessions;
	}

	public void init()
	{
		int i = 0;
		
		for (Integer integer : idExtracts)
		{
			boolean isSessionOk = false;

			Random rand = new Random();
			while (!isSessionOk)
			{
				int poz = rand.nextInt((sessions.size() - 1 - 0) + 1) + 0;
				if (sessions.get(poz).ammountOfAssignedLectures() <= sessions.get(poz).getMaxAmmountLectureInSession())
				{
					isSessionOk = true;
					sessions.get(poz).addIdLectures(new Integer(i));
					sessionToExtractAssigned.add(new Integer(poz));
				}
			}
			++i;
		}
	}

	public void printObject()
	{
		int currentPosition = 1;
		/*
		 * for (Session ses : sessions) { System.out.println("Sesja " +
		 * currentPosition); for (Integer integer : ses.getIdLectures()) {
		 * System.out.println("W: " + integer); } ++currentPosition; }
		 */
		currentPosition = 1;
		for (Session ses : sessions)
		{
			for (Integer integer : sessionToExtractAssigned)
			{
				System.out.print(" " + integer);
			}
			++currentPosition;
		}
	}

	public long fitValue()
	{
		long fit = 100 * idExtracts.size();
		ArrayList<Integer> countLectureInSession = new ArrayList<Integer>();
		for(int j=0;j<sessions.size();++j)
		{
			countLectureInSession.add(0);
		}
		int i = 0;
		for (i = 0; i < idExtracts.size(); ++i)
		{
			for (Extract extract : extracts)
			{
				if (idExtracts.get(i).equals(new Integer(extract.getIdExtract())))
				{
					// System.out.println(sessions.get(sessionToExtractAssigned.get(i)).getEndDate());
					if (!(sessions.get(sessionToExtractAssigned.get(i)).getBeginDate()
							.after(extract.getSpeaker().getArrivalDate()))
							|| !(sessions.get(sessionToExtractAssigned.get(i)).getEndDate()
									.before(extract.getSpeaker().getDepartureDate())))
					{
						fit = fit - 100;
					}
				}
			}
		}
		int sum =0;
		for(int j=0;j<sessionToExtractAssigned.size();++j)
		{
			Integer ck =countLectureInSession.get(sessionToExtractAssigned.get(j));
			int val = ck.intValue() +1;
			countLectureInSession.set(sessionToExtractAssigned.get(j), new Integer(val));
		}
		for (Integer integer : countLectureInSession)
		{
			sum=sum+integer.intValue();
		}
		int j=0;
		if(fit == 100 * idExtracts.size())
		{
			System.out.println(fit);
		}
		return fit;
	}

	public void setSessionToExtractAssignedByID(int idx, Integer value)
	{
		sessionToExtractAssigned.set(idx, value);
	}

	public Integer getSessionToExtractAssignedById(int idx)
	{
		return sessionToExtractAssigned.get(idx);
	}

	public void crossoverToID(ArrayList<Integer> list, int idBegin, int idEnd)
	{
		for (int i = idBegin; i < idEnd; ++i)
		{
			sessionToExtractAssigned.set(i, list.get(i));
		}
	}

	public ArrayList<Extract> getExtracts()
	{
		return extracts;
	}

	public ArrayList<Integer> getSessionToExtractAssigned()
	{
		return sessionToExtractAssigned;
	}

	public int getSizeAssign()
	{
		return sessionToExtractAssigned.size();
	}

	public void mutate()
	{
		Random rand = new Random();
		int index = rand.nextInt(sessionToExtractAssigned.size());
		int element = rand.nextInt((sessions.size() - 1 - 0) + 1) + 0;
		sessionToExtractAssigned.set(index, new Integer(element));
	}

}
