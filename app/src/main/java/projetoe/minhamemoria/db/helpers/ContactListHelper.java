package projetoe.minhamemoria.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projetoe.minhamemoria.db.contracts.ContactListContract;

/**
 * Classe para manuseio da lista de contatos
 */
public class ContactListHelper extends SQLiteOpenHelper {
    /**
     * Versão do banco de dados
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Nome do arquivo do banco de dados
     */
    private static final String DATABASE_NAME = "ContactList.db";

    /**
     * String para criação do banco de dados.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactListContract.ContactEntry.TABLE_NAME + " (" +
                    ContactListContract.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactListContract.ContactEntry.COLUMN_NAME + " TEXT," +
                    ContactListContract.ContactEntry.COLUMN_NUMBER + " TEXT)";

    /**
     * String para remoção do banco de dados.
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactListContract.ContactEntry.TABLE_NAME;


    public ContactListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
