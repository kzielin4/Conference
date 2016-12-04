package Zielinski.Kamil.Model;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.ToolTipManager;

import Zielinski.Kamil.Model.Lecture.LectureType;
import Zielinski.Kamil.Model.Session.SessionType;

public class DBConnector
{
	private java.sql.Connection connection;

	public DBConnector()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String Url = "jdbc:MySql://localhost:3310/mydb?autoReconnect=true&useSSL=false";
			connection = DriverManager.getConnection(Url, "root", "root");
		}
		catch (Exception ex)
		{
			// TODO
			// TU BY SIE PRZYDALO WYRABANIE PROGRAMU I jaki WARRNINGOWE OKIENKO
			System.out.println(ex);
		}
	}

	public ResultSet executeQuery(String query) throws SQLException
	{
		java.sql.Statement stmt = null;
		Savepoint sp = connection.setSavepoint();
		ResultSet rset = null;
		try
		{
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			return rset;
		}
	}

	public Session getSession(int id) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM session where idSession=" + id + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		Session tempSession = null;
		/*try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				int idx = rset.getInt(1);
				String name = rset.getString(2);
				Timestamp timestamp1 = rset.getTimestamp(3);
				Timestamp timestamp2 = rset.getTimestamp(4);
				System.out.println("ID: " + name + "    " + timestamp1 + "    " + timestamp1);
				tempSession = new Session(idx, null, timestamp1, timestamp2, name);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
			return tempSession;
		}
		*/
		return tempSession;
	}

	public Speaker getSpeaker(int id) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM speaker where idSpeaker=" + id + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		Speaker tempSpeaker = null;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				String firstName = rset.getString(2);
				String secoundName = rset.getString(3);
				Timestamp arrivalDate = rset.getTimestamp(4);
				Timestamp departureDate = rset.getTimestamp(5);
				System.out.println(
						"ID: " + firstName + "    " + secoundName + "    " + arrivalDate + "    " + departureDate);
				//tempSpeaker = new Speaker(firstName, secoundName, arrivalDate, departureDate);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
		}
		return tempSpeaker;
	}

	public Lecture getLecture(int id) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM lecture where idLecture=" + id + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		Lecture tempLecture = null;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				// String firstName = rset.getString(2);
				// String secoundName = rset.getString(3);
				// Timestamp arrivalDate = rset.getTimestamp(4);
				// Timestamp departureDate= rset.getTimestamp(5);
				// System.out.println("ID: " + firstName + " " + secoundName + "
				// "+ arrivalDate + " "+departureDate);
				// tempLecture = new Lecture(idLecture, thema, speakerNumber,
				// sessionNumber);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			connection.commit();
		}
		return tempLecture;
	}
    
	public int addSession(Session session) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into session (idSession,sessionName,timeStart,timeEnd,sessionType,idConference)" + " values (?, ?, ?, ?,?,?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, session.getIdSession());
			insert.setString(2, session.getSessionName());
			insert.setTimestamp(3, session.getBeginDate());
			insert.setTimestamp(4, session.getEndDate());
			if (session.getType() ==SessionType.PLENARY) 
				insert.setString(5,"P");
			else
				insert.setString(5,"N");
			insert.setInt(6, 0);
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			System.out.println("Rekord dodany");
			insert.close();
		}
		return isADD;

	}
	public int addUser(String name, String pass , String mail) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into users (userName,userPassword,userMail)" + " values (?,?,?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setString(1, name);
			insert.setString(2, pass);
			insert.setString(3, mail);
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			System.out.println("Rekord dodany");
			insert.close();
		}
		return isADD;

	}
	public String getUserPassword(String nick) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.users where userName like '" + nick + "';";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		String passwd="";
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				passwd = rset.getString(2);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return passwd;
	}
	public int addSpeaker(Extract extract) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into speaker (idSpeaker,firstAndSecondName,arrivalDate,departureDate,idConference)"
				+ " values (?, ?, ?, ?, ?);";
		Savepoint sp = connection.setSavepoint();
		Speaker speaker = extract.getSpeaker();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, extract.getIdExtract());
			insert.setString(2, speaker.getFirstAndSecondName());
			insert.setTimestamp(3, speaker.getArrivalDate());
			insert.setTimestamp(4, speaker.getDepartureDate());
			insert.setInt(5, 0);
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
			System.out.println("Rekord dodany");
			insert.close();
		}
		return isADD;
	}
	
	public int addLecutre(Extract extract,int idSession,int number) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into lecture (idLecture, idSpeaker,idSession,lectureType,thema,abstract,kw1,kw2,kw3,idConference,numberIn)"
				+ " values (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, extract.getIdExtract());
			insert.setInt(2, extract.getIdExtract());
			insert.setInt(3, idSession);
			if (extract.getLecture().getType() == LectureType.P) 
				insert.setString(4,"P");
			else
				insert.setString(4,"N");
			insert.setString(5, extract.getLecture().getThema());
			insert.setString(6, extract.getLecture().getAbstractLecture());
			insert.setInt(7,extract.getKw1());
			insert.setInt(8,extract.getKw2());
			insert.setInt(9,extract.getKw3());
			insert.setInt(10, 0);
			insert.setInt(11, number);
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
			System.out.println("Rekord dodany");
			insert.close();
		}
		return isADD;
	}
	public ArrayList<Lecture> getAllLectureFromSession(int idx) throws SQLException
	{
		ArrayList<Lecture> lectures = new ArrayList<Lecture>();
		// TODO
		// dowalic while od getlecture i dodajemy do listy i zwracamy liste
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM lecture where idSession=" + idx + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				// String firstName = rset.getString(2);
				// String secoundName = rset.getString(3);
				// Timestamp arrivalDate = rset.getTimestamp(4);
				// Timestamp departureDate= rset.getTimestamp(5);
				// System.out.println("ID: " + firstName + " " + secoundName + "
				// "+ arrivalDate + " "+departureDate);
				// tempLecture = new Lecture(idLecture, thema, speakerNumber,
				// sessionNumber);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
			return lectures;
		}

	}
	public boolean isConferenceExists(int idConf) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.confernce where idConference = " + idConf + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		boolean isExist = false;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				isExist = true;
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return isExist;
	}
	public int addConference(int id , String name, Timestamp start, Timestamp end) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into conference (idConference,conferenceName,timeStart,timeEnd)"
				+ " values (?, ?, ?, ?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1,id);
			insert.setString(2, name);
			insert.setTimestamp(3, start);
			insert.setTimestamp(4, end);
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
			connection.rollback(sp);
		}
		finally
		{
			System.out.println("Rekord dodany");
			insert.close();
		}
		return isADD;
	}
	

}
