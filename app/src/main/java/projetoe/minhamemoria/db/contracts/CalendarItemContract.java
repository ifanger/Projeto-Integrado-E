package projetoe.minhamemoria.db.contracts;

import android.provider.BaseColumns;

public final class CalendarItemContract {

    private CalendarItemContract() {}

    public static class CalendarItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "calendario";
        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_DATE = "data";
        public static final String COLUMN_TIME = "hora_aviso_previo";
    }
}
