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
	private int kw1;
	private int kw2;
	private int kw3;
	private int sessionId;

	public Extract(Lecture lecture, Speaker speaker, int id ,int kw1, int kw2, int kw3)
	{
		super();
		this.lecture = lecture;
		this.speaker = speaker;
		this.idExtract = id;
		this.kw1=kw1;
		this.kw2=kw2;
		this.kw3=kw3;
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

	public int getKw1()
	{
		return kw1;
	}

	public int getKw2()
	{
		return kw2;
	}

	public int getKw3()
	{
		return kw3;
	}

	public int getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(int sessionId)
	{
		this.sessionId = sessionId;
	}

}
