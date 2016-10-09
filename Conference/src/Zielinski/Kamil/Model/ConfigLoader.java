package Zielinski.Kamil.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ConfigLoader
{
	public boolean passwordVeryfication()
	{
		String filePath = "config/Config.csv";
		File file = new File(filePath);

		try
		{
			Scanner input = new Scanner(file);
			while (input.hasNext())
			{
				String data = input.next();
				ArrayList<String> userData = new ArrayList<String>(Arrays.asList(data.split(",")));
			}
			input.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		return false;
	}
}
