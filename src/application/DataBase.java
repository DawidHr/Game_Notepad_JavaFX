package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
	public static final String DRIVER = "org.splite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:Game_Notepad_JavaFX_Database.s3db";
	private Statement stat;

	Connection conn;

	public DataBase() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();
			System.out.println("Po³aczono");
		} catch (SQLException e) {
			System.err.println("Problem z otwarciem Polaczenia");
			e.printStackTrace();
		}
	}

	public ArrayList<Game> getallGames() {

		ArrayList<Game> list = new ArrayList<Game>();
		String query = "select * from Game";
		Statement stat1;
		try {
			stat1 = conn.createStatement();
			ResultSet rs = stat1.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id_game");
				String title = rs.getString("title");
				int id_system = rs.getInt("id_system");
				int id_studio = rs.getInt("id_studio");
				Date datePremiere = rs.getDate("date_premiere");
				Date datePremierePL = rs.getDate("date_premiere_pl");

				String note = rs.getString("note");
				String system = getSystem(id_system);
				String studio = getStudio(id_studio);
				list.add(new Game(id, title, system, studio, datePremiere, datePremierePL, note));
			}
			// proba
			// list.add(new Game(1, "GTA", "PC", "Studio", new Date(0, 0, 0), new Date(0, 0,
			// 0), "cos"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public String getSystem(int id) {
		String query = "select * from System where id_system=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getString("system_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStudio(int id) {
		String query = "select * from Studio where id_studio=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getString("studio_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getStudio(String name) {
		String query = "select * from Studio where studio_name=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getInt("id_studio");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	// Dodawanie rekordów

	public void addGame(String title, int system, int studio, Date datePremiere, Date datePremierePL, String note) {
		String query = "Insert into Game(title, id_system, id_studio, date_premiere, date_premiere_pl, note) values(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, title);
			preparedStatement.setInt(2, system);
			preparedStatement.setInt(3, studio);
			preparedStatement.setDate(4, datePremiere);
			preparedStatement.setDate(5, datePremierePL);
			preparedStatement.setString(6, note);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getallStudios() {
		ArrayList<String> list = new ArrayList<String>();
		String query = "select * from Studio";
		try {
			Statement stat1 = conn.createStatement();

			ResultSet rs = stat1.executeQuery(query);
			while (rs.next()) {
				list.add(rs.getString("studio_name"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getallPlatforms() {
		ArrayList<String> list = new ArrayList<String>();
		String query = "select * from System";
		try {
			Statement stat1 = conn.createStatement();
			ResultSet rs = stat1.executeQuery(query);
			while (rs.next()) {
				list.add(rs.getString("system_name"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getPlatform(String platformName) {
		String query = "select * from System where system_name=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, platformName);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return rs.getInt("id_system");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void delete(String title, String studio, String platform) {
		System.out.println("BAZA DANYCH: title:" + title + " studio " + studio + " platforma " + platform);
		String query = "Delete from Game where title=? and id_system=? and id_studio=?";
		int studioint = getStudio(studio);
		int platformint = getPlatform(platform);
		System.out.println("BAZA DANYCH: studioint:" + studioint + " platformint " + platformint);
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, title);
			preparedStatement.setInt(2, platformint);
			preparedStatement.setInt(3, studioint);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			stat.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Game selectAGame(String title, String studio, String platform) {
		String query = "select * from Game where title=? and id_system=? and id_studio=?";
		int studioInt = getStudio(studio);
		int platformInt = getPlatform(platform);
		// System.out.println("numer studia to: "+studioInt+"\nNumer platformy to:
		// "+platformInt);
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, title);
			preparedStatement.setInt(2, platformInt);
			preparedStatement.setInt(3, studioInt);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				return new Game(rs.getInt("id_game"), rs.getString("title"), getSystem(rs.getInt("id_system")),
						getStudio(rs.getInt("id_studio")), rs.getDate("date_premiere"), rs.getDate("date_premiere_pl"),
						rs.getString("note"));
				// System.out.println("Jestem w DB "+ rs.getString("note"));
				// return rs.getInt("id_system");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateGame(String titleString, String studioString, String platformString, String titleAfterChange,
			int platformInt, int studioInt, Date datePremiere, Date datePremierePL, String note1) {
		String query = "update Game SET title=?, id_system=?, id_studio=?, date_premiere=?, date_premiere_pl=?, note=? where title=? and id_system=? and id_studio=?";
		int systemINTAfter = getPlatform(platformString);
		int studioINTAfter = getStudio(studioString);
		try {
			PreparedStatement s = conn.prepareStatement(query);
			s.setString(1, titleAfterChange);
			s.setInt(2, platformInt);
			s.setInt(3, studioInt);
			s.setDate(4, datePremiere);
			s.setDate(5, datePremierePL);
			s.setString(6, note1);
			s.setString(7, titleString);
			s.setInt(8, systemINTAfter);
			s.setInt(9, studioINTAfter);
			s.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}