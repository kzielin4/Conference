package Zielinski.Kamil.Model;
import java.sql.Timestamp;

/**
 * 
 */
import javax.xml.crypto.Data;

public class Speaker
{
	private int idSpeaker;
	private String firstName;
	private String secondName;
	private Timestamp arrivalDate;
	private Timestamp departureDate;

	public Speaker(String firstName, String secoundName, Timestamp arrivalDate, Timestamp departureDate)
	{
		super();
		this.firstName = firstName;
		this.secondName = secoundName;
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

	public String getSecondName()
	{
		return secondName;
	}

	public void setSecondName(String secoundName)
	{
		this.secondName = secoundName;
	}

	public Timestamp getArrivalDate()
	{
		return arrivalDate;
	}

	public void setArrivalDate(Timestamp arrivalDate)
	{
		this.arrivalDate = arrivalDate;
	}

	public Timestamp getDepartureDate()
	{
		return departureDate;
	}

	public void setDepartureDate(Timestamp departureDate)
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
