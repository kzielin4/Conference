package Zielinski.Kamil.Model;

public class Lecture
{
	private int idLecture;
	private String thema;
	private int speakerNumber;
	private int sessionNumber;
   
	public String getThema()
	{
		return thema;
	}

	public void setThema(String thema)
	{
		this.thema = thema;
	}

	public int getSpeakerNumber()
	{
		return speakerNumber;
	}

	public void setSpeakerNumber(int speakerNumber)
	{
		this.speakerNumber = speakerNumber;
	}

	public int getSessionNumber()
	{
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber)
	{
		this.sessionNumber = sessionNumber;
	}

	public int getIdLecture()
	{
		return idLecture;
	}

	public void setIdLecture(int idLecture)
	{
		this.idLecture = idLecture;
	}
}
