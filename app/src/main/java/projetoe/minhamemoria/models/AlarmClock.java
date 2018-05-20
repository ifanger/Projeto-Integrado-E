package projetoe.minhamemoria.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmClock {
    private long id;
    private String name;
    private boolean repeat;
    private String time;
    private int hour;
    private int minute;

    public AlarmClock(String name, boolean repeat, String time) {
        this.id = -1;
        this.name = name;
        this.repeat = repeat;
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

    public void setName(String name) {
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

    /**
     * Conversão de string para números
     * @param time Tempo de repetição de entrada
     */
    public void setTime(String time) {
        //Verifica se a string de entrada está dentro do padrão:
        //HH:MM
        if(time.length() != 5 || !time.contains(":")) {
            resetTime(); //Reseta hora
            return;
        }

        try {
            //Dividimos em duas variáveis bs e as. (before split, after split)
            //bs contém o conteúdo da string antes do ":" (no caso, a hora)
            //as contém o conteúdo da string depois do ":" (no caso, os minutos)
            String bs, as;
            bs = time.split(":")[0];
            as = time.split(":")[1];

            this.hour = Integer.parseInt(bs);
            this.minute = Integer.parseInt(as);
            this.time = time;
        } catch(Exception e) {
            e.printStackTrace();
            this.minute = 0;
            this.hour = 0;
            this.time = "00:00";
        }
    }

    public void setTime(int hour, int minute) {
        String strHour = hour + "";
        String strMinute = minute + "";

        strHour = "00".substring(strHour.length()) + strHour;
        strMinute = "00".substring(strMinute.length()) + strMinute;

        setTime(strHour + ":" + strMinute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    private void resetTime() {
        this.hour = 0;
        this.minute = 0;
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
