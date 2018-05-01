package projetoe.minhamemoria.db.contracts;

import android.provider.BaseColumns;

public final class ListContract {

    private ListContract() {}

    public static class ListEntry implements BaseColumns {
        public static final String TABLE_NAME = "listas";
        public static final String COLUMN_NAME = "titulo";
    }
}
