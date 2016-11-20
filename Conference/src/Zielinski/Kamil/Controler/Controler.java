package Zielinski.Kamil.Controler;

import java.awt.Dialog;
import java.io.IOException;

import Zielinski.Kamil.Model.Categories;
import Zielinski.Kamil.Model.Conference;
import Zielinski.Kamil.Model.ExtractLoader;
import Zielinski.Kamil.Model.NormalLectureScheduler;
import Zielinski.Kamil.Model.PlenaryLectureScheduler;
import Zielinski.Kamil.Model.TimetableSkeleton;
import Zielinski.Kamil.Model.TimetableSkeletonLoader;
import Zielinski.Kamil.View.LogStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controler
{
	private Button buttonValidate;
	private ExtractLoader extractLoader;
	private TimetableSkeletonLoader timetableSkeletonLoader;
	private Categories categories;
	private Conference conference;

	public Controler()
	{
		extractLoader = new ExtractLoader();
		timetableSkeletonLoader = new TimetableSkeletonLoader();
		categories = new Categories();
		conference = new Conference();
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
	}

	public void loadExtracts()
	{
		System.out.println("Wczytaj");
		// ExtractLoader extractLoader = new ExtractLoader();
		// extractLoader.executeLoading();
		conference.setExtracts(extractLoader.loadExtracts());
		TimetableSkeleton sk = new TimetableSkeleton(timetableSkeletonLoader.loadTimetableSkeleton());
		System.out.println("CONF: " + conference.countNormalLecture());
		System.out.println("ILOŒÆ:  " + sk.countMaxNormalLectureInUnits());
		if (conference.countNormalLecture() > sk.countMaxNormalLectureInUnits()
				|| conference.countPlenaryLecture() > sk.countMaxPlenaryLectureInUnits())
		{
			// tu jakiœ b³¹d
			// loger plus alert
			System.out.println("Za malo unitow sesji");
			return;
		}
		conference.setTimetableSkeleton(sk);
		conference.initNormalSessions();
		conference.initPlenarySessions();
		categories.setCategories(categories.loadCategories());
		conference.setCategories(categories);
		genetic(conference);

	}

	public void genetic(Conference conf)
	{
		System.out.println(conference.sessionSize());
		NormalLectureScheduler schedul = new NormalLectureScheduler(conf);
		//schedul.runAlgorith();
		PlenaryLectureScheduler plenaryScheduler = new PlenaryLectureScheduler(conf.getPlenarySessions(), conf.getPlenaryExtracts());
		plenaryScheduler.printAssigned();
	}

	public void loadSkeleton()
	{
		// categories.loadCategories();
		// timetableSkeletonLoader.loadTimetableSkeleton();
		// TimetableSkeleton sk=new
		// TimetableSkeleton(timetableSkeletonLoader.loadTimetableSkeleton());
		TimetableSkeleton sk = new TimetableSkeleton(timetableSkeletonLoader.loadTimetableSkeleton());
		System.out.println("lol");
		System.out.println("ILOŒÆ:  " + sk.countSessionUnits());
	}

	public void loadCategories()
	{
		categories.loadCategories();
	}

	public void showAlert(String title, String value)
	{
		/*AnchorPane ap;
		Dialogs.create()
        .owner((Stage) ap.getScene().getWindow())
        .title("Error Dialog")
        .masthead("Look, an Error Dialog")
        .message("Ooops, there was an error!")
        .showError();*/
	}

}
