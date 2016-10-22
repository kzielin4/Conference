package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Random;

public class Scheduler
{
	private Conference data;
	private ArrayList<Individual> population;
	private ArrayList<Individual> bestIndividuals;

	public Scheduler(final Conference data)
	{
		super();
		population = new ArrayList<Individual>();
		bestIndividuals = new ArrayList<Individual>();
		this.data = data;
	}

	public void initPopulation()
	{
		System.out.println(System.currentTimeMillis());
		System.out.println("lol");
		for (int i = 0; i < 1; ++i)
		{
			Individual ind1 = new Individual(data.getExtractIdList(), data.getFalseList(), data.getSessions());
			ind1.init();
			for (Session individual : data.getSessions())
			{
				individual.printSession();
			}
		}
		System.out.println("kkkkkkkkkkkkkkoooooooooooooonnnnnnnnnnnnniec");
		System.out.println(System.currentTimeMillis());
	}

	public long fitFunction()
	{
		long fit = 0;
		return fit;
	}
}
