package Zielinski.Kamil.Model;

public class Config
{
	static String username;
	int maxSession;
	int ammountLectureCategory;
	static Conference conference;
	static int exportType;
	static String fileName;

	public Config(int maxSession, int ammountLectureCategory)
	{
		this.maxSession = maxSession;
		this.ammountLectureCategory = ammountLectureCategory;
	}

	public static String getUsername()
	{
		return username;
	}

	public static void setUsername(String username)
	{
		Config.username = username;
	}

	public static Conference getConference()
	{
		return conference;
	}

	public static void setConference(Conference session)
	{
		Config.conference = session;
	}

	public static int getExportType()
	{
		return exportType;
	}

	public static void setExportType(int exportType)
	{
		Config.exportType = exportType;
	}

	public static String getFileName()
	{
		return fileName;
	}

	public static void setFileName(String fileName)
	{
		Config.fileName = fileName;
	}

}
