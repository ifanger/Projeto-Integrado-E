package projetoe.minhamemoria.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmClock {
    private long id;
    private String name;
    private boolean repeat;
    private Date time;

    public AlarmClock(String name, boolean repeat, String time) {
        this.name = name;
        this.repeat = repeat;
        this.time = time;
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setStrTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        sdf.format(time);
    }

    public String getStrTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        return sdf.format(time);
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
}
