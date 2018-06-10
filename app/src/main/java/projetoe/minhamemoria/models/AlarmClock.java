package projetoe.minhamemoria.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmClock {
    private long id;
    private String name;
    private boolean repeat;
    private String time;

    public AlarmClock(String name, boolean repeat, String time) throws NameException, TimeException {
        this.id = -1;
        this.repeat = repeat;
        this.setName(name);
        this.setTime(time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NameException {
        if(name.trim().length() > 25 || name.trim().length() < 2)
            throw new NameException(NameException.INVALID_LENGTH);

        this.name = name;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getTime() {
        return time;
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

    public int getHour() {
        return Integer.parseInt(this.time.split(":")[0]);
    }

    public int getMinute() {
        return Integer.parseInt(this.time.split(":")[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlarmClock that = (AlarmClock) o;

        if (id != that.id) return false;
        if (repeat != that.repeat) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (repeat ? 1 : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlarmClock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", repeat=" + repeat +
                ", time=" + time +
                '}';
    }

    public class NameException extends Throwable {
        public final static String INVALID_LENGTH = "O tamanho do título deve ser entre 2 e 25 caracteres.";

        public NameException(String message) {
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
