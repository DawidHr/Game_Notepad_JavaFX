package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editGame implements Initializable {
	DataBase db;
	
	@FXML
	private TextField title;
	@FXML
	private TextArea note;
	@FXML
	private DatePicker date_premiere;
	@FXML
	private DatePicker date_premiere_pl;
	@FXML
	private ComboBox<String> platform;
	@FXML
	private ComboBox<String> studio;
	
	String titleString;
	String studioString;
	String platformString;
	ObservableList<String> liststudio;
	ObservableList<String> observableListplatform;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(db==null) {
		db = new DataBase();
		}
		ArrayList<String> studiolist = db.getallStudios();
		liststudio = FXCollections.observableArrayList(studiolist);
		studio.setItems(liststudio);
		
		ArrayList<String> platformlist = db.getallPlatforms();
		observableListplatform = FXCollections.observableList(platformlist);
		platform.setItems(observableListplatform);
		note.setWrapText(true);
	}
	
	//Pobieranie i ustawianie danych do edycji
	public void setAttribute(String title1, String studio1, String platform1) {
		titleString = title1;
		studioString=studio1;
		platformString=platform1;
		Game aGame = db.selectAGame(title1, studio1, platform1);
		//Ustawianie tytu³u do wyœwietlania
		title.setText(titleString);
		//Ustawianie studia do wyswietlania
		for(int i=0;i<liststudio.size();i++) {
			if(liststudio.get(i).equals(studioString)) {
				studio.setValue(studioString);
			}
		}
		//Ustawianie platformy
		for(int i=0;i<observableListplatform.size();i++) {
			if(observableListplatform.get(i).equals(platformString)) {
				platform.setValue(platformString);
			}
		}
		//Ustawianie notatki
		note.setText(aGame.note);
		//Ustawianie Daty
		Date date = aGame.getDate_premiere();
		date_premiere.setValue(date.toLocalDate());
		if(aGame.getDate_premiere_pl() != null) {
			date = aGame.getDate_premiere_pl();
			date_premiere_pl.setValue(date.toLocalDate());
		}
	}
	
	
	
	//edycja danych
	public void editGame(ActionEvent event) {
		String titleAfterChange = title.getText();
		if(titleAfterChange.trim() == null || titleAfterChange.trim().length()==0) {
			title.setText("Nale¿y uzupe³nic tytu³");
			return;
		}
		String note1 = note.getText(); 
	
		String studioName = studio.getValue();
		/*if(studioName.trim() == null || studioName.trim().length() == 0) {
			return;
		}*/

		int studioInt = db.getStudio(studioName);
		String platformName = platform.getValue();
		
		if(platformName == null || platformName.length() == 0) {
			return;
		}
		int platformInt = db.getPlatform(platformName);
		if(date_premiere.getValue() == null) {
			System.out.println("jest null");
			return;
		}
		System.out.println("data: "+date_premiere.getValue());
		LocalDate date = date_premiere.getValue();
		Date datePremiere = Date.valueOf(date);
		System.out.println(datePremiere);
		Date datePremierePL = null;
		if(date_premiere_pl.getValue() == null) {
		System.out.println("Data rowna null");
		}
		else {
		LocalDate date2 = date_premiere_pl.getValue();
		datePremierePL = Date.valueOf(date2);
		}
		db.updateGame(titleString, studioString, platformString, titleAfterChange, platformInt, studioInt, datePremiere, datePremierePL, note1);
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
			/*((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root1;
			root1 = loader.load(getClass().getResource("mainView.fxml").openStream());
			mainApplication mainView = (mainApplication)loader.getController();
			mainView.setAttribute(db);
			primaryStage.setResizable(false);
			Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
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