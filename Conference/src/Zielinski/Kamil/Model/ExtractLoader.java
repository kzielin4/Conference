package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExtractLoader
{
	private ArrayList<Extract> extracts;
   
	public void read(String file)
	{ 
		file="Extracts/"+file;
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				System.out.println(line);
			}
		}
		catch (IOException e)
		{
			System.out.println("CAN NOT OPEN THE PATH");
		}
	}

	public int countExtracts()
	{
		return extracts.size();
	}

	public ArrayList<Extract> getAllExtracts()
	{
		return extracts;
	}

	public Extract getExtract(int idx)
	{
		return extracts.get(idx);
	}

}
