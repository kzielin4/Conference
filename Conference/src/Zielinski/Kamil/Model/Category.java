package Zielinski.Kamil.Model;

public class Category
{
	int idCategory;
	String name;

	public Category(int idCategory, String name)
	{
		super();
		this.idCategory = idCategory;
		this.name = name;
	}

	public int getIdCategory()
	{
		return idCategory;
	}

	public void setIdCategory(int idCategory)
	{
		this.idCategory = idCategory;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
