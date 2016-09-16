package Zielinski.Kamil.Controler;

import java.io.IOException;

import Zielinski.Kamil.Model.ExtractLoader;
import Zielinski.Kamil.View.LogStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controler
{
	private Button buttonValidate;
	private ExtractLoader extractLoader;

	public Controler()
	{
		extractLoader = new ExtractLoader();
	}

	public void click() throws IOException
	{
		System.out.println("Click");
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(Main.class.getResource("LogWindow.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.setTitle("LogWindow");
		logScene.show();
		logScene.setResizable(false);
		Stage stage= new Stage(StageStyle.UNDECORATED);
		stage.initModality(Modality.APPLICATION_MODAL);
		//stage = new LogStage(page, StageStyle.UNDECORATED);
		Pane pan1 = (Pane) FXMLLoader.load(LogStage.class.getResource("NotView.fxml"));
		Scene scene1 = new Scene(pan1);
		//stage = new LogStage(scene1, StageStyle.UNDECORATED);
        stage.setScene(scene1);
        //stage.setSize(200, 100);
        ////stage.setAlwaysOnTop(true);
        ///stage.setLocation(1100.0,300.0);
        stage.show();
	}

	public void loadExtracts()
	{
		System.out.println("Wczytaj");
		// ExtractLoader extractLoader = new ExtractLoader();
		extractLoader.executeLoading();

	}

}
