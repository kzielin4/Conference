package Zielinski.Kamil.Controler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.itextpdf.text.DocumentException;

import Zielinski.Kamil.Model.Categories;
import Zielinski.Kamil.Model.Conference;
import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.Extract;
import Zielinski.Kamil.Model.ExtractLoader;
import Zielinski.Kamil.Model.MailSender;
import Zielinski.Kamil.Model.NormalLectureScheduler;
import Zielinski.Kamil.Model.PlenaryLectureScheduler;
import Zielinski.Kamil.Model.TimetableSkeleton;
import Zielinski.Kamil.Model.TimetableSkeletonLoader;
import Zielinski.Kamil.Model.MyLogger;
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
	@FXML
	private Label label1;
    private boolean isRUN;
    private Stage stage;
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
							isRUN = true;
							stage = setLoadingStage();
						}
						catch (IOException e)
						{
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
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					loadExtracts();
					isRUN = false;
					th.destroy();
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
		while (isRUN)
		{
			
		}
		stage.close();
		System.out.println("I co teraz !!!!!!!!!!!!!!!!!!!!!!");
	}

	public void loadExtracts() throws IOException, DocumentException, SQLException, InterruptedException
	{
		MyLogger logger = new MyLogger();
		System.out.println("Wczytaj");
		// ExtractLoader extractLoader = new ExtractLoader();
		// extractLoader.executeLoading();
		ArrayList<Extract> extracts = extractLoader.loadExtracts();
		if(extracts ==null)
		{
			System.out.println("ex");
			return;
		}
		conference.setExtracts(extracts);
		// TimetableSkeleton sk
		// =timetableSkeletonLoader.loadTimetableSkeleton();
		TimetableSkeleton sk = timetableSkeletonLoader.loadTimetableSkeleton();
		if (sk == null)
		{
			System.out.println("sk");
			return;
		}
		System.out.println("CONF: " + conference.countNormalLecture());
		System.out.println("ILOŒÆ:  " + sk.countMaxNormalLectureInUnits());
		if (conference.countNormalLecture() > sk.countMaxNormalLectureInUnits()
				|| conference.countPlenaryLecture() > sk.countMaxPlenaryLectureInUnits())
		{
			logger.writeError("Not enough session units to assigned all lectures");
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
		//plenaryScheduler.printAssigned();
		//conf.printSessionAssigned();
		//conf.writeToCSVFile();
		//conf.writeToPDF();
		//conf.writeToDB();
		//conf.writeTOICS();
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
		try
		{
			stage=setDBView();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("USER: "+Config.getUsername());
	
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

	public Stage setLoadingStage() throws IOException
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
		return  logScene;
	}
	
	public Stage setDBView() throws IOException
	{
		System.out.println("loading...");
		exitMainView();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("DBView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		return  logScene;
	}

	public void exitMainView()
	{
		Stage stage = (Stage) dbButton.getScene().getWindow();
		stage.close();
	}

	public void exitloading()
	{
		stage.close();
	}
}
