package Zielinski.Kamil.Controler;

import java.util.logging.Level;
import java.util.logging.Logger;

import Zielinski.Kamil.View.LogStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{

	/**
	 * @param args
	 *            the command line arguments
	 */
	private Controler contoler;
	public static void main(String[] args)
	{
		
		Application.launch(Main.class, (java.lang.String[]) null);
	}

	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("LoginWindow.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			primaryStage.setResizable(false);
		}
		catch (Exception ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
