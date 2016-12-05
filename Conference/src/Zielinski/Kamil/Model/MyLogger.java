package Zielinski.Kamil.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class MyLogger
{
	private java.util.logging.Logger logger;
	private FileHandler fh;

	public MyLogger()
	{
		logger = java.util.logging.Logger.getLogger("MyLog");
	}
  
	public void writeError(String message)
	{
		try
		{

			fh = new FileHandler("Logs/log.txt",true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.warning(message);
			fh.close();

		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		logger.warning(message);
	}
	public void close()
	{
		fh.close();
	}
}
