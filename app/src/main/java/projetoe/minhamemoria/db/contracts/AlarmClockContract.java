package projetoe.minhamemoria.db.contracts;

import android.provider.BaseColumns;

public final class AlarmClockContract {

    private AlarmClockContract() {}

    public static class AlarmClockEntry implements BaseColumns {
        public static final String TABLE_NAME = "alarme";
        public static final String COLUMN_NAME = "nome_alarme";
        public static final String COLUMN_TIME = "rotina";
        public static final String COLUMN_REPEAT = "repetir";
        public static final String COLUMN_REMEDIO = "remedio";
    }
}
