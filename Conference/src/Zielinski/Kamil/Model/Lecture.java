package Zielinski.Kamil.Model;

import java.util.ArrayList;

public class Lecture
{
	public enum LectureType{
		N,P
	};
	private LectureType type;
	private int idLecture;
	private String thema;
	private String extract;
	private ArrayList<String> authorsList;
	private int speakerNumber;
	private int sessionNumber;
	private boolean isAssigned;
	private int numberInSession;

	public Lecture(int idLecture, String thema, int speakerNumber, int sessionNumber)
	{
		super();
		this.idLecture = idLecture;
		this.thema = thema;
		this.speakerNumber = speakerNumber;
		this.sessionNumber = sessionNumber;
	}

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
