package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NormalLectureScheduler
{
	private Conference data;
	private ArrayList<Individual> population;
	private ArrayList<Individual> newPopulation;
	private ArrayList<Individual> bestIndividuals;
	private Individual bestPlan;
	final static int POP_STRT = 1000;// 5000 10000
	final static int ELITISM_K = 65; // 50
	final static int POP_SIZE = 140 + ELITISM_K; // population size /2000
													// 200+ELITISM_K;
	final static int MAX_ITER = 100; // max number of iterations //1000 2000
	final static double MUTATION_RATE = 0.3155555; // probability of mutation 0.05
	final static double CROSSOVER_RATE = 0.9405555; // probability of crossover
													// 0.8
	final static int MAXNOUPDATEITERATION = 300;
	private static Random randNumber;
	private long totalFitness;
	private int MAXVALUE;

	public NormalLectureScheduler(final Conference data)
	{
		super();
		population = new ArrayList<Individual>();
		newPopulation = new ArrayList<Individual>();
		bestIndividuals = new ArrayList<Individual>();
		this.data = data;
		totalFitness = 0;
		randNumber = new Random();
		MAXVALUE = 100 * data.getNormalExtractIdList().size() + 100 * data.getSessions().size();
	}

	public void runAlgorith()
	{
		initPopulation();
		geneticAlgorithm();
	}

	public void initPopulation()
	{
		long dif = System.currentTimeMillis();
		System.out.println("lol");
		for (int i = 0; i < POP_STRT; ++i)
		{
			Individual ind1 = new Individual(
					data.getNormalExtractIdList()/* , data.getFalseList() */, data.getNormalSessions(),
					data.getNormalExtracts(), null, data.getCategories());
			ind1.init();
			ind1.fitValue();
			// ind1.fitValue();
			// ind1.printObject();
			population.add(ind1);
			// data.clearId();
		}
		findBestStartIndividual();
		int sum = 0;
		for (Individual individual : population)
		{
			if (individual.getFitValue() >= MAXVALUE)
			{
				sum = sum + 1;
				// individual.printObject();
				// System.out.println("");
			}
			// individual.printObject();
		}
		System.out.println("-----------");
		/*
		 * System.out.println("\n-----------------\n"); for (int i = 0; i <
		 * population.size(); ++i) { //
		 * System.out.println(population.get(i).getSizeAssign());
		 * population.get(i).fitValue(); } System.out.println("KOniec");
		 */
		// geneticAlgorithm();
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
				ind1.getSessionToExtractAssigned(), data.getCategories());
		newIndiv[1] = new Individual(ind2.getIdExtracts(), ind2.getSessions(), ind2.getExtracts(),
				ind2.getSessionToExtractAssigned(), data.getCategories());

		int randPoint = randNumber.nextInt(ind1.getIdExtracts().size());
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

	public long countTotalFitness()
	{
		long sum = 0;
		for (Individual individual : population)
		{
			sum = sum + individual.getFitValue();
		}
		// System.out.println("sum: "+sum);
		totalFitness = sum;
		return sum;
	}

	public int rouletteWheelSelection()
	{
		/*
		 * double randNum = randNumber.nextDouble() * this.totalFitness; int
		 * idx; for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) { randNum =
		 * randNum - population.get(idx).getFitValue(); } return idx - 1;
		 */
		int idSelect = 0;
		/*
		 * long total = population.get(0).getFitValue(); for( int i = 1; i
		 * <POP_SIZE; i++ ) { long val=population.get(i).getFitValue(); if(
		 * randNumber.nextDouble() <= (val / totalFitness)) { idSelect = i; } }
		 */
		double rndNumber = randNumber.nextDouble();
		double offset = 0.0;
		int pick = 0;

		for (int i = 0; i < POP_SIZE; i++)
		{
			offset += population.get(i).getFitValue();
			if (rndNumber < offset / totalFitness)
			{
				idSelect = i;
				break;
			}
		}
		return idSelect;

	}

	public void findBestStartIndividual()
	{
		int idxMax = 0;
		long currentMax = 0;
		long currentVal;

		for (int idx = 0; idx < POP_STRT; ++idx)
		{
			currentVal = population.get(idx).getFitValue();
			if (currentVal > currentMax)
			{
				currentMax = currentVal;
				idxMax = idx;
			}
		}
		Collections.<Individual> swap(population, 0, idxMax);
	}

	public Individual findBestIndividual()
	{
		int idxMax = 0;
		long currentMax = 0;
		long currentVal;

		for (int idx = 0; idx < POP_SIZE; ++idx)
		{
			currentVal = population.get(idx).getFitValue();
			if (currentVal > currentMax)
			{
				currentMax = currentVal;
				idxMax = idx;
			}
		}
		return population.get(idxMax);
	}

	public void rateAllIndiviual()
	{
		long sum = 0;
		for (Individual individual : population)
		{
			sum = sum + individual.fitValue();
		}
		totalFitness = sum;
	}

	public void printAllFitValues()
	{
		System.out.println("Values:");
		for (Individual individual : population)
		{
			System.out.println(individual.getFitValue());
		}
		System.out.println("endOfValues");
	}

	public void geneticAlgorithm()
	{
		int maxMax = 0;
		System.out.println("stop1");
		int count;
		long lastBestValue = 0;
		int sum = 0;
		int counter = 0;
		for (int iter = 0; iter < MAX_ITER; iter++)
		{
			for (Individual individual : population)
			{
				// individual.printObject();
				// System.out.println(individual.getFitValue());
			}
			count = 0;
			sum = 0;
			// Elitism
			for (int i = 0; i < ELITISM_K; ++i)
			{
				newPopulation.add(findBestIndividual());
				++count;
			}

			System.out.println("\n-----------");
			countTotalFitness();
			// System.out.println(count);
			while (count < POP_SIZE)
			{
				// Selection
				int idx1 = rouletteWheelSelection();
				int idx2 = rouletteWheelSelection();
				// System.out.println("id1 " + idx1 + " id2: " + idx2);
				if (idx1 > POP_SIZE + ELITISM_K || idx2 > POP_SIZE + ELITISM_K)
				{
					System.out.println("bandofArray: " + idx1 + "  " + idx2);
				}
				Individual[] indiv = new Individual[2];
				indiv[0] = new Individual(population.get(idx1).getIdExtracts(), population.get(idx1).getSessions(),
						population.get(idx1).getExtracts(), population.get(idx1).getSessionToExtractAssigned(),
						data.getCategories());
				indiv[1] = new Individual(population.get(idx2).getIdExtracts(), population.get(idx2).getSessions(),
						population.get(idx2).getExtracts(), population.get(idx2).getSessionToExtractAssigned(),
						data.getCategories());

				// Crossover
				indiv[0].fitValue();
				indiv[1].fitValue();
				if (randNumber.nextDouble() < CROSSOVER_RATE)
				{
					indiv = crossover(indiv[0], indiv[1]);
				}

				// Mutation
				if (randNumber.nextDouble() < MUTATION_RATE)
				{
					indiv[0].mutate();
				}
				if (randNumber.nextDouble() < MUTATION_RATE)
				{
					indiv[1].mutate();
				}
				// indiv[0].printObject();
				// indiv[1].printObject();
				// add to new population
				// newPopulation.set(count, indiv[0]);
				// newPopulation.set(count+1, indiv[1]);
				indiv[0].fitValue();
				indiv[1].fitValue();
				newPopulation.add(indiv[0]);
				newPopulation.add(indiv[1]);
				count += 2;
			}
			sum = 0;
			for (Individual individual : newPopulation)
			{
				if (individual.getFitValue() == MAXVALUE)
				{
					sum = sum + 1;
					individual.printObject();
					// System.out.println("");
				}
				// ndividual.printObject();
			}
			if (sum > maxMax)
			{
				maxMax = sum;
			}
			System.out.println("Iter: " + iter);
			sum = 0;
			population.removeAll(population);
			population.addAll(newPopulation);
			newPopulation.removeAll(newPopulation);
			for (Individual individual : newPopulation)
			{
				individual.printObject();
			}
			countTotalFitness();
			System.gc();
			// rateAllIndiviual();
			// reevaluate current population
			System.out.print("Total Fitness = " + totalFitness);
			// System.out.println(" ; Best Fitness = " +
			// findBestIndividual().getFitValue());
			Individual bestOne = findBestIndividual();
			System.out.println("lol");
			System.out.println(bestOne.getFitValue());
			if (bestOne.isOptimalIndyviudal())
			{
				System.out.println("wbilem");
				System.out.println("PO ITERACJACH = " + iter);
				break;
			}
			if (bestOne.getFitValue() > lastBestValue)
			{
				counter = 0;
				lastBestValue = bestOne.getFitValue();
			}
			else if (counter > MAXNOUPDATEITERATION)
			{
				break;
			}
			++counter;
		}
		sum = 0;
		System.out.println(findBestIndividual().fitValue());
		// findBestIndividual().printSessionAssigned();
		System.out.println("---koniec-----");
	}

	public int countDif()
	{
		int sum = 0;
		int ctemp = 0;
		ArrayList<Integer> tempList;
		for (Individual individual : population)
		{
			tempList = individual.getSessionToExtractAssigned();
			for (Individual individual2 : population)
			{
				if (individual2.getSessionToExtractAssigned().equals(tempList))
				{
					++ctemp;
				}
			}
			if (ctemp == 1)
			{
				++sum;
			}
			ctemp = 0;
		}
		return sum;
	}
}
