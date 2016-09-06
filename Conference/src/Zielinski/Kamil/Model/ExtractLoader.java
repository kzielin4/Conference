package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

public class ExtractLoader
{
	private ArrayList<Extract> extracts;
	private Validator validator;
    
	public ExtractLoader()
	{
		super();
		extracts=new ArrayList<Extract>();
		validator=new Validator();
	}
    //Main method of ExtractLoader 
	public void executeLoading()
	{
		File[] files=getAllFiles("Extracts/");
		for (File file : files)
		{
			ArrayList<String> extractData=readData(file.getName());
			if(extractData.size()==0)
			{
				continue;
			}
			else
			{
				for(String extractLine: extractData)
				{
					validator.validateLine(extractLine);
				}
			}
		}
	}
    //Method to load data from giving extract
	public File[] getAllFiles(String direct)
	{
		//direct="Extracts/"+direct;
		File directory = new File(direct);
		File[] fList = directory.listFiles();
		for (File file : fList)
		{
			if (file.isFile())
			{
				System.out.println(file.getAbsolutePath());
			}
		}
		return fList;
	}

	public ArrayList<String> readData(String file)
	{
		ArrayList<String> exctractData=new ArrayList<String>();
		file = "Extracts/" + file;
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				System.out.println(line);
				exctractData.add(line);
			}
		}
		catch (IOException e)
		{
			System.out.println("CAN NOT OPEN THE PATH");
		}
		return exctractData;
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
