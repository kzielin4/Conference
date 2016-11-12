package Zielinski.Kamil.Model;

import java.util.ArrayList;

public class CategoriesCounter
{

	private ArrayList<Category> categories;
	private ArrayList<CategoryCounter> counters;

	public CategoriesCounter(ArrayList<Category> categories)
	{
		super();
		this.categories = categories;
		this.counters = new ArrayList<CategoryCounter>();
		initCounters();
	}

	public void initCounters()
	{
		for (Category category : categories)
		{
			counters.add(new CategoryCounter(category.getIdCategory()));
		}
	}

	public void resetCounters()
	{
		for (CategoryCounter counter : counters)
		{
			counter.resetAllKw();
		}
	}

	public void incrementCounter(int idCategory, int kw)
	{
		for (CategoryCounter counter : counters)
		{
			if (counter.getIdCategory() == idCategory)
			{
				if (kw == 1)
				{
					counter.incrementKw1Counter();
				}
				else if (kw == 2)
				{
					counter.incrementKw2Counter();
				}
				else if (kw == 3)
				{
					counter.incrementKw3Counter();
				}
			    break;
			}
		}
	}
	
	public void setSumCounters()
	{
		for (CategoryCounter counter : counters)
		{
			counter.setSumCounter();
		}
	}
	
	public ArrayList<Integer> getCommonsCategories(int size)
	{
		ArrayList<Integer> commonCategories = new ArrayList<Integer>();
		if (size == 0)
		{
			return commonCategories;
		}
		for (CategoryCounter counter : counters)
		{
			if(counter.getSumCounter()==size)
			{
				commonCategories.add(new Integer(counter.getIdCategory()));
			}
		}
		return commonCategories;
	}
	
}
