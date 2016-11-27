package Zielinski.Kamil.Model;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Zielinski.Kamil.Model.Lecture.LectureType;
import Zielinski.Kamil.Model.Session.SessionType;
import Zielinski.Kamil.Model.TimetableSkeletonLoader.EventType;

public class Conference
{

	private TimetableSkeleton timetableSkeleton;
	private ArrayList<Session> sessions;
	private ArrayList<Extract> extracts;
	private boolean isComplited;
	private Categories categories;

	public TimetableSkeleton getTimetableSkeleton()
	{
		return timetableSkeleton;
	}

	public void setTimetableSkeleton(TimetableSkeleton timetableSkeleton)
	{
		this.timetableSkeleton = timetableSkeleton;
	}

	public ArrayList<Session> getSessions()
	{
		return sessions;
	}

	public ArrayList<Session> getNormalSessions()
	{
		ArrayList<Session> normalSessions = new ArrayList<Session>();
		for (Session session : sessions)
		{
			if (session.getType() == SessionType.SESSION)
			{
				normalSessions.add(session);
			}
		}
		return normalSessions;
	}

	public ArrayList<Extract> getNormalExtracts()
	{
		ArrayList<Extract> normalExtracts = new ArrayList<Extract>();
		for (Extract extract : extracts)
		{
			if (extract.getLecture().getType() == LectureType.N)
			{
				normalExtracts.add(extract);
			}
		}
		return normalExtracts;
	}

	public ArrayList<Extract> getPlenaryExtracts()
	{
		ArrayList<Extract> plenaryExtracts = new ArrayList<Extract>();
		for (Extract extract : extracts)
		{
			if (extract.getLecture().getType() == LectureType.P)
			{
				plenaryExtracts.add(extract);
			}
		}
		return plenaryExtracts;
	}

	public ArrayList<Session> getPlenarySessions()
	{
		ArrayList<Session> plenarySessions = new ArrayList<Session>();
		for (Session session : sessions)
		{
			if (session.getType() == SessionType.PLENARY)
			{
				plenarySessions.add(session);
			}
		}
		return plenarySessions;
	}

	public void setSessions(ArrayList<Session> sessions)
	{
		this.sessions = sessions;
	}

	public Session getSessionById(int id)
	{
		for (Session session : sessions)
		{
			if (session.getIdSession() == id)
			{
				return session;
			}
		}
		return null;
	}

	public Extract geExtractById(int id)
	{
		for (Extract extract : extracts)
		{
			if (extract.getIdExtract() == id)
			{
				return extract;
			}
		}
		return null;
	}

	public ArrayList<Extract> getExtracts()
	{
		return extracts;
	}

	public void setExtracts(ArrayList<Extract> extracts)
	{
		this.extracts = extracts;
	}

	public Conference()
	{
		this.timetableSkeleton = new TimetableSkeleton();
		this.sessions = new ArrayList<Session>();
		this.extracts = new ArrayList<Extract>();
		this.isComplited = false;
	}

	public Conference(TimetableSkeleton timetableSkeleton, ArrayList<Session> sessions, ArrayList<Extract> extracts)
	{
		super();
		this.timetableSkeleton = timetableSkeleton;
		this.sessions = sessions;
		this.extracts = extracts;
		this.isComplited = false;
	}

	public double rateConference()
	{
		return 0;
	}

	public boolean isComplited()
	{
		return isComplited;
	}

	public void setComplited(boolean isComplited)
	{
		this.isComplited = isComplited;
	}

