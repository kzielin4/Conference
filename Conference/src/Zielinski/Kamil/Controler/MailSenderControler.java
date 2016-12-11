package Zielinski.Kamil.Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.MailSender;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MailSenderControler
{
	@FXML
	private ChoiceBox<String> choiseUserBox;
	@FXML
	private Button sentMailButton;
	@FXML
	private Button returnButton;

	public void initialize()
	{
		addUsers();
	}

	private void addUsers()
	{
		DBConnector con = new DBConnector();
		choiseUserBox.getItems().clear();
		try
		{
			ArrayList<String> listUsers = con.getUsersList();
			int number = 0;
			for (String name : listUsers)
			{
				choiseUserBox.getItems().add(name);
				++number;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void quit()
	{
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}
	
	public void sent()
	{
		MailSender mailSender = new MailSender();
		if (choiseUserBox.getValue() == null)
			return;
		DBConnector con = new DBConnector();
		sentMailButton.setDisable(true);
		returnButton.setDisable(true);
		try
		{
			String mail = con.getUserMail(choiseUserBox.getValue());
			mailSender.sentMail(mail,Config.getFileName());
			sentMailButton.setDisable(false);
			returnButton.setDisable(false);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
