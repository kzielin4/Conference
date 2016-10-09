package Zielinski.Kamil.Model;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TimetableSkeletonLoader
{
	public enum LineType
	{
		DATE, DATA
	};
    public enum EventType
    {
    	SESSION,PLENARY,OTHER,ERROR
    }
	public Validator validator;

	public TimetableSkeletonLoader()
	{
		validator = new Validator();
	}

	public boolean loadTimetableSkeleton()
	{
		String filePath = "config/szkilet.csv";
		File file = new File(filePath);
		Timestamp lineDate;
		String lineDateString="NULL";
		try
		{
			Scanner input = new Scanner(file);
			while (input.hasNext())
			{
				String data = input.next();
				if (this.checkLineType(data) == LineType.DATE)
				{
					ArrayList<String> line = new ArrayList<String>(Arrays.asList(data.split(",")));
					System.out.println(line.get(0));
					if (validator.isStringDate(line.get(0)))
					{
						//System.out.println("okej");
						lineDateString=line.get(0);
						lineDate = Timestamp.valueOf(line.get(0)+ " 00:00:00");
					}
					else
					{
						// TODO
						/*
						 * Tu trzeba dodaæ obs³ugê b³êdu - tzn log daæ ¿e Ÿle i
						 * trzeba poprawiæ;
						 */
					}
				}
				else
				{
					ArrayList<String> fields = new ArrayList<String>(Arrays.asList(data.split(",")));
					if (fields.get(0).length() == 11 && lineDateString!="NULL")
					{
						String beginTime = fields.get(0).substring(0, 5);
						String endTime = fields.get(0).substring(6, 11);
						System.out.println(beginTime + " " + endTime);
						if(validator.isTime(beginTime, endTime))
						{
							Timestamp beginTimestamp=Timestamp.valueOf(lineDateString+ " "+beginTime+":00" );
							Timestamp endTimestamp=Timestamp.valueOf(lineDateString+ " "+endTime+":00" );
							checkEvent(fields);
						}
						else
						{
							//TODO
							/*
							 * Obsluga bledu
							 */
						}
					}
					else
					{
						//TODO
						/*
						 * Obsluga bledu
						 */
					}

				}
			}
			input.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		return false;
	}

	public LineType checkLineType(String line)
	{
		ArrayList<String> checkLine = new ArrayList<String>(Arrays.asList(line.split(",")));
		if (checkLine.size() == 1)
		{
			return LineType.DATE;
		}
		else
			return LineType.DATA;
	}
	//TOFIX
	public EventType checkEvent(ArrayList<String> fields)
	{
		if(fields.size()==2)
		{
			if(fields.get(1).length()>7 &&((String) fields.get(1).subSequence(0,7)).toUpperCase().contains("PLENARY"))
			{
				System.out.println("plenary");
				return EventType.PLENARY;
			}
			else
			{
				System.out.println("other");
				return EventType.OTHER;
			}
		}
		else
		{
			for (String field : fields)
			{
				if(fields.get(1).length()>7 && !((String) field.subSequence(0,7)).toUpperCase().contains("SESSION"))
				{
					System.out.println("error");
					return EventType.ERROR;
				}
			}
		}
		System.out.println("session");
		return EventType.SESSION;
	}
}
