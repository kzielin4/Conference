package Zielinski.Kamil.Model;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.ToolTipManager;

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
		try
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
				tempSpeaker = new Speaker(firstName, secoundName, arrivalDate, departureDate);
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
			return tempSpeaker;
		}
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
			// connection.commit();
			return tempLecture;
		}

	}

	public int addSession(Session session) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into session (idSession,sessionName,timeStart,timeEnd)" + " values (?, ?, ?, ?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, session.getIdSession());
			insert.setString(2, session.getSessionName());
			insert.setTimestamp(3, session.getBeginDate());
			insert.setTimestamp(4, session.getEndDate());
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println("Duplikat klucza");
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

	public int addSpeaker(Speaker speaker) throws SQLException
	{
		PreparedStatement insert = null;
		String insertString = "insert into speaker (idSpeaker,firstName,secondName,arrivalDate,departureDate)"
				+ " values (?, ?, ?, ?, ?);";
		Savepoint sp = connection.setSavepoint();
		int isADD = 0;
		try
		{
			insert = connection.prepareCall(insertString);
			insert.setInt(1, speaker.getIdSpeaker());
			insert.setString(2, speaker.getFirstName());
			insert.setString(3, speaker.getLastName());
			insert.setTimestamp(3, speaker.getArrivalDate());
			insert.setTimestamp(4, speaker.getDepartureDate());
			insert.execute();
			isADD = 1;
		}
		catch (SQLException ex)
		{
			System.out.println("Duplikat klucza");
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

	public static void main(String[] args) throws SQLException
	{
		DBConnector con = new DBConnector();
		// do inserta
		// INSERT INTO `mydb`.`speaker` (`idSpeaker`, `firstName`, `lastName`,
		// `arrivalDate`, `departureDate`) VALUES ('1', 'Kamil', 'Zielinski',
		// '2012-01-01', '2012-01-03');
		// con.getSession(1);
		// con.getSpeaker(3);
		System.out.println("----------------------------------------------------------");
		// con.addSession(new Session(2, null, Timestamp.valueOf("2012-01-01
		// 12:12:12"), Timestamp.valueOf("2012-01-03 12:12:12"),
		// "sessionName"));
	}

}
