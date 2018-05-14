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

    public long insert(AlarmClock alarmClock) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_NAME, alarmClock.getName());
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_TIME, alarmClock.getStrTime());
        values.put(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, (alarmClock.isRepeat()) ? 1 : 0);

        result = db.insert(AlarmClockContract.AlarmClockEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public List<AlarmClock> getLists() {
        List<AlarmClock> alarms = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {AlarmClockContract.AlarmClockEntry._ID, AlarmClockContract.AlarmClockEntry.COLUMN_NAME, AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT, AlarmClockContract.AlarmClockEntry.COLUMN_TIME};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(AlarmClockContract.AlarmClockEntry.TABLE_NAME, fields, null, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    AlarmClock alarmClock = new AlarmClock(
                            cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_REPEAT)),
                            cursor.getString(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry.COLUMN_TIME))
                    );

                    alarmClock.setId(cursor.getLong(cursor.getColumnIndex(AlarmClockContract.AlarmClockEntry._ID)));

                    alarms.add(alarmClock);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }

            cursor.close();
        } else {
            System.out.println("Invalid cursor pointer.");
        }

        db.close();

        return lists;
    }

    public boolean update(List list) {
        if(list.getId() == -1) {
            System.out.println("Invalid list id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ListContract.ListEntry.COLUMN_NAME, list.getTitle());

        db.update(ListContract.ListEntry.TABLE_NAME, contentValues, ListContract.ListEntry._ID + "=" + list.getId(), null);
        db.close();

        return true;
    }

    public boolean delete(List list) {
        return delete(list.getId());
    }

    public boolean delete(long id) {
        if(id == -1) {
            System.out.println("Invalid list id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        int result = db.delete(
                ListContract.ListEntry.TABLE_NAME,
                ListContract.ListEntry._ID + "=" + id,
                null
        );

        db.close();

        return result > 0;
    }

    public List get(long id) throws Exception {
        List list = new List("Lista com Erro");
        Cursor cursor;

        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + ListContract.ListEntry.TABLE_NAME + " WHERE id=" + id, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.setTitle(cursor.getString(cursor.getColumnIndex(ListContract.ListEntry.COLUMN_NAME)));
        }

        cursor.close();
        db.close();

        return list;
    }
}
