package projetoe.minhamemoria.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import projetoe.minhamemoria.db.contracts.AlarmClockContract;

public class AlarmClockHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "AlarmClock.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AlarmClockContract.AlarmClockEntry.TABLE_NAME + " (" +
                    AlarmClockContract.AlarmClockEntry._ID + " INTEGER PRIMARY KEY," +
                    AlarmClockContract.AlarmClockEntry.COLUMN_NAME + " TEXT, " +
                    AlarmClockContract.AlarmClockEntry.COLUMN_TIME + " DATE, " +
                    AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO + " INT, " +
                    AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT + " INT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AlarmClockContract.AlarmClockEntry.TABLE_NAME;

    public AlarmClockHelper(Context context) {
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
