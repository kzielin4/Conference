package Zielinski.Kamil.Model;

public class Logger
{
	public Logger(String logLine, String path)
	{
		super();
		this.logLine = logLine;
		this.path = path;
	}
	private String logLine;
    private String path;
	public void writeLog(String line)
	{

	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
}
