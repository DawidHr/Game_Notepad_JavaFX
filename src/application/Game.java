package application;

import java.sql.Date;

public class Game {

	private int id;
	private String title;
	private String platform;
	private String studio;
	private Date date_premiere;
	private Date date_premiere_pl;
	String note;
	public Game() {
		super();
	}
	
	
	public Game(int id, String title, String platform,  String studio, Date date_premiere,
			Date date_premiere_pl, String note) {
		super();
		this.id = id;
		this.title = title;
		this.platform = platform;
		this.studio = studio;
		this.date_premiere = date_premiere;
		this.date_premiere_pl = date_premiere_pl;
		this.note=note;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}
	public Date getDate_premiere() {
		return date_premiere;
	}
	public void setDate_premiere(Date date_premiere) {
		this.date_premiere = date_premiere;
	}
	public Date getDate_premiere_pl() {
		return date_premiere_pl;
	}
	public void setDate_premiere_pl(Date date_premiere_pl) {
		this.date_premiere_pl = date_premiere_pl;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	
	
}
