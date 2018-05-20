package projetoe.minhamemoria.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import projetoe.minhamemoria.db.contracts.ListContract;
import projetoe.minhamemoria.db.helpers.ListHelper;
import projetoe.minhamemoria.models.List;

public class ListController {
    private SQLiteDatabase db;
    private ListHelper dbHelper;

    public ListController(Context context) {
        dbHelper = new ListHelper(context);
    }

    public long insert(List list) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(ListContract.ListEntry.COLUMN_NAME, list.getTitle());

        result = db.insert(ListContract.ListEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public java.util.List<List> getLists() {
        java.util.List<List> lists = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {ListContract.ListEntry._ID, ListContract.ListEntry.COLUMN_NAME};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(ListContract.ListEntry.TABLE_NAME, fields, null, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    List list = new List(
                            cursor.getString(cursor.getColumnIndex(ListContract.ListEntry.COLUMN_NAME))
                    );

                    list.setId(cursor.getLong(cursor.getColumnIndex(ListContract.ListEntry._ID)));

                    lists.add(list);
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
            list.setId(cursor.getLong(cursor.getColumnIndex(ListContract.ListEntry._ID)));
        }

        cursor.close();
        db.close();

        return list;
    }
}
