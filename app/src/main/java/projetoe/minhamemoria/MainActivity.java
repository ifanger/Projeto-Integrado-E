package projetoe.minhamemoria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import projetoe.minhamemoria.controllers.ContactListController;
import projetoe.minhamemoria.models.Contact;

public class MainActivity extends AppCompatActivity {
    private ContactListController contactListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactListController = new ContactListController(this);
        List<Contact> contactList = contactListController.getContactList();

        System.out.println("Lista de contatos: ");
        for (Contact c: contactList) {
            System.out.println("- " + c.getName());
        }
    }

    public void onClick(View view) {
        Contact contact = new Contact("Gustavo", "3838-2929");

        contactListController.addContact(contact);
    }
}
