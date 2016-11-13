package Zielinski.Kamil.Controler;

import java.io.IOException;

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

public class SignInControler
{
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField surnameField;
	@FXML
	private PasswordField passField1;
	@FXML
	private PasswordField passField2;
	@FXML
	private Label notificationLabel;

	public void acceptClick()
	{
		String name = nameField.getText();
		String surname = surnameField.getText();
		String pass1 = passField1.getText();
		String pass2 = passField2.getText();
		String massage = "Empty: ";
		if (name == "")
		{
			massage = massage + "Name ";
		}
		if (surname == "")
		{
			massage = massage + "Surname ";
		}
		if (pass1 == "" || pass2 == "")
		{
			massage = massage + "Password ";
		}
		notificationLabel.setText(massage);
	}
	
    public void exit() throws IOException
    {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("LoginWindow.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.initStyle(StageStyle.UNDECORATED);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
    }
}
