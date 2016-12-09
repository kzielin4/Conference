package Zielinski.Kamil.Model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class ICSWritter
{
	private BufferedWriter writer;
	private Calendar calendar;

	public ICSWritter(String file) throws IOException
	{

		writer = new BufferedWriter(new FileWriter(file));
		calendar = new net.fortuna.ical4j.model.Calendar();
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

	}

	public void addEvenet(long start, long end, String name, String desc,int id) throws ParseException, SocketException 
	{

		VEvent event = new VEvent(new DateTime(start), new DateTime(end), name);
		UidGenerator ug = new UidGenerator(""+id);
		event.getProperties().add(ug.generateUid());
		event.getProperties().add(new Description(desc));
		calendar.getComponents().add(event);
	}
    public void print()
    {
    	System.out.println(calendar);
    }
	public void write() throws IOException
	{
		writer.write(calendar.toString());
	}

	public void close() throws IOException
	{
		writer.close();
	}
}
