package projetoe.minhamemoria.db.contracts;

import android.provider.BaseColumns;

public final class ContactListContract {

    private ContactListContract() {}

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contatos";
        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_NUMBER = "numero";
    }
}
