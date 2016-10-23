package Zielinski.Kamil.Model;

public class Settings
{
	final int minutePerNormalLecture = 20;
	final int minutePerPlenaryLecture = 60;
	final int minimalUncertainty = 180;

	public int getMinutePerLecture()
	{
		return minutePerNormalLecture;
	}

	public int getMinutePerPlenaryLecture()
	{
		return minutePerPlenaryLecture;
	}
	
}
