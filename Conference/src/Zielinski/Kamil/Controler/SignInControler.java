package Zielinski.Kamil.Controler;

import java.io.IOException;
import java.sql.SQLException;

import Zielinski.Kamil.Model.DBConnector;
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

	public void acceptClick() throws SQLException, IOException
	{
		String name = nameField.getText();
		String mail = surnameField.getText();
		String pass1 = passField1.getText();
		if (name.equals("")||mail.equals("")||pass1.equals(""))
		{
			String message = "Blank fields";
			notificationLabel.setText(message);
			return;
		}
		DBConnector con = new DBConnector();
		if (!con.getUserPassword(name).equals(""))
		{
			String message = "User "+name+" already exists";
			notificationLabel.setText(message);
			return;
		}
		con.addUser(name, pass1, mail);
		this.exit();
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
