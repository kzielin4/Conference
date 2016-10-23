package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Random;

public class Scheduler
{
	private Conference data;
	private ArrayList<Individual> population;
	private ArrayList<Individual> bestIndividuals;
	final static int ELITISM_K = 5;
	final static int POP_SIZE = 200 + ELITISM_K; // population size
	final static int MAX_ITER = 2000; // max number of iterations
	final static double MUTATION_RATE = 0.05; // probability of mutation
	final static double CROSSOVER_RATE = 0.7; // probability of crossover
	private static Random randNumber = new Random();
	private long totalFitness;

	public Scheduler(final Conference data)
	{
		super();
		population = new ArrayList<Individual>();
		bestIndividuals = new ArrayList<Individual>();
		this.data = data;
		totalFitness = 0;
	}

	public void initPopulation()
	{
		long dif = System.currentTimeMillis();
		System.out.println("lol");
		for (int i = 0; i < 3; ++i)
		{
			Individual ind1 = new Individual(
					data.getExtractIdList()/* , data.getFalseList() */, data.getSessions(), data.getExtracts(), null);
			ind1.init();
			ind1.fitValue();
			// ind1.printObject();
			population.add(ind1);
			// data.clearId();
		}
		for(int i = 0; i < 200000; ++i)
		{
	    Random rand = new Random();
		int element1 = rand .nextInt((population.size() - 1 - 0) + 1) + 0;
		int element2 = rand .nextInt((population.size() - 1 - 0) + 1) + 0;
		Individual[] newindividual = crossover(population.get(element1), population.get(element2));
		population.add(newindividual[0]);
		population.add(newindividual[1]);
		}
		
		System.out.println("\n-----------------\n");
		for (int i = 0; i < population.size(); ++i)
		{
			// System.out.println(population.get(i).getSizeAssign());
			population.get(i).fitValue();
		}

	}

	public long fitFunction()
	{
		long fit = 0;
		return fit;
	}

	public Individual[] crossover(Individual ind1, Individual ind2)
	{
		Individual[] newIndiv = new Individual[2];
		newIndiv[0] = new Individual(ind1.getIdExtracts(), ind1.getSessions(), ind1.getExtracts(),
				ind1.getSessionToExtractAssigned());
		newIndiv[1] = new Individual(ind2.getIdExtracts(), ind2.getSessions(), ind2.getExtracts(),
				ind2.getSessionToExtractAssigned());

		int randPoint = randNumber.nextInt(ind1.getIdExtracts().size());
		System.out.println(randPoint);
		int i;
		for (i = 0; i < randPoint; ++i)
		{
			newIndiv[0].setSessionToExtractAssignedByID(i, ind1.getSessionToExtractAssignedById(i));
			newIndiv[1].setSessionToExtractAssignedByID(i, ind2.getSessionToExtractAssignedById(i));
		}
		for (; i < ind1.getIdExtracts().size(); ++i)
		{
			newIndiv[0].setSessionToExtractAssignedByID(i, ind2.getSessionToExtractAssignedById(i));
			newIndiv[1].setSessionToExtractAssignedByID(i, ind1.getSessionToExtractAssignedById(i));
		}

		return newIndiv;
	}

	public void mutate(Individual ind)
	{
		ind.mutate();
	}
}
