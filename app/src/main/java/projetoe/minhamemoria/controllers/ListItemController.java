package projetoe.minhamemoria.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import projetoe.minhamemoria.db.contracts.ListItemContract;
import projetoe.minhamemoria.db.helpers.ListItemHelper;
import projetoe.minhamemoria.models.ListItem;

public class ListItemController {
    private SQLiteDatabase db;
    private ListItemHelper dbHelper;

    public ListItemController(Context context) {
        dbHelper = new ListItemHelper(context);
    }

    public long insert(ListItem listItem) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(ListItemContract.ListItemEntry.COLUMN_LIST, listItem.getListId());
        values.put(ListItemContract.ListItemEntry.COLUMN_NAME, listItem.getTitle());
        values.put(ListItemContract.ListItemEntry.COLUMN_CHECK, listItem.isChecked());

        result = db.insert(ListItemContract.ListItemEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    public List<ListItem> getItems(long listId) {
        List<ListItem> items = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {ListItemContract.ListItemEntry._ID, ListItemContract.ListItemEntry.COLUMN_NAME, ListItemContract.ListItemEntry.COLUMN_LIST, ListItemContract.ListItemEntry.COLUMN_CHECK};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(ListItemContract.ListItemEntry.TABLE_NAME, fields, ListItemContract.ListItemEntry.COLUMN_LIST + "=?", new String[]{listId+""}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    ListItem listItem = new ListItem(
                            cursor.getLong(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_LIST)),
                            cursor.getString(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_NAME))
                    );

                    listItem.setId(cursor.getLong(cursor.getColumnIndex(ListItemContract.ListItemEntry._ID)));
                    listItem.setChecked(cursor.getInt(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_CHECK)) == 1);

                    items.add(listItem);
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

        return items;
    }

    public boolean update(ListItem listItem) {
        if(listItem.getId() == -1) {
            System.out.println("Invalid list item id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ListItemContract.ListItemEntry.COLUMN_NAME, listItem.getTitle());
        contentValues.put(ListItemContract.ListItemEntry.COLUMN_CHECK, listItem.isChecked());

        db.update(ListItemContract.ListItemEntry.TABLE_NAME, contentValues, ListItemContract.ListItemEntry._ID + "=" + listItem.getId(), null);
        db.close();

        return true;
    }

    public boolean delete(ListItem listItem) {
        return delete(listItem.getId());
    }

    public boolean delete(long id) {
        if(id == -1) {
            System.out.println("Invalid list item id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        int result = db.delete(
                ListItemContract.ListItemEntry.TABLE_NAME,
                ListItemContract.ListItemEntry._ID + "=" + id,
                null
        );

        db.close();

        return result > 0;
    }

    public ListItem get(long id) throws Exception {
        ListItem list = new ListItem(0, "Item com Erro");
        Cursor cursor;

        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + ListItemContract.ListItemEntry.TABLE_NAME + " WHERE _id=" + id, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            list.setTitle(cursor.getString(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_NAME)));
            list.setListId(cursor.getLong(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_LIST)));
            list.setChecked(cursor.getInt(cursor.getColumnIndex(ListItemContract.ListItemEntry.COLUMN_CHECK)) == 1);
            list.setId(cursor.getLong(cursor.getColumnIndex(ListItemContract.ListItemEntry._ID)));
        }

        cursor.close();
        db.close();

        return list;
    }
}
