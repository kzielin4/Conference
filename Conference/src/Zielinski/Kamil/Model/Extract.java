/**
 * 
 */
package Zielinski.Kamil.Model;

/**
 * @author k.zielinski
 *
 */
public class Extract
{
	private Lecture lecture;
	private Speaker speaker;

	public Extract(Lecture lecture, Speaker speaker)
	{
		super();
		this.lecture = lecture;
		this.speaker = speaker;
	}

	public Lecture getLecture()
	{
		return lecture;
	}

	public void setLecture(Lecture lecture)
	{
		this.lecture = lecture;
	}

	public Speaker getSpeaker()
	{
		return speaker;
	}

	public void setSpeaker(Speaker speaker)
	{
		this.speaker = speaker;
	}

}
