package Zielinski.Kamil.Controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.DataTable;
import Zielinski.Kamil.Model.Extract;
import Zielinski.Kamil.Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DBControler
{
	@FXML
	private Button confButton;
	@FXML
	private ChoiceBox<Integer> choiseConfBox;
	@FXML
	private Button sesButton;
	@FXML
	private ChoiceBox<Integer> choiseSesBox;
	@FXML
	private Button lecButton;
	@FXML
	private ChoiceBox<Integer> choiseLecBox;
	@FXML
	private TableView<DataTable> tableView;
	private Stage stage;

	public DBControler()
	{
		/*
		 * // TODO Auto-generated constructor stub DBConnector con = new
		 * DBConnector(); try { ArrayList<Integer> listConf =
		 * con.getConferenceIdList(); int number =0; for (Integer integer :
		 * listConf) { choiseConfBox.getItems().add(integer); ++number; } }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } addConf();
		 */
	}

	public void initialize()
	{
		addConferences();
	}

	public void addConferences()
	{
		DBConnector con = new DBConnector();
		choiseConfBox.getItems().clear();
		try
		{
			ArrayList<Integer> listConf = con.getConferenceIdList();
			int number = 0;
			for (Integer integer : listConf)
			{
				choiseConfBox.getItems().add(integer);
				++number;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addSessions()
	{
		stage = (Stage) confButton.getScene().getWindow() ;
		DBConnector con = new DBConnector();
		if (choiseConfBox.getValue() == null)
			return;
		int idConf = choiseConfBox.getValue().intValue();
		choiseSesBox.getItems().clear();
		choiseLecBox.getItems().clear();
		try
		{
			ArrayList<Integer> listSes = con.getSessionIdList(idConf);
			int number = 0;
			for (Integer integer : listSes)
			{
				choiseSesBox.getItems().add(integer);
				++number;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addLectures()
	{

		DBConnector con = new DBConnector();
		if (choiseConfBox.getValue() == null)
			return;
		if (choiseSesBox.getValue() == null)
			return;
		int idConf = choiseConfBox.getValue().intValue();
		int idSession = choiseSesBox.getValue().intValue();
		choiseLecBox.getItems().clear();
		try
		{
			ArrayList<Integer> listLec = con.getLectureIdList(idConf, idSession);
			System.out.println(listLec.size());
			int number = 0;
			for (Integer integer : listLec)
			{
				choiseLecBox.getItems().add(integer);
				++number;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addDataTOtableSessions(ArrayList<DataTable> list,ArrayList<String> nameList,int type)
	{
		tableView.getItems().clear();
		tableView.getColumns().clear();
		ObservableList<DataTable> dataC = FXCollections.observableArrayList(list);
		/*TableColumn firstCol = new TableColumn(name1);
		firstCol.prefWidthProperty().bind(tableView.widthProperty().divide(2));
		firstCol.setMaxWidth(400);
		firstCol.setCellValueFactory(new PropertyValueFactory<DataTable, String>("field1"));
		TableColumn secondCol = new TableColumn(name2);
		secondCol.setMaxWidth(400);
		secondCol.setCellValueFactory(new PropertyValueFactory<DataTable, String>("field2"));
		secondCol.prefWidthProperty().bind(tableView.widthProperty().divide(2));*/
		ArrayList<TableColumn> columns = new ArrayList<TableColumn>();
		tableView.setItems(dataC);
		int i=1;
		for (String tableColumn : nameList)
		{
			TableColumn firstCol = new TableColumn(nameList.get(i-1));
			if(type!=0)
			{
				firstCol.prefWidthProperty().bind(tableView.widthProperty().divide(type));
			}
			firstCol.setCellValueFactory(new PropertyValueFactory<DataTable, String>("field"+i));
			++i;
			tableView.getColumns().addAll(firstCol);
		}
		//tableView.getColumns().addAll(columns);
	}

	public void loadSessions()
	{
		DBConnector con = new DBConnector();
		try
		{
			if (choiseConfBox.getValue() == null)
				return;
			this.addSessions();
			int idConf = choiseConfBox.getValue().intValue();
			ArrayList<Integer> sessionList = con.getSessionIdList(idConf);
			ArrayList<DataTable> values = new ArrayList<DataTable>();
			for (Integer integer : sessionList)
			{
				Session session = con.getSession(idConf, integer.intValue());
				values.add(new DataTable(integer.toString(), session.getSessionName(),session.getBeginDate().toString(),session.getEndDate().toString(),"",""));
			}
			ArrayList<String> colNames = new ArrayList<String>();
			colNames.add("Session id");
			colNames.add("Session name");
			colNames.add("Session start time");
			colNames.add("Session end time");
			addDataTOtableSessions(values, colNames, 4);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadLectures()
	{
		DBConnector con = new DBConnector();
		if (choiseConfBox.getValue() == null)
			return;
		if (choiseSesBox.getValue() == null)
			return;
		addLectures();
		int idConf = choiseConfBox.getValue().intValue();
		int idSes = choiseSesBox.getValue().intValue();
		ArrayList<DataTable> values = new ArrayList<DataTable>();
		try
		{
			ArrayList<Integer> lectureList = con.getLectureIdList(idConf, idSes);
			for (Integer integer : lectureList)
			{
				int idExtr =integer.intValue();
				Extract ext = con.getExtract(idConf, idSes, idExtr);
				values.add(new DataTable(integer.toString(), ext.getLecture().getThema(),String.valueOf(ext.getNumberInSession()),String.valueOf(ext.getKw1()),String.valueOf(ext.getKw2()),String.valueOf(ext.getKw3())));
			}
			ArrayList<String> colNames = new ArrayList<String>();
			colNames.add("Lecture id");
			colNames.add("Lecture thema");
			colNames.add("Order lecture");
			colNames.add("KW1");
			colNames.add("KW2");
			colNames.add("KW3");
			addDataTOtableSessions(values,colNames,6);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void loadLecture()
	{
		DBConnector con = new DBConnector();
		if (choiseConfBox.getValue() == null)
		{	
			System.out.println("conf");
			return;
		}
		if (choiseSesBox.getValue() == null)
		{
			System.out.println("ses");
			return;
		}
		if (choiseLecBox.getValue() == null)
		{
			System.out.println("lec");
			return;
		}
		int idConf = choiseConfBox.getValue().intValue();
		int idSes = choiseSesBox.getValue().intValue();
		int idExtr = choiseLecBox.getValue().intValue();
		ArrayList<DataTable> values = new ArrayList<DataTable>();
		try
		{
			Extract ext = con.getExtract(idConf, idSes, idExtr);
			values.add(new DataTable(choiseLecBox.getValue().toString(),ext.getSpeaker().getFirstAndSecondName() ,ext.getLecture().getThema(),ext.getLecture().getAbstractLecture(),ext.getSpeaker().getArrivalDate().toString(),ext.getSpeaker().getDepartureDate().toString()));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		ArrayList<String> colNames = new ArrayList<String>();
		colNames.add("Lecture id");
		colNames.add("Author");
		colNames.add("Lecture thema");
		colNames.add("Abstract");
		colNames.add("Arrival speaker Date");
		colNames.add("Departure speaker Date");
		addDataTOtableSessions(values,colNames,0);
		
	}
	public void cleanSessionAndLec()
	{
		choiseLecBox.getItems().clear();
		choiseSesBox.getItems().clear();
	}
	public void cleanLec()
	{
		choiseLecBox.getItems().clear();
	}
	
	public void exitMainView()
	{
		Stage stage = (Stage) choiseConfBox.getScene().getWindow();
		stage.close();
	}
}
