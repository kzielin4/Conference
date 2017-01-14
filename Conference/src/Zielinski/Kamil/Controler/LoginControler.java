package Zielinski.Kamil.Controler;

import java.io.File;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Zielinski.Kamil.Model.CoderBase64;
import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.MyLogger;
import Zielinski.Kamil.View.LogStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginControler
{
	@FXML
	private Button cancelButton;
	@FXML
	private Button loginButton;
	@FXML
	private TextField userField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label notificationLabel;

	public void login() throws Exception
	{
		String user = userField.getText();
		String pass = passwordField.getText();
		// tu sie jak¹œ walidacje walnie
		if (passwordVeryfication(new String(user), new String(pass)))
		{
			try
			{
				Config.setUsername(user);
				MyLogger logger = new MyLogger();
				logger.writeInfo("User "+user+" successful login in to system");	
				setMainStage();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.out.println(e);
			}
		}
		else
		{
			MyLogger logger = new MyLogger();
			logger.writeInfo("Invalid Username/Password: "+user+"/"+pass);	
			showNegativeNotification();
		}
	}

	public void exit()
	{
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setMainStage() throws IOException
	{
		exit();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(Main.class.getResource("View.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
	}

	public void setSignInStage() throws IOException
	{
		exit();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("SignIn.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
	}

	public void showNegativeNotification()
	{
		notificationLabel.setText("Invalid Username/Password");
	}

	public boolean passwordVeryfication(String user, String password) throws Exception
	{
		
		DBConnector con = new DBConnector();
		CoderBase64 coder = new CoderBase64();
		String passwd = con.getUserPassword(user);getClass();
		System.out.println(passwd);
	    if (passwd.equals(""))
	    {
	    	return false;
	    }
	    passwd = coder.decrypt(passwd);
		if (passwd.equals(password))
		{
			return true;
		}
		return false;
	}
}
