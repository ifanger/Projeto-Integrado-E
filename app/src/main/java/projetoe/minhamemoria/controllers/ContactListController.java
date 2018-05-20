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

    /**
     * Cria uma instância do controlador da lista de contatos.
     * @param context Contexto.
     */
    public ContactListController(Context context) {
        dbHelper = new ContactListHelper(context);
    }

    /**
     * Adiciona um contato no banco de dados.
     * @param contact Contato a ser salvo no banco de dados.
     * @return Id do usuário inserido.
     */
    public long addContact(Contact contact) {
        ContentValues values;
        long result;

        db = dbHelper.getWritableDatabase();

        values = new ContentValues();
        values.put(ContactListContract.ContactEntry.COLUMN_NAME, contact.getName());
        values.put(ContactListContract.ContactEntry.COLUMN_NUMBER, contact.getNumber());

        result = db.insert(ContactListContract.ContactEntry.TABLE_NAME, null, values);
        db.close();

        return result;
    }

    /**
     * Obtém a lista de todos os contatos.
     * @return Lista de todos os contatos.
     */
    public List<Contact> getContactList() {
        List<Contact> contactList = new ArrayList<>();

        Cursor cursor;
        String[] fields =  {ContactListContract.ContactEntry._ID, ContactListContract.ContactEntry.COLUMN_NAME, ContactListContract.ContactEntry.COLUMN_NUMBER};

        db = dbHelper.getWritableDatabase();

        cursor = db.query(ContactListContract.ContactEntry.TABLE_NAME, fields, null, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                try {
                    Contact contact = new Contact(
                            cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NUMBER))
                    );

                    contact.setId(cursor.getLong(cursor.getColumnIndex(ContactListContract.ContactEntry._ID)));

                    contactList.add(contact);
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

        return contactList;
    }

    /**
     * Atualiza um contato no banco de dados.
     * @param contact Contato a ser atualizado.
     * @return Se a operação foi bem sucedida.
     */
    public boolean update(Contact contact) {
        if(contact.getId() == -1) {
            System.out.println("Invalid contact id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactListContract.ContactEntry.COLUMN_NAME, contact.getName());
        contentValues.put(ContactListContract.ContactEntry.COLUMN_NUMBER, contact.getNumber());

        db.update(ContactListContract.ContactEntry.TABLE_NAME, contentValues, ContactListContract.ContactEntry._ID + "=" + contact.getId(), null);
        db.close();

        return true;
    }

    /**
     * Remove um contato do banco de dados.
     * @param contact Contato a ser removido.
     * @return Estado da remoção.
     */
    public boolean delete(Contact contact) {
        return delete(contact.getId());
    }

    /**
     * Remove um contato do banco de dados a partir da id.
     * @param id Id do contato.
     * @return Resultado da operação.
     */
    public boolean delete(long id) {
        if(id == -1) {
            System.out.println("Invalid contact id.");
            return false;
        }

        db = dbHelper.getWritableDatabase();

        int result = db.delete(
                ContactListContract.ContactEntry.TABLE_NAME,
                ContactListContract.ContactEntry._ID + "=" + id,
                null
        );

        db.close();

        return result > 0;
    }

    /**
     * Retorna um contato a partir da id.
     * @param id Id do contato.
     * @return Contato.
     * @throws Exception Caso o nome ou o número guardado sejam invalidos.
     */
    public Contact get(long id) throws Exception {
        Contact contact = new Contact("Desconhecido", "0000-0000");
        Cursor cursor;

        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + ContactListContract.ContactEntry.TABLE_NAME + " WHERE id=" + id, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            contact.setName(cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NAME)));
            contact.setNumber(cursor.getString(cursor.getColumnIndex(ContactListContract.ContactEntry.COLUMN_NUMBER)));
            contact.setId(cursor.getLong(cursor.getColumnIndex(ContactListContract.ContactEntry._ID)));
        }

        cursor.close();
        db.close();

        return contact;
    }
}
