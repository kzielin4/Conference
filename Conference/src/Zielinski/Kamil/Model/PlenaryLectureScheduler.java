package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlenaryLectureScheduler
{
	private ArrayList<Session> sessions;
	private ArrayList<Extract> extracts;
	private int costAssigned[][];
	private int allocation[][];
	private int lineTable[][];

	public PlenaryLectureScheduler(ArrayList<Session> sessions, ArrayList<Extract> extracts)
	{
		super();
		this.sessions = sessions;
		this.extracts = extracts;
		this.costAssigned = new int[extracts.size()][sessions.size()];
		this.allocation = new int[extracts.size()][sessions.size()];
		this.lineTable = new int[extracts.size()][sessions.size()];
		for (int i = 0; i < extracts.size(); ++i)
			for (int j = 0; j < sessions.size(); ++j)
			{
				costAssigned[i][j] = 0;
				lineTable[i][j] = 1;
				allocation[i][j] = 0;
			}
		setCostAssigned();
		List<Line> minLines = MinLines.getMinLines(costAssigned);
		System.out.printf("Min num of lines for example matrix is: %d\n", minLines.size());
		// MinLines.printResult(costAssigned,minLines);
		System.out.println("Size: " + minLines.size());
		if(minLines.size()<extracts.size())
			System.exit(0);
		pritncostAssigned();
		makeAssignAlgorithm();
	}

	public void pritncostAssigned()
	{
		System.out.println("Table Cost Assign");
		for (int i = 0; i < extracts.size(); ++i)
		{
			for (int j = 0; j < sessions.size(); ++j)
				System.out.print(costAssigned[i][j] + " ");
			System.out.println("");
		}
	}

	public void setCostAssigned()
	{
		int i = 0, j = 0;
		for (Extract extract : extracts)
		{
			for (Session session : sessions)
			{
				if (!(session.getBeginDate().after(extract.getSpeaker().getArrivalDate()))
						|| !(session.getEndDate().before(extract.getSpeaker().getDepartureDate())))
				{
					costAssigned[i][j] = 1;
					System.out.println("lol");
				}
				++j;
			}
			++i;
			j = 0;
		}
	}

	public void resetAlocation()
	{
		for (int i = 0; i < extracts.size(); ++i)
			for (int j = 0; j < sessions.size(); ++j)
			{
				allocation[i][j] = 0;
			}
	}

	public boolean checkAssign()
	{
		for (int i = 0; i < extracts.size(); ++i)
		{
			int countAssign = 0;
			for (int j = 0; j < sessions.size(); ++j)
			{
				if (allocation[i][j] == 1)
				{
					++countAssign;
				}
			}
			if (countAssign != 1)
			{
				return false;
			}
			countAssign = 0;
		}
		for (int i = 0; i < extracts.size(); ++i)
		{
			int countAssign = 0;
			for (int j = 0; j < sessions.size(); ++j)
			{
				if (allocation[j][i] == 1)
				{
					++countAssign;
				}
			}
			if (countAssign != 1)
			{
				return false;
			}
			countAssign = 0;
		}
		return true;
	}

	public void makeAssignAlgorithm()
	{
		boolean isOkAssign = false;
		Random rand = new Random();
		while (!isOkAssign)
		{
			for (int i = 0; i < extracts.size(); ++i)
			{
				ArrayList<Integer> idRowsZero = new ArrayList<Integer>();
				for (int j = 0; j < sessions.size(); ++j)
				{
					if (costAssigned[i][j] == 0)
					{
						idRowsZero.add(new Integer(j));
					}
				}
				int poz = rand.nextInt((idRowsZero.size() - 1 - 0) + 1) + 0;
				int val = idRowsZero.get(poz).intValue();
				allocation[i][val] = 1;
				idRowsZero.clear();
			}
			isOkAssign = checkAssign();
			if (!isOkAssign)
			{
				resetAlocation();
			}
		}
		assignedLectureToSessions();
	}

	public void printAssigned()
	{
		System.out.println("\nAssigned: ");
		for (int i = 0; i < extracts.size(); ++i)
		{
			for (int j = 0; j < sessions.size(); ++j)
				System.out.print(allocation[i][j] + " ");
			System.out.println("");
		}
	}

	public void assignedLectureToSessions()
	{
		for (int i = 0; i < extracts.size(); ++i)
		{
			for (int j = 0; j < sessions.size(); ++j)
			{
				if (allocation[i][j] == 1)
				{
					sessions.get(j).addIdLectures(new Integer(extracts.get(i).getIdExtract()));
				}
			}
		}
		printLectureAssigned();
	}
	
	public void printLectureAssigned()
	{
		for (Session ses : sessions)
		{
			System.out.println("Sesja " + ses.getIdSession());
			for (Integer integer : ses.getIdLectures())
			{
				System.out.println("W: " + integer);
			}
		}

	}
}
