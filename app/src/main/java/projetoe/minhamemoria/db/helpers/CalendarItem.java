package projetoe.minhamemoria.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarItem {
	private long id;
	private String name;
	private String date;
	private String time;
	
	public CalendarItem(String name, String date, String time) throws InvalidInputNameException {
		this.setName(name);
		this.setDate(date);
		this.setTime(time);
		this.id = -1;
	}
	
	public void setName(String name) throws InvalidInputNameException {
		if(name.lenght() > 25 || name.lenght() < 2)
			throw new InvalidInputNameException();
		
		this.name = name;
	}
	
	public void setDate(String date) throws InvalidInputDateException {
		if(date.lenght() != 10)
			throw new InvalidInputDateException();
		
		this.date = date;
	}	
}