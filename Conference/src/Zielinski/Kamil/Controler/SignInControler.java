package Zielinski.Kamil.Controler;

import java.io.IOException;
import java.sql.SQLException;

import Zielinski.Kamil.Model.CoderBase64;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.MyLogger;
import Zielinski.Kamil.Model.Validator;
import Zielinski.Kamil.View.LogStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

	public void acceptClick() throws Exception
	{
		String name = nameField.getText();
		String mail = surnameField.getText();
		String pass1 = passField1.getText();
		Validator validator = new Validator();
		MyLogger logger = new MyLogger();
		if (name.equals("") || mail.equals("") || pass1.equals(""))
		{
			logger.writeError("Blanks filed in sign in");
			setErrorDialog("Blank fields");
			return;
		}
		DBConnector con = new DBConnector();
		if (!con.getUserPassword(name).equals(""))
		{
			logger.writeError("User exists " + name + "can not add dupliacte user to database");
			setErrorDialog("User with given nick  allready exists");
			return;
		}
		if (!validator.isPasswordValid(pass1))
		{
			logger.writeError("Password does not fulfill security requirements, password should must containt min 8 signs, at least 1 lowercase ,1 uppercase and 1 specialcase");
			setErrorDialog("Password does not fulfill security requirements");
			return;
		}
		CoderBase64 coder = new CoderBase64();
		String code = coder.encrypt(pass1);
		System.out.println(code);
		con.addUser(name, code, mail);
		setInfoDialog("User added", "User correct added to system");
		logger.writeInfo("User "+name+"correct added to system");
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

	public void setErrorDialog(String message)
	{
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.ERROR);
		alert.setTitle("Error Window");
		alert.setHeaderText(message);
		alert.setContentText("Look in to log file to get more details");
		alert.showAndWait();
		return;
	}

	public void setInfoDialog(String message, String detials)
	{
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		alert.setTitle("INFO");
		alert.setHeaderText(message);
		alert.setContentText(detials);
		alert.showAndWait();
		return;
	}
}
