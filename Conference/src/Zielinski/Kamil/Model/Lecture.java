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

	public String getThema()
	{
		return thema;
	}

	public void setThema(String thema)
	{
		this.thema = thema;
	}


	public int getSessionNumber()
	{
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber)
	{
		this.sessionNumber = sessionNumber;
	}

	public LectureType getType()
	{
		return type;
	}

	public String getAbstractLecture()
	{
		return abstractLecture;
	}

	public void setAbstractLecture(String abstractLecture)
	{
		this.abstractLecture = abstractLecture;
	}

}
