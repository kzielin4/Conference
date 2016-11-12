package Zielinski.Kamil.Model;

public class CategoryCounter
{

	private int idCategory;
	private int kw1Counter;
	private int kw2Counter;
	private int kw3Counter;
	private int sumCounter;

	public CategoryCounter(int idCategory)
	{
		super();
		this.idCategory = idCategory;
		kw1Counter = 0;
		kw2Counter = 0;
		kw3Counter = 0;
		sumCounter = 0;
	}

	public int getIdCategory()
	{
		return idCategory;
	}

	public int getKw1Counter()
	{
		return kw1Counter;
	}

	public int getKw2Counter()
	{
		return kw2Counter;
	}

	public int getKw3Counter()
	{
		return kw3Counter;
	}

	public void incrementKw1Counter()
	{
		++kw1Counter;
	}

	public void incrementKw2Counter()
	{
		++kw2Counter;
	}

	public void incrementKw3Counter()
	{
		++kw3Counter;
	}

	public void resetKw1Counter()
	{
		kw1Counter = 0;
	}

	public void resetKw2Counter()
	{
		kw2Counter = 0;
	}

	public void resetKw3Counter()
	{
		kw3Counter = 0;
	}

	public void resetAllKw()
	{
		resetKw1Counter();
		resetKw2Counter();
		resetKw3Counter();
	}

	public void setSumCounter()
	{
		sumCounter = kw1Counter + kw2Counter + kw3Counter;
	}

	public int getSumCounter()
	{
		return sumCounter;
	}
	
}
