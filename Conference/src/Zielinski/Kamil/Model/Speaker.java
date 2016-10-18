package Zielinski.Kamil.Model;
import java.sql.Timestamp;

/**
 * 
 */
import javax.xml.crypto.Data;

public class Speaker
{
	private int idSpeaker;
	private String firstAndSecondName;
	private Timestamp arrivalDate;
	private Timestamp departureDate;

	public Speaker(String firstAndSecondName, Timestamp arrivalDate, Timestamp departureDate)
	{
		super();
		this.firstAndSecondName = firstAndSecondName;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
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



	public String getFirstAndSecondName()
	{
		return firstAndSecondName;
	}



	public void setFirstAndSecondName(String firstAndSecondName)
	{
		this.firstAndSecondName = firstAndSecondName;
	}
}
