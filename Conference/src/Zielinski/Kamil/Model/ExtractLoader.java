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

import Zielinski.Kamil.Model.Lecture.LectureType;
import Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType;

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

	public ArrayList<Extract> loadExtracts()
	{
		MyLogger logger = new MyLogger();
		categories.setCategories(categories.loadCategories());
		if (categories.getSize() == 0)
		{
			// tu log ze brak kategorii
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
				if (i != 0 && validateExtractLine(extractData,i+1))
				{
					// DEVIDE AUTHORS
					ArrayList<String> authorsList = new ArrayList<String>(Arrays.asList(extractData.get(9).split("/")));
					// Lecture lecture = new Lecture(idLecture, thema,
					// speakerNumber, sessionNumber);
					Timestamp beginTimestamp = Timestamp.valueOf(extractData.get(7).replace(".", "-") + ":00");
					Timestamp endTimestamp = Timestamp.valueOf(extractData.get(8).replace(".", "-") + ":00");
					if(!validator.validateTimestamps(beginTimestamp, endTimestamp))
					{
						int num = i+1;
						logger.writeError("Wrong date ARRIVAL<DEPARTURE Extract csv file in line "+ num);
						return null;
					}
					Speaker speaker = new Speaker(authorsList.get(0), beginTimestamp, endTimestamp);
					Lecture lecture;
					if (extractData.get(6).equals(new String("P")))
					{
						lecture = new Lecture(LectureType.P, extractData.get(1), extractData.get(2));
					}
					else
					{
						lecture = new Lecture(LectureType.N, extractData.get(1), extractData.get(2));
					}
					extractList.add(new Extract(lecture, speaker, Integer.parseInt(extractData.get(0)),Integer.parseInt(extractData.get(3)),Integer.parseInt(extractData.get(4)),Integer.parseInt(extractData.get(5))));
				}
				else if (i != 0)
				{
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
	public boolean validateExtractLine(ArrayList<String> extractData,int number)
	{
		MyLogger logger = new MyLogger();
		int requiredFields = 10;
		if (extractData.size() != requiredFields)
		{
			logger.writeError("Wrong ammount of fields in Extract csv file in line "+ number);
			return false;
		}
		else
		{
			if (!validator.isNumeric(extractData.get(0)))
			{
				logger.writeError("Wrong ID_LECTURE format in Extract csv file in line "+ number);
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
						int n1 =i-2;
						logger.writeError("Wrong KW"+n1+" in Extract csv file in line "+ number);
						return false;
					}
				}

				if (!validator.isLectureType(extractData.get(6)))
				{
					logger.writeError("Wrong LECTURE_TYPE in Extract csv file in line "+ number);
					return false;
				}
				if (!validator.isStringDateMinute(extractData.get(7).replace(".", "-"))
						|| !validator.isStringDateMinute(extractData.get(8).replace(".", "-")))
				{
					logger.writeError("Wrong date of ARRIVAL or DEPARTURE in csv file in line "+ number);
					return false;
				}
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
