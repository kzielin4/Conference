package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class myLogger
{
	private java.util.logging.Logger logger;

	public myLogger()
	{
		logger = java.util.logging.Logger.getLogger("MyLog");
		FileHandler fh;

		try
		{

			fh = new FileHandler("Logs/log.txt", true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeError(String message)
	{
		logger.warning(message);
	}
}
