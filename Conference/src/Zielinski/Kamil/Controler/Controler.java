package Zielinski.Kamil.Controler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.omg.CosNaming.IstringHelper;

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
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controler
{
	private Button buttonValidate;
	private ExtractLoader extractLoader;
	private TimetableSkeletonLoader timetableSkeletonLoader;
	private Categories categories;
	private volatile Conference conference;
	@FXML
	private Button dbButton;
	@FXML
	private Button confButton;
	@FXML
	private ImageView LoadIMG;
	@FXML
	private Label label1;
	private volatile boolean isRUN;
	private volatile Stage stage;

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
		confButton.setDisable(true);
		dbButton.setDisable(true);
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
							setLoadingStage();
							System.out.println("Stage1: " + stage);
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
		Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					isRUN = true;
					loadExtracts();
					isRUN = false;
					exitloading();
					System.out.println("Stage2: " + stage.toString());
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
		});
		thread.start();
		Thread thread2 = new Thread(new Runnable()
		{
			public void run()
			{

				Task<Void> task = new Task<Void>()
				{

					@Override
					protected Void call() throws Exception
					{

						Platform.runLater(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									setExportStage();
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
				while (isRUN)
				{
				}
				th.start();
			}
		});
		thread2.start();
	}

	public void loadExtracts() throws IOException, DocumentException, SQLException, InterruptedException
	{
		MyLogger logger = new MyLogger();
		System.out.println("Wczytaj");
		ArrayList<Extract> extracts = extractLoader.loadExtracts();
		if (extracts == null)
		{
			System.out.println("ex");
			return;
		}
		conference.setExtracts(extracts);
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
		conference = conf;
		Config.setConference(conf);
		// plenaryScheduler.printAssigned();
		// conf.printSessionAssigned();
		// conf.writeToCSVFile();
		// conf.writeToPDF();
		// conf.writeToDB();
		// conf.writeTOICS();
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
			stage = setDBView();
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("USER: " + Config.getUsername());

	}

	public void choosePDF()
	{
		Config.setExportType(2);
		try
		{
			setExportTypeView();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void chooseCSV()
	{
		Config.setExportType(1);
		try
		{
			setExportTypeView();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void chooseICS()
	{
		Config.setExportType(3);
		try
		{
			setExportTypeView();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void chooseDB()
	{
		try
		{
			setDatabaseExportView();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLoadingStage() throws IOException
	{

		System.out.println("loading...");
		exitMainView();
		Stage logScene = new Stage();
		Pane page;
		page = (Pane) FXMLLoader.load(LogStage.class.getResource("LoadingView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		stage = logScene;

	}

	public Stage setDBView() throws IOException
	{
		exitMainView();
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("DBView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		return logScene;
	}
	
	public Stage setDBView2() throws IOException
	{
		Stage logScene = new Stage();
		Pane page = (Pane) FXMLLoader.load(LogStage.class.getResource("DBView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		return logScene;
	}

	public void setExportStage() throws IOException
	{
		exitMainView();
		Stage logScene = new Stage();
		Pane page;
		page = (Pane) FXMLLoader.load(LogStage.class.getResource("ExportView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		stage=logScene;
	}
	
	public void setDatabaseExportView() throws IOException
	{
		Stage logScene = new Stage();
		Pane page;
		page = (Pane) FXMLLoader.load(LogStage.class.getResource("DatabaseExporterView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		stage=logScene;
	}

	public void exitMainView()
	{
		Stage stage = (Stage) dbButton.getScene().getWindow();
		stage.close();
	}

	public void setStage()
	{
		stage = (Stage) dbButton.getScene().getWindow();
	}

	public void exitloading()
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				stage.close();
			}
		});
	}
	
	public void setExportTypeView() throws IOException
	{
		Stage logScene = new Stage();
		Pane page;
		page = (Pane) FXMLLoader.load(LogStage.class.getResource("ExportTypeView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		stage=logScene;
		
	}
	
	
	public void setExportMailView() throws IOException
	{
		Stage logScene = new Stage();
		Pane page;
		page = (Pane) FXMLLoader.load(LogStage.class.getResource("ExportMailView.fxml"));
		Scene scene = new Scene(page);
		logScene.setScene(scene);
		logScene.setResizable(false);
		logScene.initModality(Modality.APPLICATION_MODAL);
		logScene.show();
		
	}
	
	public void writeFile() 
	{
		int type=Config.getExportType();
		if(type==1)
		{
			try
			{
				Config.getConference().writeToCSVFile();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type==2)
		{
			try
			{
				try
				{
					Config.getConference().writeToPDF();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (DocumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type==3)
		{
			
			try
			{
				Config.getConference().writeTOICS();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		//stage.close();
	}
	public void sentFile()
	{
		writeFile();
		try
		{
			setExportMailView();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