	public int countNormalLecture()
	{
		int sum = 0;
		if (extracts.size() == 0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType() == LectureType.N)
				{
					sum = sum + 1;
				}
			}
		}
		return sum;
	}

	public int countPlenaryLecture()
	{
		int sum = 0;
		if (extracts.size() == 0)
		{
			return 0;
		}
		else
		{
			for (Extract extract : extracts)
			{
				if (extract.getLecture().getType() == LectureType.P)
				{
					sum = sum + 1;
				}
			}
		}
		return sum;
	}

	public void initNormalSessions()
	{

		for (TimeUnit unit : timetableSkeleton.getTimeUnits())
		{
			if (unit.getUnitType() == EventType.SESSION)
			{
				sessions.add(new Session(sessions.size(), unit.getStartTime(), unit.getEndTime(),
						unit.getMaxLectureInUnit(), SessionType.SESSION, unit.getUnitName()));
			}
		}
	}

	public void initPlenarySessions()
	{

		for (TimeUnit unit : timetableSkeleton.getTimeUnits())
		{
			if (unit.getUnitType() == EventType.PLENARY)
			{
				sessions.add(new Session(sessions.size(), unit.getStartTime(), unit.getEndTime(),
						unit.getMaxLectureInUnit(), SessionType.PLENARY, unit.getUnitName()));
			}
		}
	}

	public int sessionSize()
	{
		return sessions.size();
	}

	public long fitFunction()
	{
		long fit = 0;
		return fit;
	}

	public ArrayList<Integer> getNormalExtractIdList()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Extract extract : extracts)
		{
			if (extract.getLecture().getType() == LectureType.N)
				list.add(new Integer(extract.getIdExtract()));
		}
		return list;
	}

	public ArrayList<Boolean> getFalseList()
	{
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (Extract extract : extracts)
		{
			list.add(new Boolean(false));
		}
		return list;
	}

	public void clearId()
	{
		for (Session session : sessions)
		{
			session.getIdLectures().clear();
		}
	}

	public Timestamp getArrivalSpeakerTime(int idx)
	{
		return extracts.get(idx).getSpeaker().getArrivalDate();
	}

	public Timestamp getDepartureSpeakerTime(int idx)
	{
		return extracts.get(idx).getSpeaker().getDepartureDate();
	}

	public Categories getCategories()
	{
		return categories;
	}

	public void setCategories(Categories categories)
	{
		this.categories = categories;
	}

	public void printSessionAssigned()
	{
		int counter = 0;
		for (Session ses : sessions)
		{
			System.out.println("Sesja " + ses.getIdSession());
			for (Integer integer : ses.getIdLectures())
			{
				System.out.println("W: " + integer);
				++counter;
			}
		}
		System.out.println("Iloœæ wyk³adów przypisanych: " + counter);
	}

	public void setSessionByNormalScheduler(ArrayList<Session> normalSessions)
	{
		for (Session session : normalSessions)
		{
			sessions.remove(getSessionById(session.getIdSession()));
			sessions.add(session);
		}
	}

	public void setAssignmentInExtracts()
	{
		for (Session session : sessions)
		{
			for (Integer idExtract : session.getIdLectures())
			{

			}
		}
	}

	public void writeToCSVFile() throws IOException
	{
		String csvFile = "Output/out.csv";
		FileWriter writer = new FileWriter(csvFile);
		List<String> values = new ArrayList<String>();
		values.add("ID_LECTURE");
		values.add("TITLE");
		values.add("ABSTRACT");
		values.add("KW1");
		values.add("KW2");
		values.add("KW3");
		values.add("TYPE");
		// values.add("ARRIVAL");
		// values.add("DEPARTURE");
		values.add("SPEAKER");
		values.add("SESSION_ID");
		values.add("SESSION_NAME");
		values.add("NUMBER_IN_SESSION");
		values.add("SESSION_START");
		values.add("SESSION_END");
		CSVWritter.writeLine(writer, values);
		values.clear();
		for (Session session : sessions)
		{
			int number = 1;
			for (Integer idExtract : session.getIdLectures())
			{
				Extract tempExtract = geExtractById(idExtract.intValue());
				values.add("" + tempExtract.getIdExtract());
				values.add("" + tempExtract.getLecture().getThema());
				values.add("" + tempExtract.getLecture().getAbstractLecture());
				values.add("" + tempExtract.getKw1());
				values.add("" + tempExtract.getKw2());
				values.add("" + tempExtract.getKw3());
				values.add("" + tempExtract.getLecture().getType());
				values.add("" + tempExtract.getSpeaker().getFirstAndSecondName());
				values.add("" + session.getIdSession());
				values.add("" + session.getSessionName());
				values.add("" + number);
				values.add("" + session.getBeginDate());
				values.add("" + session.getEndDate());
				++number;
				CSVWritter.writeLine(writer, values);
				values.clear();
			}
		}
		writer.flush();
		writer.close();
	}

	public void writeToPDF() throws IOException, DocumentException
	{
		PDFWritter pdfWritter = new PDFWritter();
		pdfWritter.createPdf("HelloWorld.pdf");
		pdfWritter.write();
		ArrayList<String> values = new ArrayList<String>();
		PdfPTable table = new PdfPTable(8);
		float[] columnWidths = new float[] { 20f, 20f, 30f, 20f, 30f, 20f, 40f, 40f };
		table.setWidths(columnWidths);
		pdfWritter.addEmptyLine(3);
		values.clear();
		values.add("IDLECT");
		values.add("TYPE");
		values.add("AUTHOR");
		values.add("SESSID");
		values.add("SESSNAME");
		values.add("NUMIN");
		values.add("SESSSTART");
		values.add("SESSEND");
		pdfWritter.addCelltoTable(table, values);
		values.clear();
		for (Session session : sessions)
		{
			int number = 1;
			for (Integer idExtract : session.getIdLectures())
			{
				Extract tempExtract = geExtractById(idExtract.intValue());
				values.add("" + tempExtract.getIdExtract());
				// values.add(""+tempExtract.getLecture().getThema());
				// values.add(""+tempExtract.getLecture().getAbstractLecture());
				// values.add(""+tempExtract.getKw1());
				// values.add(""+tempExtract.getKw2());
				// values.add(""+tempExtract.getKw3());
				values.add("" + tempExtract.getLecture().getType());
				values.add("" + tempExtract.getSpeaker().getFirstAndSecondName());
				values.add("" + session.getIdSession());
				values.add("" + session.getSessionName());
				values.add("" + number);
				values.add("" + session.getBeginDate());
				values.add("" + session.getEndDate());
				++number;
				pdfWritter.addCelltoTable(table, values);
				values.clear();
			}
		}
		table.setTotalWidth(PageSize.A4.getWidth());
		table.setLockedWidth(true);
		pdfWritter.addTable(table);
		pdfWritter.close();
		System.out.println("KONIEC");
	}
}
