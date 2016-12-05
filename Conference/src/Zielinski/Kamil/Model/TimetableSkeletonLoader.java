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
		SESSION, PLENARY, OTHER, ERROR
	}

	public Validator validator;

	public TimetableSkeletonLoader()
	{
		validator = new Validator();
	}

	public TimetableSkeleton loadTimetableSkeleton()
	{
		TimetableSkeleton skeleton = new TimetableSkeleton();
		String filePath = "config/szkilet.csv";
		File file = new File(filePath);
		Timestamp lineDate;
		String lineDateString = "NULL";
		MyLogger logger = new MyLogger();
		try
		{
			Scanner input = new Scanner(file);
			input.useDelimiter("\n");
			int num =1;
			while (input.hasNext())
			{
				String data = input.next();
				if (this.checkLineType(data) == LineType.DATE)
				{
					ArrayList<String> line = new ArrayList<String>(Arrays.asList(data.split(",")));
					if (validator.isStringDate(line.get(0)))
					{
						lineDateString = line.get(0);
						lineDate = Timestamp.valueOf(line.get(0) + " 00:00:00");
						System.out.println(lineDate);
					}
					else
					{
						logger.writeError("Expect date format YYYY-MM-DD in Skeleton csv file line "+num);
						return null;
					}
				}
				else
				{
					ArrayList<String> fields = new ArrayList<String>(Arrays.asList(data.split(",")));
					if (fields.get(0).length() == 11 && lineDateString != "NULL")
					{
						String beginTime = fields.get(0).substring(0, 5);
						String endTime = fields.get(0).substring(6, 11);
						System.out.println(beginTime + " " + endTime);
						if (validator.isTime(beginTime, endTime))
						{
							Timestamp beginTimestamp = Timestamp.valueOf(lineDateString + " " + beginTime + ":00");
							Timestamp endTimestamp = Timestamp.valueOf(lineDateString + " " + endTime + ":00");
							long dif = (endTimestamp.getTime()) - (beginTimestamp.getTime());
							System.out.println(dif);
							EventType eventType = checkEvent(fields);
							if (eventType == EventType.PLENARY)
							{
								System.out.println("plenart");
								skeleton.addTimeUnit(
										new TimeUnit(beginTimestamp,endTimestamp, dif, fields.get(1), EventType.PLENARY));
							}
							else if (eventType == EventType.OTHER)
							{
								System.out.println("other");
								skeleton.addTimeUnit(new TimeUnit(beginTimestamp,endTimestamp, dif, fields.get(1), EventType.OTHER));
							}
							else if (eventType == EventType.SESSION)
							{
								for (int i = 1; i < fields.size(); i++)
								{
									System.out.println("session");
									skeleton.addTimeUnit(
											new TimeUnit(beginTimestamp,endTimestamp, dif, fields.get(i), EventType.SESSION));
								}
							}
							else
							{
								logger.writeError("Wrong type of event in Skeleton csv file line "+num);
								return null;
							}
						}
						else
						{
							logger.writeError("Expect hours format HH:MM-HH:MM in Skeleton csv file line "+num);
							return null;
						}
					}
					else
					{
						logger.writeError("Wrong type of line in Skeleton csv file line "+num);
						return null;
					}

				}
				++num;
			}
			input.close();

		}
		catch (IOException e)
		{

			e.printStackTrace();
		}
		return skeleton;
	}

	public LineType checkLineType(String line)
	{
		System.out.println(line);
		ArrayList<String> checkLine = new ArrayList<String>(Arrays.asList(line.split(",")));
		System.out.println(checkLine.size());
		int counter = 0;
		for (String string : checkLine)
		{
		    if(string.length()>0)
		    	++counter;
		}
		System.out.println(counter);
		if (counter==2)
		{
			return LineType.DATE;
		}
		else
			return LineType.DATA;
	}

	// TOFIX
	public EventType checkEvent(ArrayList<String> fields)
	{
		System.out.println("SIZE TYPE: "+fields.size());
		int counter = 0;
		for (String string : fields)
		{
		    if(string.length()>0)
		    	++counter;
		}
		if (counter == 3)
		{
			if (fields.get(1).length() >= 7
					&& ((String) fields.get(1).subSequence(0, 7)).toUpperCase().contains("PLENARY"))
			{

				return EventType.PLENARY;
			}
			else
			{

				return EventType.OTHER;
			}
		}
		else
		{
			for (String field : fields)
			{
				if (fields.indexOf(field) != 0 && !field.toUpperCase().contains("SESSION"))
				{

					return EventType.ERROR;
				}
			}
		}

		return EventType.SESSION;
	}
}
