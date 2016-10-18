package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

public class ExtractLoader
{
	private ArrayList<Extract> extracts;
	private Validator validator;
	private Categories categories;

	public ExtractLoader()
	{
		super();
		extracts = new ArrayList<Extract>();
		validator = new Validator();
		categories = new Categories();
	}

	// Main method of ExtractLoader
	/*
	 * public void executeLoading() { File[] files=getAllFiles("Extracts/"); for
	 * (File file : files) { ArrayList<String>
	 * extractData=readData(file.getName()); if(extractData.size()==0) {
	 * continue; } else { for(String extractLine: extractData) {
	 * validator.validateLine(extractLine); } } } } //Method to load data from
	 * giving extract public File[] getAllFiles(String direct) {
	 * //direct="Extracts/"+direct; File directory = new File(direct); File[]
	 * fList = directory.listFiles(); for (File file : fList) { if
	 * (file.isFile()) { System.out.println(file.getAbsolutePath()); } } return
	 * fList; }
	 * 
	 * public ArrayList<String> readData(String file) { ArrayList<String>
	 * exctractData=new ArrayList<String>(); file = "Extracts/" + file; try
	 * (BufferedReader br = new BufferedReader(new FileReader(file))) { String
	 * line; while ((line = br.readLine()) != null) { System.out.println(line);
	 * exctractData.add(line); } } catch (IOException e) { System.out.println(
	 * "CAN NOT OPEN THE PATH"); } return exctractData; }
	 */
	public ArrayList<Extract> loadExtracts()
	{
		categories.setCategories(categories.loadCategories());
		if(categories.getSize()==0)
		{
			//tu log ze brak kategorii
			return null;
		}
		ArrayList<Extract> extractList = new ArrayList<Extract>();
		String filePath = "Extracts/Extracts.csv";
		File file = new File(filePath);
		int i = 0;
		try
		{
			Scanner input = new Scanner(file);
			input.useDelimiter("\n");
			while (input.hasNextLine())
			{
				String data = input.nextLine();
				ArrayList<String> extractData = new ArrayList<String>(Arrays.asList(data.split(",")));
				System.out.println(extractData.size());
				if (i != 0 && validateExtractLine(extractData))
				{
					// DEVIDE AUTHORS
					ArrayList<String> authorsList = new ArrayList<String>(Arrays.asList(extractData.get(9).split("/")));
					//Lecture lecture = new Lecture(idLecture, thema, speakerNumber, sessionNumber);
					Timestamp beginTimestamp = Timestamp.valueOf(extractData.get(7).replace(".", "-") + ":00");
					Timestamp endTimestamp = Timestamp.valueOf(extractData.get(8).replace(".", "-") + ":00");
					Speaker speaker = new Speaker(authorsList.get(0),beginTimestamp , endTimestamp);
					// TODO
					// TU DODAÆ WYK£ADOWCÓW
					// TU DODAÆ WYK£ADY
				}
				else if (i != 0)
				{
					System.out.println(i);
					return null;
				}
				++i;
			}
			input.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		System.out.println(i);
		return extractList;
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

	// TODO
	// ROZSZERZ O CONFIG ile ma byæ pól
	public boolean validateExtractLine(ArrayList<String> extractData)
	{
		int requiredFields = 10;
		if (extractData.size() != requiredFields)
		{
			return false;
		}
		else
		{
			if (!validator.isNumeric(extractData.get(0)))
			{
				System.out.println("id-nie");
				// tu log o zlym formacie id
				return false;
			}
			// warunek kategorii
			else
			{
				for (int i = 3; i < 6; ++i)
				{
					// check is category filed is okej
					if (!validator.isNumeric(extractData.get(i)) || !isCategory(extractData.get(i)))
					{
						System.out.println("categorie-nie");
						return false;
					}
				}

				if (!validator.isLectureType(extractData.get(6)))
				{
					System.out.println("type-nie");
					return false;
				}
				if (!validator.isStringDateMinute(extractData.get(7).replace(".", "-"))
						|| !validator.isStringDateMinute(extractData.get(8).replace(".", "-")))
				{
					System.out.println("data-nie");
					return false;
				}
				// to trzeba bêdzie zrobiæ przy tworzeniu klasy
				// extractData.get(7)=extractData.get(7).replace(".", "-");
				// TODO
				// CZY DODAÆ WALIDACJE IMION
			}
		}
		return true;
	}

	private boolean isStream(String string)
	{
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isCategory(String string)
	{
		int num = Integer.parseInt(string);
		if (categories.isCategory(num))
		{
			return true;
		}
		return false;
	}

	public void setCategories(Categories categories)
	{
		this.categories = categories;
	}
}
