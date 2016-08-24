package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExtractLoader
{
	public void read(String file)
	{
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
}
