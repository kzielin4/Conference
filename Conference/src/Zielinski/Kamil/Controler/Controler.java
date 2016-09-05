package Zielinski.Kamil.Controler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controler extends Application
{
	private Button button;

	public void click()
	{
		System.out.println("Kliknalem");
	}

	@Override
	public void start(Stage primaryStage)
	{

	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
