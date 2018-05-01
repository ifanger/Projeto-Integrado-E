package projetoe.minhamemoria.views;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ContactListController;
import projetoe.minhamemoria.models.Contact;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity Debug";
    private ContactListController contactListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactListController = new ContactListController(this);
    }

    public void onClick(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        final EditText textNome = new EditText(this);
        final EditText textTelefone = new EditText(this);

        linearLayout.addView(textNome);
        linearLayout.addView(textTelefone);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("new entity")
                .setView(linearLayout)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Contact contact = new Contact(textNome.getText().toString(), textTelefone.getText().toString());
                            contactListController.addContact(contact);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
        alertDialog.show();
    }

    public void onSelectClick(View view) {
        List<Contact> contactList = contactListController.getContactList();

        StringBuilder contactListstr = new StringBuilder();

        for (Contact c: contactList) {
            contactListstr.append(c.getId()).append(" - ");
            contactListstr.append(c.getName()).append(" ");
            contactListstr.append(c.getNumber()).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("contact list")
                .setMessage(contactListstr.toString())
                .setPositiveButton("ok", null)
                .create();
        alertDialog.show();
    }

    public void onUpdateClick(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        final EditText textId = new EditText(this);
        final EditText textNome = new EditText(this);
        final EditText textTelefone = new EditText(this);

        linearLayout.addView(textId);
        linearLayout.addView(textNome);
        linearLayout.addView(textTelefone);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("new entity")
                .setView(linearLayout)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Contact contact = new Contact(textNome.getText().toString(), textTelefone.getText().toString());
                            contact.setId(Long.parseLong(textId.getText().toString()));
                            boolean result = contactListController.update(contact);
                            if(result)
                                Toast.makeText(MainActivity.this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Não foi atualizado!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
        alertDialog.show();
    }

    public void onDeleteClick(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        final EditText textId = new EditText(this);

        linearLayout.addView(textId);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("new entity")
                .setView(linearLayout)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            boolean result = contactListController.delete(Long.parseLong(textId.getText().toString()));
                            if(result)
                                Toast.makeText(MainActivity.this, "Deletado com sucesso!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Não foi deletado!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
        alertDialog.show();
    }
}
