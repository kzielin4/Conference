package Zielinski.Kamil.Model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.xml.crypto.Data;
public class Validator
{
	public int validate(String line)
	{
		return 1;
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
		finally
		{
			return isOK;
		}
	}

	public boolean isStringDate(String date)
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

}
