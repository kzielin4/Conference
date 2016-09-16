package Zielinski.Kamil.Controler;

import java.awt.Label;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

	public void pokazKomunikat()
	{
		String user = userField.getText();
		String pass = passwordField.getText();
		System.out.println(user);
		System.out.println(pass);
		//tu sie jak¹œ walidacje walnie
		if (1 == 1)
		{
			try
			{
				setMainStage();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			// showNegativeNotification();
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
		logScene.setTitle("LogWindow");
		logScene.show();
	}

	public void showNegativeNotification()
	{
		notificationLabel.setText("Invalid Username/Password");
	}
}
