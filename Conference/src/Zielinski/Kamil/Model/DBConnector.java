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

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

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
				// tempSpeaker = new Speaker(firstName, secoundName,
				// arrivalDate, departureDate);
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

	public Extract getExtract(int idConf, int idSes, int idLec) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM lecture where idConference=" + idConf + " and idSession=" + idSes + " and idLecture=" + idLec + ";";
		String selectString2 = "SELECT * FROM speaker where idConference=" + idConf +" and idSpeaker=" + idLec + ";";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		Lecture tempLecture = null;
		Speaker tempSpeaker =null;
		int kw1 = 0;
		int kw2 = 0;
		int kw3 = 0;
		int numberIn = 0;
		Extract extract = null;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				String type = rset.getString(5);
				if(type.equals("P"))
					tempLecture = new Lecture(LectureType.P, rset.getString(6), rset.getString(7));
				else
					tempLecture = new Lecture(LectureType.N, rset.getString(6), rset.getString(7));
				kw1 = rset.getInt(8);
				kw2 = rset.getInt(9);
				kw3 = rset.getInt(10);
				numberIn =rset.getInt(12);
			}
			else
			{
				return null;
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		Savepoint sp2 = connection.setSavepoint();
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString2);
			if (rset.next())
			{
				tempSpeaker = new Speaker(rset.getString(2), rset.getTimestamp(3), rset.getTimestamp(4));
			}
			else
			{
				return null;
			}
			extract = new Extract(tempLecture, tempSpeaker, idLec, kw1, kw2, kw3);
			extract.setNumberInSession(numberIn);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp2);
		}
		return extract;
	}

	public int addSession(Session session, int idConf) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into session (idSession,sessionName,timeStart,timeEnd,sessionType,idConference)"
				+ " values (?, ?, ?, ?,?,?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, session.getIdSession());
			insert.setString(2, session.getSessionName());
			insert.setTimestamp(3, session.getBeginDate());
			insert.setTimestamp(4, session.getEndDate());
			if (session.getType() == SessionType.PLENARY)
				insert.setString(5, "P");
			else
				insert.setString(5, "N");
			insert.setInt(6, idConf);
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

	public int addUser(String name, String pass, String mail) throws SQLException
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
		String selectString = "SELECT * FROM mydb.users where userName = '" + nick + "';";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		String passwd = "";
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
	
	public String getUserMail(String nick) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.users where userName like '" + nick + "';";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		String mail = "";
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			if (rset.next())
			{
				mail = rset.getString(3);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return mail;
	}
	
	public ArrayList<String> getUsersList() throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.users;";
		System.out.println(selectString);
		Savepoint sp = connection.setSavepoint();
		ArrayList<String> userList = new ArrayList<String>();
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				userList.add(rset.getString(1));
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return userList;
	}
	public int addSpeaker(Extract extract, int idConf) throws SQLException
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
			insert.setInt(5, idConf);
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

	public int addCategory(Category category, int idConf) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into category (idCategory,categoryName,idConference)" + " values (?, ?, ?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, category.getIdCategory());
			insert.setString(2, category.getName());
			insert.setInt(3, idConf);
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

	public int addLecutre(Extract extract, int idSession, int number, int idConf) throws SQLException
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
				insert.setString(4, "P");
			else
				insert.setString(4, "N");
			insert.setString(5, extract.getLecture().getThema());
			insert.setString(6, extract.getLecture().getAbstractLecture());
			insert.setInt(7, extract.getKw1());
			insert.setInt(8, extract.getKw2());
			insert.setInt(9, extract.getKw3());
			insert.setInt(10, idConf);
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

	public ArrayList<Lecture> getAllLectureFromSession(int idSes, int idConf) throws SQLException
	{
		ArrayList<Lecture> lectures = new ArrayList<Lecture>();
		// TODO
		// dowalic while od getlecture i dodajemy do listy i zwracamy liste
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM lecture where idSession=" + idSes + " and idConference= " + idConf + ";";
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
		String selectString = "SELECT * FROM mydb.conference where idConference = " + idConf + ";";
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

	public boolean isSpeakerExist(int idConf, int idSpeaker) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.speaker where idConference = " + idConf + " and idSpeaker = "
				+ idSpeaker + ";";
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

	public boolean isLectureExist(int idConf, int idLecture) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.lecture where idConference = " + idConf + " and idLecture = "
				+ idLecture + ";";
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

	public boolean isSessionExist(int idConf, int idSession) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.session where idConference = " + idConf + " and idSession = "
				+ idSession + ";";
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

	public int addConference(int id, String name, Timestamp start, Timestamp end) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into conference (idConference,conferenceName,timeStart,timeEnd,userName)"
				+ " values (?, ?, ?, ?,?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, id);
			insert.setString(2, name);
			insert.setTimestamp(3, start);
			insert.setTimestamp(4, end);
			insert.setString(5, Config.getUsername());
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

	public int getAvaliableConferenceID()
	{
		int i = 0;
		try
		{
			while (isConferenceExists(i))
			{
				++i;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return i;
	}

	public ArrayList<Integer> getConferenceIdList() throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.conference where userName = '" +Config.getUsername()+"';";
		System.out.println(selectString);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		Savepoint sp = connection.setSavepoint();
		boolean isExist = false;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				idList.add(new Integer(rset.getInt(1)));
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return idList;
	}

	public ArrayList<Integer> getSessionIdList(int idConf) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.session where idConference = " + idConf + ";";
		System.out.println(selectString);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		Savepoint sp = connection.setSavepoint();
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				idList.add(new Integer(rset.getInt(1)));
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return idList;
	}

	public Session getSession(int idConf, int idSes) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.session where idConference = " + idConf + " and idSession=" + idSes
				+ ";";
		String name = null;
		Savepoint sp = connection.setSavepoint();
		Session session = null;
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				String type = rset.getString(5);
				if(type.equals("P"))
					session = new Session(rset.getInt(1), rset.getTimestamp(3), rset.getTimestamp(4), SessionType.PLENARY, rset.getString(2));
				else
					session = new Session(rset.getInt(1), rset.getTimestamp(3), rset.getTimestamp(4), SessionType.SESSION, rset.getString(2));
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return session;
	}

	public ArrayList<Integer> getLectureIdList(int idConf, int idSession) throws SQLException
	{
		java.sql.Statement stmt = null;
		String selectString = "SELECT * FROM mydb.lecture where idConference = " + idConf + " and idSession="
				+ idSession + ";";
		System.out.println(selectString);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		Savepoint sp = connection.setSavepoint();
		try
		{
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(selectString);
			while (rset.next())
			{
				idList.add(new Integer(rset.getInt(1)));
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
			connection.rollback(sp);
		}
		return idList;
	}
}
