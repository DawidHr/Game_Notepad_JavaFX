package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
//import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainApplication implements Initializable {

	DataBase db;

	@FXML
	private TableView<Game> table1;
	@FXML
	private TableColumn<Game, String> title1;
	@FXML
	private TableColumn<Game, String> platform1;
	@FXML
	private TableColumn<Game, String> studio1;
	@FXML
	private TableColumn<Game, Date> date_premiere1;
	@FXML
	private TableColumn<Game, Date> date_premiere_pl1;

	public ObservableList<Game> list = FXCollections.observableArrayList();
	ArrayList<Game> listGames = new ArrayList<Game>();

	@FXML
	Button addButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = new DataBase();
		listGames = db.getallGames();
		list.addAll(listGames);
		table1.setItems(list);
		title1.setCellValueFactory(new PropertyValueFactory<Game, String>("title"));
		platform1.setCellValueFactory(new PropertyValueFactory<Game, String>("platform"));
		studio1.setCellValueFactory(new PropertyValueFactory<Game, String>("studio"));
		date_premiere1.setCellValueFactory(new PropertyValueFactory<Game, Date>("date_premiere"));
		date_premiere_pl1.setCellValueFactory(new PropertyValueFactory<Game, Date>("date_premiere_pl"));

		/*
		 * table1.setOnKeyPressed(new EventHandler<KeyEvent>() { public void
		 * handle(KeyEvent ke) { if(ke.getText().equals("a")) { addGame(addButton.);
		 * System.out.println("To jest przycisk z"); } else if(ke.getText().equals("d"))
		 * { System.out.println("To jest przycisk z"); } else
		 * if(ke.getText().equals("e")) { System.out.println("To jest przycisk z"); }
		 * else if(ke.getText().equals("w")) { System.out.println("To jest przycisk z");
		 * } else {} } });
		 */
	}

	// Metoda dodawania
	// Skonczona
	public void addGame(ActionEvent event) {
		try {
			db.close();
			System.out.println(event.getSource());
			((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("addView.fxml").openStream());
			addGame addGameView = (addGame) loader.getController();
			Stage primaryStage = new Stage();
			primaryStage.setResizable(false);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteGame(ActionEvent event) {
		try {
			String gameName = table1.getSelectionModel().getSelectedItem().getTitle();
			String gamePlatform = table1.getSelectionModel().getSelectedItem().getPlatform();
			String gameStudio = table1.getSelectionModel().getSelectedItem().getStudio();

			db.close();
			System.out.println("Zamknieto baze danych: " + db.conn.isClosed());
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root11;
			root11 = loader.load(getClass().getResource("deleteView.fxml").openStream());
			deleteGame deleteView = (deleteGame) loader.getController();
			deleteView.setAttribute(gameName, gameStudio, gamePlatform);
			primaryStage.setResizable(false);
			Scene scene = new Scene(root11);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("jestem w bledzie");
			e.printStackTrace();
		}

	}

	public void editGame(ActionEvent event) {
		try {
			String gameName = table1.getSelectionModel().getSelectedItem().getTitle();
			String gamePlatform = table1.getSelectionModel().getSelectedItem().getPlatform();
			String gameStudio = table1.getSelectionModel().getSelectedItem().getStudio();
			db.close();
			System.out.println("Zamknieto baze danych: " + db.conn.isClosed());
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root11;
			root11 = loader.load(getClass().getResource("editView.fxml").openStream());
			editGame editView = (editGame) loader.getController();
			editView.setAttribute(gameName, gameStudio, gamePlatform);
			primaryStage.setResizable(false);
			Scene scene = new Scene(root11);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Catch");
			e.printStackTrace();
		}
	}

	public void moreAboutGame(ActionEvent event) {
		try {
			String gameName = table1.getSelectionModel().getSelectedItem().getTitle();
			String gamePlatform = table1.getSelectionModel().getSelectedItem().getPlatform();
			String gameStudio = table1.getSelectionModel().getSelectedItem().getStudio();

			db.close();
			System.out.println("Zamknieto baze danych: " + db.conn.isClosed());
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root11;
			root11 = loader.load(getClass().getResource("selectedGameView.fxml").openStream());
			selectedGame editView = (selectedGame) loader.getController();
			editView.setAttribute(gameName, gameStudio, gamePlatform);
			primaryStage.setResizable(false);
			Scene scene = new Scene(root11);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Catch");
			e.printStackTrace();
		}
	}
}
