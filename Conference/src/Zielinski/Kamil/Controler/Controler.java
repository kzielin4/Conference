package Zielinski.Kamil.Controler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.itextpdf.text.DocumentException;

import Zielinski.Kamil.Model.Categories;
import Zielinski.Kamil.Model.Conference;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.ExtractLoader;
import Zielinski.Kamil.Model.NormalLectureScheduler;
import Zielinski.Kamil.Model.PlenaryLectureScheduler;
import Zielinski.Kamil.Model.TimetableSkeleton;
import Zielinski.Kamil.Model.TimetableSkeletonLoader;
import Zielinski.Kamil.View.LogStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
	@FXML
	private Button dbButton;
	@FXML
	private ImageView LoadIMG;

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

	public void createConference()
	{
		Task task = new Task<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							setLoadingStage();
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		new Thread(new Runnable() {
		    public void run() {
		       try
			{
				loadExtracts();
				exitloading();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (DocumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		}).start();
	}

	public void loadExtracts() throws IOException, DocumentException, SQLException, InterruptedException
	{
		System.out.println("Wczytaj");
		// ExtractLoader extractLoader = new ExtractLoader();
		// extractLoader.executeLoading();
		conference.setExtracts(extractLoader.loadExtracts());
		TimetableSkeleton sk = new TimetableSkeleton(timetableSkeletonLoader.loadTimetableSkeleton());
		System.out.println("CONF: " + conference.countNormalLecture());
		System.out.println("ILO��:  " + sk.countMaxNormalLectureInUnits());
		if (conference.countNormalLecture() > sk.countMaxNormalLectureInUnits()
				|| conference.countPlenaryLecture() > sk.countMaxPlenaryLectureInUnits())
		{
			// tu jaki� b��d
			// loger plus alert
			System.out.println("Za malo unitow sesji");
			return;
		}
		conference.setTimetableSkeleton(sk);
		conference.initNormalSessions();
		conference.initPlenarySessions();
		categories.setCategories(categories.loadCategories());
		conference.setCategories(categories);
		runAlgorithms(conference);

	}
	public void runAlgorithms(Conference conf) throws IOException, DocumentException, SQLException
	{
		System.out.println(conference.sessionSize());
		NormalLectureScheduler schedul = new NormalLectureScheduler(conf);
		schedul.runAlgorith();
		conference.setSessionByNormalScheduler(schedul.findBestIndividual().getSessions());
		PlenaryLectureScheduler plenaryScheduler = new PlenaryLectureScheduler(conf.getPlenarySessions(),
				conf.getPlenaryExtracts());
		plenaryScheduler.printAssigned();
		conf.printSessionAssigned();
		conf.writeToCSVFile();
		conf.writeToPDF();
		// conf.writeToDB();
	}

	public void loadSkeleton()
	{
		// categories.loadCategories();
		// timetableSkeletonLoader.loadTimetableSkeleton();
		// TimetableSkeleton sk=new
		// TimetableSkeleton(timetableSkeletonLoader.loadTimetableSkeleton());
		DBConnector con = new DBConnector();
	}

	public void loadCategories()
	{
	}

	public void showAlert(String title, String value)
	{
		/*
		 * AnchorPane ap; Dialogs.create() .owner((Stage)
		 * ap.getScene().getWindow()) .title("Error Dialog") .masthead(
		 * "Look, an Error Dialog") .message("Ooops, there was an error!")
		 * .showError();
		 */
	}

	public void setLoadingStage() throws IOException
	{
		System.out.println("loading...");
		exitMainView();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("LoadingView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.initStyle(StageStyle.UNDECORATED);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
	}

	public void exitMainView()
	{
		Stage stage = (Stage) dbButton.getScene().getWindow();
		stage.close();
	}
	public void exitloading()
	{
		Stage stage = (Stage) LoadIMG.getScene().getWindow();
		stage.close();
	}
}
