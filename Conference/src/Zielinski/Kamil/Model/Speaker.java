package Zielinski.Kamil.Model;
/**
 * 
 */
import javax.xml.crypto.Data;

public class Speaker
{
	private int idSpeaker;
	private String firstName;
	private String secoundName;
	private Data arrivalDate;
	private Data departureDate;

	public Speaker(String firstName, String secoundName, Data arrivalDate, Data departureDate)
	{
		super();
		this.firstName = firstName;
		this.secoundName = secoundName;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSecoundName()
	{
		return secoundName;
	}

	public void setSecoundName(String secoundName)
	{
		this.secoundName = secoundName;
	}

	public Data getArrivalDate()
	{
		return arrivalDate;
	}

	public void setArrivalDate(Data arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	public Data getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(Data departureDate)
	{
		this.departureDate = departureDate;
	}

	public int getIdSpeaker()
	{
		return idSpeaker;
	}

	public void setIdSpeaker(int idSpeaker)
	{
		this.idSpeaker = idSpeaker;
	}
}
