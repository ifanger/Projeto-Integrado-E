package projetoe.minhamemoria.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import projetoe.minhamemoria.db.contracts.ContactListContract;
import projetoe.minhamemoria.db.helpers.ContactListHelper;
import projetoe.minhamemoria.models.Contact;

public class ContactListController {
    private SQLiteDatabase db;
    private ContactListHelper dbHelper;

    public ContactListController(Context context) {
        dbHelper = new ContactListHelper(context);
    }

    public boolean addContact(Contact contact) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(ContactListContract.ContactEntry.COLUMN_NAME, contact.getName());
        values.put(ContactListContract.ContactEntry.COLUMN_NUMBER, contact.getNumber());

        result = db.insert(ContactListContract.ContactEntry.TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }

    public List<Contact> getContactList() {
        List<Contact> contactList = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {ContactListContract.ContactEntry.COLUMN_NAME, ContactListContract.ContactEntry.COLUMN_NUMBER};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(ContactListContract.ContactEntry.TABLE_NAME, fields, null, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Contact contact = new Contact(
                        cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NUMBER))
                );

                contactList.add(contact);
                cursor.moveToNext();
            }

            cursor.close();
        } else {
            System.out.println("Invalid cursor pointer.");
        }

        db.close();

        return contactList;
    }
}
