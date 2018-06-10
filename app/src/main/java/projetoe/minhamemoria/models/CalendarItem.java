package projetoe.minhamemoria.models;

public class CalendarItem {
	private long id;
	private String name;
	private String date;
	private String time;
	
	public CalendarItem(String name, String date, String time) throws NameException, DateException, TimeException {
		this.setName(name);
		this.setDate(date);
		this.setTime(time);
		this.id = -1;
	}
	
	public void setName(String name) throws NameException {
		if(name.trim().length() > 25 || name.trim().length() < 2)
			throw new NameException(NameException.INVALID_LENGTH);
		
		this.name = name.trim();
	}
	
	public void setDate(String date) throws DateException {
		if(date.length() != 10)
			throw new DateException(DateException.INVALID_LENGTH);

		if(!date.contains("-"))
			throw new DateException(DateException.INVALID_INPUT);

		String[] splitDate = date.split("-");

		if(splitDate.length != 3)
			throw new DateException(DateException.INVALID_INPUT);

		if(splitDate[0].length() != 4 || splitDate[1].length() != 2 || splitDate[2].length() != 2)
			throw new DateException(DateException.INVALID_INPUT);

		try {
			for (String v : splitDate)
				Integer.parseInt(v);
		} catch(NumberFormatException e) {
			throw new DateException(DateException.INVALID_INPUT);
		}

		this.date = date;
	}

	public void setTime(String time) throws TimeException {
		if(time.length() != 5)
			throw new TimeException(TimeException.INVALID_LENGTH);

		if(!time.contains(":"))
			throw new TimeException(TimeException.INVALID_FORMAT);

		String[] splitTime = time.split(":");

		if(splitTime[0].length() != 2 || splitTime[1].length() != 2)
			throw new TimeException(TimeException.INVALID_FORMAT);

		try {
			for (String v : splitTime)
				Integer.parseInt(v);
		} catch(NumberFormatException e) {
			throw new TimeException(TimeException.INVALID_FORMAT);
		}

		this.time = time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public int getYear() {
		return Integer.parseInt(this.date.split("-")[0]);
	}

	public int getMonth() {
		return Integer.parseInt(this.date.split("-")[1]);
	}

	public int getDay() {
		return Integer.parseInt(this.date.split("-")[2]);
	}

	public int getHour() {
		return Integer.parseInt(this.time.split(":")[0]);
	}

	public int getMinute() {
		return Integer.parseInt(this.time.split(":")[1]);
	}

	public class NameException extends Throwable {
		public final static String INVALID_LENGTH = "O tamanho do título deve ser entre 2 e 25 caracteres.";

		public NameException(String message) {
			super(message);
		}
	}

	public class DateException extends Throwable {
		public final static String INVALID_LENGTH = "Você não escolheu uma data.";
		public final static String INVALID_INPUT = "Você não escolheu uma data.";

		public DateException(String message) {
			super(message);
		}
	}

	public class TimeException extends Throwable {
		public final static String INVALID_LENGTH = "Você não escolheu uma hora.";
		public final static String INVALID_FORMAT = "Você não escolheu uma hora.";

		public TimeException(String message) {
			super(message);
		}
	}
}