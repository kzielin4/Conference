package Zielinski.Kamil.Controler;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogWindowControler
{
   @FXML
   private Button closeButton;
   @FXML
   public void okClik()
   {
	   Stage stage = (Stage) closeButton.getScene().getWindow();
	   stage.close();
   }
}
