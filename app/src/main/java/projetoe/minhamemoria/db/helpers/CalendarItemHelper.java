package projetoe.minhamemoria.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projetoe.minhamemoria.db.contracts.CalendarItemContract;

public class CalendarItemHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "CalendarItems.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CalendarItemContract.CalendarItemEntry.TABLE_NAME + " (" +
                    CalendarItemContract.CalendarItemEntry._ID + " INTEGER PRIMARY KEY," +
                    CalendarItemContract.CalendarItemEntry.COLUMN_NAME + " TEXT, " +
                    CalendarItemContract.CalendarItemEntry.COLUMN_DATE + " DATE, " +
                    CalendarItemContract.CalendarItemEntry.COLUMN_TIME + " DATE)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CalendarItemContract.CalendarItemEntry.TABLE_NAME;

    public CalendarItemHelper(Context context) {
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
