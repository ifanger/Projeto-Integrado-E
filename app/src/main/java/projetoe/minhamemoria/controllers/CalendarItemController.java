package projetoe.minhamemoria.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import projetoe.minhamemoria.db.contracts.CalendarItemContract;
import projetoe.minhamemoria.db.helpers.CalendarItemHelper;
import projetoe.minhamemoria.models.CalendarItem;

public class CalendarItemController {
    private SQLiteDatabase db;
    private CalendarItemHelper dbHelper;

    public CalendarItemController(Context context) {
        dbHelper = new CalendarItemHelper(context);
    }

    public long insert(CalendarItem calendarItem) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(CalendarItemContract.CalendarItemEntry.COLUMN_NAME, calendarItem.getName());
        values.put(CalendarItemContract.CalendarItemEntry.COLUMN_DATE, calendarItem.getDate());
        values.put(CalendarItemContract.CalendarItemEntry.COLUMN_TIME, calendarItem.getTime());

        result = db.insert(CalendarItemContract.CalendarItemEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public List<CalendarItem> getAlarms() throws CalendarItem.TimeException, CalendarItem.DateException, CalendarItem.NameException {
        List<CalendarItem> calendars = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {CalendarItemContract.CalendarItemEntry._ID, CalendarItemContract.CalendarItemEntry.COLUMN_NAME, CalendarItemContract.CalendarItemEntry.COLUMN_DATE, CalendarItemContract.CalendarItemEntry.COLUMN_TIME};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(CalendarItemContract.CalendarItemEntry.TABLE_NAME, fields, null, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                CalendarItem calendarItem = new CalendarItem(
                        cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_TIME))
                );

                calendarItem.setId(cursor.getLong(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry._ID)));
                calendars.add(calendarItem);
                cursor.moveToNext();
            }

            cursor.close();
        } else {
            System.out.println("Invalid cursor pointer.");
        }

        db.close();

        return calendars;
    }

    public boolean update(CalendarItem calendarItem) {
        if(calendarItem.getId() == -1) {
            System.out.println("Invalid alarm id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarItemContract.CalendarItemEntry.COLUMN_NAME, calendarItem.getName());
        contentValues.put(CalendarItemContract.CalendarItemEntry.COLUMN_DATE, calendarItem.getDate());
        contentValues.put(CalendarItemContract.CalendarItemEntry.COLUMN_TIME, calendarItem.getTime());

        db.update(CalendarItemContract.CalendarItemEntry.TABLE_NAME, contentValues, CalendarItemContract.CalendarItemEntry._ID + "=" + calendarItem.getId(), null);
        db.close();

        return true;
    }

    public boolean delete(CalendarItem calendarItem) {
        return delete(calendarItem.getId());
    }

    public boolean delete(long id) {
        if(id == -1) {
            System.out.println("Invalid alarm id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        int result = db.delete(
                CalendarItemContract.CalendarItemEntry.TABLE_NAME,
                CalendarItemContract.CalendarItemEntry._ID + "=" + id,
                null
        );

        db.close();

        return result > 0;
    }

    public CalendarItem get(long id) throws CalendarItem.TimeException, CalendarItem.DateException, CalendarItem.NameException {
        CalendarItem calendarItem = new CalendarItem("Calendário Desconhecido", "0000-00-00", "00:00");

        Cursor cursor;
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + CalendarItemContract.CalendarItemEntry.TABLE_NAME + " WHERE _id=" + id, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            calendarItem.setId(cursor.getLong(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry._ID)));
            calendarItem.setName(cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_NAME)));
            calendarItem.setDate(cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_DATE)));
            calendarItem.setTime(cursor.getString(cursor.getColumnIndex(CalendarItemContract.CalendarItemEntry.COLUMN_TIME)));
        }

        cursor.close();
        db.close();

        return calendarItem;
    }
}
