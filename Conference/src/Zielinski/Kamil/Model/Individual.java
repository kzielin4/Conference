package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Random;

public class Individual
{
	private ArrayList<Integer> idExtracts;
	private CategoriesCounter counter;
	private ArrayList<Boolean> isAssigned;
	private ArrayList<Session> sessions;
	private ArrayList<Integer> sessionToExtractAssigned;
	private ArrayList<Extract> extracts;
	private Categories categories;
	private long fitValue;
	static int epislon;
	private int maxFitValue;
	final static int VALUEBONUS= 100000;
	final static int MAXBONUS=10;
	final static int MIDBONUS=6;
	final static int MINBONUS=3;

	public Individual(final ArrayList<Integer> idExtracts, final ArrayList<Session> sessions,
			final ArrayList<Extract> extracts, ArrayList<Integer> sessionToAssigned, Categories categories)
	{
		fitValue = 0;
		this.idExtracts = new ArrayList<Integer>(idExtracts);
		// this.isAssigned = new ArrayList<Boolean>(isAssigned);
		this.sessions = new ArrayList<Session>();
		this.sessionToExtractAssigned = new ArrayList<Integer>();
		this.extracts = new ArrayList<Extract>(extracts);
		// this.sessions=(ArrayList<Session>) sessions.clone();
		for (Session session : sessions)
		{
			this.sessions.add(new Session(session.getIdSession(), session.getBeginDate(), session.getEndDate(),
					session.getMaxAmmountLectureInSession(), session.getType(),session.getSessionName()));
		}
		this.sessionToExtractAssigned = new ArrayList<Integer>();
		if (sessionToAssigned != null)
		{
			for (Integer integer : sessionToAssigned)
			{
				this.sessionToExtractAssigned.add(integer);
			}
		}
		this.categories = categories;
		this.counter = new CategoriesCounter(categories.getCategories());
		this.counter.initCounters();
		this.maxFitValue = VALUEBONUS * idExtracts.size() + VALUEBONUS * sessions.size() + MAXBONUS * idExtracts.size() ;
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
				if (sessions.get(poz).ammountOfAssignedLectures() + 1 <= sessions.get(poz)
						.getMaxAmmountLectureInSession() )
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
		// for (Session ses : sessions)
		// {
		for (Integer integer : sessionToExtractAssigned)
		{
			System.out.print(" " + integer);
		}
		System.out.println(" ");
		++currentPosition;
		// }
	}

	public void printSessionAssigned()
	{
		int currentPosition = 1;
		for (Session ses : sessions)
		{
			System.out.println("Sesja " + ses.getIdSession());
			for (Integer integer : ses.getIdLectures())
			{
				System.out.println("W: " + integer);
			}
			++currentPosition;
		}

	}

	public long fitValue()
	{
		long fit = VALUEBONUS * idExtracts.size() + VALUEBONUS * sessions.size();
		ArrayList<Integer> countLectureInSession = new ArrayList<Integer>();
		setLectrureInSession();
		for (int j = 0; j < sessions.size(); ++j)
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
						fit = fit - VALUEBONUS;
					}
				}
			}
		}
		int sum = 0;
		for (int j = 0; j < sessionToExtractAssigned.size(); ++j)
		{
			Integer ck = countLectureInSession.get(sessionToExtractAssigned.get(j));
			int val = ck.intValue() + 1;
			countLectureInSession.set(sessionToExtractAssigned.get(j), new Integer(val));
		}

		int j = 0;
		for (Integer integer : countLectureInSession)
		{
			if (sessions.get(j).getMaxAmmountLectureInSession() < integer.intValue())
			{
				fit = fit - VALUEBONUS;
			}
			++j;
		}
		for (Session session : sessions)
		{
			for (int x = 0; x < session.ammountOfAssignedLectures(); ++x)
			{
				int id = session.getIdLectures().get(x).intValue();
				Extract extract = getExtractByid(id);
				int kw1 = extract.getKw1();
				int kw2 = extract.getKw2();
				int kw3 = extract.getKw3();
				counter.incrementCounter(kw1, 1);
				counter.incrementCounter(kw2, 2);
				counter.incrementCounter(kw3, 3);
			}
			counter.setSumCounters();
			ArrayList<Integer> commonCategories = new ArrayList<Integer>(
					counter.getCommonsCategories(session.ammountOfAssignedLectures()));
			int bestFit = 0;
			for (Integer idCategory : commonCategories)
			{
				int actualFit = 0;
				for (int x = 0; x < session.ammountOfAssignedLectures(); ++x)
				{
					int id = session.getIdLectures().get(x).intValue();
					Extract extract = getExtractByid(id);
					if (idCategory.intValue() == extract.getKw1())
					{
						actualFit = actualFit + MAXBONUS;
					}
					else if (idCategory.intValue() == extract.getKw2())
					{
						actualFit = actualFit + MIDBONUS;
					}
					else if (idCategory.intValue() == extract.getKw3())
					{
						actualFit = actualFit + MINBONUS;
					}
				}
				if (actualFit > bestFit)
				{
					bestFit = actualFit;
				}
			}
			fit = fit + bestFit;
			counter.resetCounters();
		}
		this.fitValue = fit;
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

	public void setLectrureInSession()
	{
		for (Session session : sessions)
		{
			session.clearIdLectures();
		}
		int i = 0;
		for (Integer idSession : sessionToExtractAssigned)
		{
			sessions.get(idSession.intValue()).addIdLectures(idExtracts.get(i));
			++i;
		}
	}

	public void mutate()
	{
		Random rand = new Random();
		int index1 = rand.nextInt(sessionToExtractAssigned.size());
		int index2 = rand.nextInt(sessionToExtractAssigned.size());
		int element = rand.nextInt((sessions.size() - 1 - 0) + 1) + 0;
		int val = sessionToExtractAssigned.get(index2).intValue();
		//sessionToExtractAssigned.set(index1, new Integer(element));
		sessionToExtractAssigned.set(index2, new Integer(sessionToExtractAssigned.get(index1).intValue()));
		sessionToExtractAssigned.set(index1, new Integer(val));
	}

	public long getFitValue()
	{
		return fitValue;
	}

	public void print()
	{
		if (getFitValue() == VALUEBONUS * idExtracts.size() + VALUEBONUS * sessions.size())
		{
			System.out.println("**************  " + getFitValue() + "**************  ");
		}
	}

	public Extract getExtractByid(int id)
	{
		for (Extract extract : extracts)
		{
			if (extract.getIdExtract() == id)
			{
				return extract;
			}
		}
		return null;
	}

	public void printCategories()
	{
		System.out.println("KATEGORIE:");
		for (Category extract : categories.getCategories())
		{
			System.out.println(extract.getIdCategory());
		}
	}

	public int getMaxFitValue()
	{
		return maxFitValue;
	}
	
	public boolean isOptimalIndyviudal()
	{
		if(fitValue == maxFitValue )
			return true;
		else 
			return false;
	}
	
	public long getMinFitValueToBeCorrect()
	{
		return VALUEBONUS * idExtracts.size() + VALUEBONUS * sessions.size();
	}

}
