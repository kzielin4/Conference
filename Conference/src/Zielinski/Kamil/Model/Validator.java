package Zielinski.Kamil.Model;

import java.awt.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.crypto.Data;

public class Validator
{
	public boolean validateLine(String line)
	{
		parseLine(line);
		return true;
	}

	public ArrayList<String> parseLine(String line)
	{
		ArrayList<String> extractData = new ArrayList<String>(Arrays.asList(line.split(",")));
		// System.out.println(extractData.size());
		return extractData;
	}

	public boolean validateName(String name)
	{
		return name.matches("[a-zA-Z]+");
	}

	public boolean validateLastName(String lastName)
	{
		return lastName.matches("[a-zA-Z]+");
	}

	public boolean validateDates(String arrival, String departure)
	{
		boolean isOK = false;
		try
		{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.util.Date date1 = sdf.parse(arrival);
			java.util.Date date2 = sdf.parse(departure);

			if (date1.before(date2))
			{
				isOK = true;
			}

		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		return isOK;
	}

	public boolean validateTimestamps(Timestamp t1, Timestamp t2)
	{
		if (t1.before(t2))
			return true;
		else
			return false;
	}

	public boolean isStringDateMinute(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		try
		{
			dateFormat.parse(date.trim());

		}
		catch (ParseException pe)
		{

			return false;
		}
		return true;
	}

	public boolean isStringDate(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try
		{
			dateFormat.parse(date.trim());

		}
		catch (ParseException pe)
		{

			return false;
		}
		return true;
	}

	public boolean isStringTime(String time)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setLenient(false);
		try
		{
			dateFormat.parse(time.trim());

		}
		catch (ParseException pe)
		{
			System.out.println("false");
			return false;
		}
		return true;
	}

	public boolean isTime(String startTime, String endTime)
	{

		if (!isStringTime(startTime) || !isStringTime(endTime))
		{
			return false;
		}
		return true;
	}

	public boolean isNumeric(String value)
	{
		try
		{
			double number = Double.parseDouble(value);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

	public boolean isLectureType(String type)
	{
		String s1 = new String("P");
		String s2 = new String("N");
		String s3 = new String(type);
		if (s1.equals(s3) || s2.equals(s3))
		{
			return true;
		}
		return false;
	}

	public boolean isPasswordValid(String password)
	{
		int uppercase = 0, lowercase = 0 , specialcase=0;
		char ch;
		if (password.length() < 8)
			return false;
		for (int i = 0; i < password.length(); i++)
		{
			ch = password.charAt(i);
			int asciivalue = (int) ch;
			if (asciivalue >= 65 && asciivalue <= 90)
			{
				uppercase++;
			}
			else if (asciivalue >= 97 && asciivalue <= 122)
			{
				lowercase++;
			}
			else if (asciivalue >= 33 && asciivalue <= 64)
			{
				specialcase++;
			}
		}
		if (lowercase == 0)
			return false;
		if (uppercase == 0)
			return false;
		if (specialcase == 0)
			return false;
		return true;
	}

}
