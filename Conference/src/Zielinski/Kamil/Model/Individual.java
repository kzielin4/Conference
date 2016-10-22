package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Random;

public class Individual
{
	private ArrayList<Integer> idExtracts;
	private ArrayList<Boolean> isAssigned;
	private ArrayList<Session> sessions;

	public Individual(final ArrayList<Integer> idExtracts, final ArrayList<Boolean> isAssigned,
			final ArrayList<Session> sessions)
	{
	
		this.idExtracts = new ArrayList<Integer>(idExtracts);
		this.isAssigned = new ArrayList<Boolean>(isAssigned);
		this.sessions = new ArrayList<Session>(sessions);
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
				}
			}
			++i;
		}
	}

	public void printObject()
	{
		int currentPosition = 1;
		for (Session ses : sessions)
		{
			System.out.println("Sesja " + currentPosition);
			for (Integer integer : ses.getIdLectures())
			{
				System.out.println("W: " + integer);
			}
			++currentPosition;
		}
		System.out.println("Sesja");
	}
}
