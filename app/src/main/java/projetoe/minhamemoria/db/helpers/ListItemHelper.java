package projetoe.minhamemoria.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projetoe.minhamemoria.db.contracts.ListItemContract;

public class ListItemHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ListItems.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ListItemContract.ListItemEntry.TABLE_NAME + " (" +
                    ListItemContract.ListItemEntry._ID + " INTEGER PRIMARY KEY," +
                    ListItemContract.ListItemEntry.COLUMN_LIST + " LONG, " +
                    ListItemContract.ListItemEntry.COLUMN_CHECK + " INT, " +
                    ListItemContract.ListItemEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ListItemContract.ListItemEntry.TABLE_NAME;

    public ListItemHelper(Context context) {
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
