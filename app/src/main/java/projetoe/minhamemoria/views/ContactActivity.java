package projetoe.minhamemoria.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ContactListController;
import projetoe.minhamemoria.models.AppUtils;
import projetoe.minhamemoria.models.Contact;
import projetoe.minhamemoria.views.adapters.ContactListAdapter;

public class ContactActivity extends AppCompatActivity {
    private List<Contact> contactList;
    private ContactListController controller;
    private FloatingActionButton buttonNewContact;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        recyclerView = (RecyclerView) findViewById(R.id.list_contacts);
        buttonNewContact = (FloatingActionButton) findViewById(R.id.button_new_contact);

        controller = new ContactListController(this);

        init();
    }

    /**
     * Load data and setup environment
     */
    private void init() {
        setTitle(R.string.str_contact_list);

        buttonNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNewContactClick(view);
            }
        });

        loadContacts();
        initRecycleViewer();
    }

    /**
     * Get contact list from database.
     */
    private boolean contactsLoaded = false;
    private void loadContacts() {
        if(contactsLoaded) return;

        contactList = controller.getContactList();
        adapter = new ContactListAdapter(contactList, this);
        contactsLoaded = true;
    }

    /**
     * Load contact list into a RecyclerView.
     */
    private void initRecycleViewer() {
        if(!contactsLoaded) return;

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * UI for add a new contact.
     * @param view TODO
     */
    private void onNewContactClick(View view) {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_new_contact, null);
        final TextInputLayout contactName = layout.findViewById(R.id.text_layout_name);
        final TextInputLayout contactNumber = layout.findViewById(R.id.text_layout_number);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_new_contact)
                .setView(layout)
                .setPositiveButton(R.string.str_add_contact, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(contactName.getEditText() == null || contactNumber.getEditText() == null)
                            return;

                        addContact(
                                contactName.getEditText().getText().toString().trim(),
                                contactNumber.getEditText().getText().toString().trim()
                        );
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }

    private void addContact(String name, String number) {
        try {
            Contact contact = new Contact(name, number);
            controller.addContact(contact);
            contactList.add(contact);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Hides the keyboard.
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppUtils.CALL_PERMISSION_REQ_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String numberToCall = AppUtils.getPref(this, AppUtils.PREF_CONTACT_CALL_BEFORE_REQUEST).trim();
                    if(!numberToCall.isEmpty()) {
                        try {
                            AppUtils.callContact(this, new Contact("tmp", numberToCall));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    Toast.makeText(this, getString(R.string.str_permission_call_refused), Toast.LENGTH_LONG).show();

                break;
            default:
                break;
        }
    }
}
