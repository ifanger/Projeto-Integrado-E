package projetoe.minhamemoria.db.contracts;

import android.provider.BaseColumns;

public final class ListItemContract {

    private ListItemContract() {}

    public static class ListItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "listas_itens";
        public static final String COLUMN_NAME = "titulo";
        public static final String COLUMN_LIST = "lista_id";
        public static final String COLUMN_CHECK = "checado";
    }
}
