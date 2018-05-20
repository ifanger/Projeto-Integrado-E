package projetoe.minhamemoria.models;

public class AppUtils {
    public static String formatTime(String time) throws InvalidInputTimeException {
        if(!time.contains(":"))
            throw new InvalidInputTimeException();

        String strHour = time.split(":")[0];
        String strMinute = time.split(":")[1];

        strHour = "00".substring(strHour.length()) + strHour;
        strMinute = "00".substring(strMinute.length()) + strMinute;

        return strHour + ":" + strMinute;
    }

    public static class InvalidInputTimeException extends Throwable {
    }
}
