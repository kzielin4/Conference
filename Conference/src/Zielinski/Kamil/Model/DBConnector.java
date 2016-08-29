package Zielinski.Kamil.Model;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;

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
			System.out.println("Wyjatek");
			connection.rollback(sp);
		}
		finally
		{
			// connection.commit();
			return tempSession;
		}
	}

	public Speaker getSpeaker(int id)
	{
		return null;

	}

	public Lecture getLecture(int id)
	{
		return null;

	}

	public static void main(String[] args) throws SQLException
	{
		DBConnector con = new DBConnector();
		con.getSession(1);
	}

}
