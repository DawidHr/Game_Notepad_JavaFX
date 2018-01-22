package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class deleteGame implements Initializable {

	DataBase db;
	
	@FXML
	private Label titleFXML;
	@FXML
	private Label studioFXML;
	@FXML
	private Label platformFXML;
	
	String title;
	String studio;
	String platform;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setAttribute(String gameTitle, String gameStudio, String gamePlatform) {
		title=gameTitle;
		studio = gameStudio;
		platform = gamePlatform;
		titleFXML.setText(title);
		studioFXML.setText(studio);
		platformFXML.setText(platform);
		db = new DataBase();
		System.out.println("Jestesmy w deleteView\nNazwa:"+title);
	}
	
	public void deleteGame(ActionEvent event) {
		db.delete(title, studio, platform);
		
		try {
			try {
				System.out.println("Zamknieto baze danych: "+db.conn.isClosed());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.close();
			try {
				System.out.println("Zamknieto baze danych: "+db.conn.isClosed());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root1;
			root1 = loader.load(getClass().getResource("mainView.fxml").openStream());
			mainApplication mainView = (mainApplication)loader.getController();
			primaryStage.setResizable(false);
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("jestem w bledzie");
			e.printStackTrace();
		}
	}
	
	public void cencel(ActionEvent event) {
		db.close();
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage= new Stage();
			Parent root1 = FXMLLoader.load(getClass().getResource("mainView.fxml"));
			primaryStage.setResizable(false);
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("jestem w bledzie");
			e.printStackTrace();
		
	}
		
	}

}
