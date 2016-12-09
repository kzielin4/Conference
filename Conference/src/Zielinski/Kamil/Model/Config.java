package Zielinski.Kamil.Model;

public class Config
{
	static String username;
	int maxSession;
	int ammountLectureCategory;
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
	
}
