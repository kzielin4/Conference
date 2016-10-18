package Zielinski.Kamil.Model;

import java.util.ArrayList;

public class Lecture
{
	public Lecture(LectureType type, String thema, String extract)
	{
		super();
		this.type = type;
		this.thema = thema;
		this.abstractLecture = extract;
	}

	public enum LectureType
	{
		N, P
	};

	private LectureType type;
	//private int idLecture;
	private String thema;
	private String abstractLecture;
	//private int speakerNumber;
	private int sessionNumber;
	private boolean isAssigned;
	private int numberInSession;



	public String getThema()
	{
		return thema;
	}

	public void setThema(String thema)
	{
		this.thema = thema;
	}

/*	public int getSpeakerNumber()
	{
		return speakerNumber;
	}

	public void setSpeakerNumber(int speakerNumber)
	{
		this.speakerNumber = speakerNumber;
	}
	public int getIdLecture()
	{
		return idLecture;
	}

	public void setIdLecture(int idLecture)
	{
		this.idLecture = idLecture;
	}
	*/

	public int getSessionNumber()
	{
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber)
	{
		this.sessionNumber = sessionNumber;
	}

}
