package projetoe.minhamemoria.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import projetoe.minhamemoria.db.contracts.AlarmClockContract;
import projetoe.minhamemoria.db.helpers.AlarmClockHelper;
import projetoe.minhamemoria.models.AlarmClock;

public class AlarmClockController {
    private SQLiteDatabase db;
    private AlarmClockHelper dbHelper;

    public AlarmClockController(Context context) {
        dbHelper = new AlarmClockHelper(context);
    }

    public long add(AlarmClock alarmClock) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_NAME, alarmClock.getName());
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_TIME, alarmClock.getTime());
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO, alarmClock.isMedicine() ? 1 : 0);
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, (alarmClock.isRepeat()) ? 1 : 0);

        result = db.insert(AlarmClockContract.AlarmClockEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public List<AlarmClock> getAlarms() throws AlarmClock.NameException, AlarmClock.TimeException {
        List<AlarmClock> alarms = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {AlarmClockContract.AlarmClockEntry._ID, AlarmClockContract.AlarmClockEntry.COLUMN_NAME, AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO, AlarmClockContract.AlarmClockEntry.COLUMN_TIME};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(AlarmClockContract.AlarmClockEntry.TABLE_NAME, fields, AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO + "=0", null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                AlarmClock alarmClock = new AlarmClock(
                        cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT)) == 1,
                        cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_TIME))
                );
                alarmClock.setMedicine(cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO)) == 1);
                alarmClock.setId(cursor.getLong(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry._ID)));
                alarms.add(alarmClock);
                cursor.moveToNext();
            }

            cursor.close();
        } else {
            System.out.println("Invalid cursor pointer.");
        }

        db.close();

        return alarms;
    }

    public List<AlarmClock> getAlarmMedicines() throws AlarmClock.NameException, AlarmClock.TimeException {
        List<AlarmClock> alarms = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {AlarmClockContract.AlarmClockEntry._ID, AlarmClockContract.AlarmClockEntry.COLUMN_NAME, AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO, AlarmClockContract.AlarmClockEntry.COLUMN_TIME};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(AlarmClockContract.AlarmClockEntry.TABLE_NAME, fields, AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO + "=1", null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                AlarmClock alarmClock = new AlarmClock(
                        cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT)) == 1,
                        cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_TIME))
                );
                alarmClock.setMedicine(cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO)) == 1);
                alarmClock.setId(cursor.getLong(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry._ID)));
                alarms.add(alarmClock);
                cursor.moveToNext();
            }

            cursor.close();
        } else {
            System.out.println("Invalid cursor pointer.");
        }

        db.close();

        return alarms;
    }

    public boolean update(AlarmClock alarmClock) {
        if(alarmClock.getId() == -1) {
            System.out.println("Invalid alarm id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmClockContract.AlarmClockEntry.COLUMN_NAME, alarmClock.getName());
        contentValues.put(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, alarmClock.isRepeat() ? 1 : 0);
        contentValues.put(AlarmClockContract.AlarmClockEntry.COLUMN_TIME, alarmClock.getTime());
        contentValues.put(AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO, alarmClock.isMedicine() ? 1 : 0);

        db.update(AlarmClockContract.AlarmClockEntry.TABLE_NAME, contentValues, AlarmClockContract.AlarmClockEntry._ID + "=" + alarmClock.getId(), null);
        db.close();

        return true;
    }

    public boolean delete(AlarmClock alarmClock) {
        return delete(alarmClock.getId());
    }

    public boolean delete(long id) {
        if(id == -1) {
            System.out.println("Invalid alarm id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        int result = db.delete(
                AlarmClockContract.AlarmClockEntry.TABLE_NAME,
                AlarmClockContract.AlarmClockEntry._ID + "=" + id,
                null
        );

        db.close();

        return result > 0;
    }

    public AlarmClock get(long id) throws AlarmClock.NameException, AlarmClock.TimeException {
        AlarmClock alarmClock = new AlarmClock("Alarme Desconhecido", false, "00:00");

        Cursor cursor;
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + AlarmClockContract.AlarmClockEntry.TABLE_NAME + " WHERE _id=" + id, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            alarmClock.setName(cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_NAME)));
            alarmClock.setId(cursor.getLong(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry._ID)));
            alarmClock.setRepeat(
                    (cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT)) == 1)
            );
            alarmClock.setMedicine(cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REMEDIO)) == 1);
            alarmClock.setTime(cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_TIME)));
        }

        cursor.close();
        db.close();

        return alarmClock;
    }
}
