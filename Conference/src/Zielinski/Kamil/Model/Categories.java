package Zielinski.Kamil.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Categories
{

	private ArrayList<Category> categories;
	private Boolean isValid;
	private Validator validator;

	public Categories(ArrayList<Category> categories)
	{
		super();
		this.categories = categories;
		validator = new Validator();
	}

	public Categories()
	{
		ArrayList<Category> categories = new ArrayList<Category>();
		isValid = false;
		validator = new Validator();
	}
    
	public Boolean getIsValid()
	{
		return isValid;
	}

	public void setIsValid(Boolean isValid)
	{
		this.isValid = isValid;
	}

	public void addCategory(Category category)
	{
		categories.add(category);
	}
	
	public int getSize()
	{
		return categories.size();
	}

	public ArrayList<Category> loadCategories()
	{
		ArrayList<Category> categories = new ArrayList<Category>();
		String filePath = "Config/Categories.csv";
		File file = new File(filePath);
		try
		{
			Scanner input = new Scanner(file);
			input.useDelimiter("\n");
			int i = 0;
			while (input.hasNextLine())
			{
				String data = input.nextLine();
				ArrayList<String> extractData = new ArrayList<String>(Arrays.asList(data.split(",")));
				if (i != 0 && checkLine(extractData))
				{
					categories.add(new Category(Integer.parseInt(extractData.get(1)), extractData.get(0)));
				}
				else if (i != 0)
				{
					// log z b³edem
					return null;
				}
				i = i + 1;
			}
			input.close();

		}
		catch (IOException e)
		{
			// e.printStackTrace();
			System.out.println("elo123");
		}
		if (categories.size() == 0)
		{
			return null;
		}
		return categories;
	}

	public boolean checkLine(ArrayList<String> line)
	{
		if (line.size() != 2)
		{
			return false;
		}
		if (!validator.isNumeric(line.get(1)))
		{
			return false;
		}
		return true;
	}
	
	public boolean isCategory(int categoryNumber)
	{
		for(int i=0;i<categories.size();++i)
		{
			if (categories.get(i).getIdCategory()==categoryNumber)
			{
				return true;
			}
		}
		return false;
	}

	public void setCategories(ArrayList<Category> categories)
	{
		this.categories = categories;
	}

}
