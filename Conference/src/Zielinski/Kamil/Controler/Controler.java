package Zielinski.Kamil.Controler;

import java.io.IOException;

import Zielinski.Kamil.Model.ExtractLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controler
{
	private Button buttonValidate;
	// private Stage logScene;
	public void click() throws IOException
	{
		System.out.println("Click");
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(Main.class.getResource("LogWindow.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.setTitle("LogWindow");
		logScene.showAndWait();
		logScene.setResizable(false);
	}

	public void loadExtracts()
	{
        System.out.println("Wczytaj");
		ExtractLoader extractLoader = new ExtractLoader();
        extractLoader.executeLoading();
	}

}
