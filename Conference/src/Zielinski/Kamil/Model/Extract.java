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
	private int idExtract;

	public Extract(Lecture lecture, Speaker speaker, int id)
	{
		super();
		this.lecture = lecture;
		this.speaker = speaker;
		this.idExtract = id;
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

	public int getIdExtract()
	{
		return idExtract;
	}

	public void setIdExtract(int idExtract)
	{
		this.idExtract = idExtract;
	}

}
