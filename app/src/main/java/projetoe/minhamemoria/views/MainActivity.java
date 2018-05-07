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
import projetoe.minhamemoria.controllers.ListController;
import projetoe.minhamemoria.controllers.ListItemController;
import projetoe.minhamemoria.models.Contact;
import projetoe.minhamemoria.models.ListItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity Debug";
    private ListItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new ListItemController(this);
    }

    public void onClick(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        final EditText textTitulo = new EditText(this);
        textTitulo.setText("Titulo");

        linearLayout.addView(textTitulo);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("new entity")
                .setView(linearLayout)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ListItem contact = new ListItem(textTitulo.getText().toString());
                            controller.insert(contact);
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
        List<projetoe.minhamemoria.models.List> contactList = controller.getLists();

        StringBuilder contactListstr = new StringBuilder();

        for (projetoe.minhamemoria.models.List c: contactList) {
            contactListstr.append(c.getId()).append(" - ");
            contactListstr.append(c.getTitle()).append("\n");
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("lists")
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
        textId.setText("id");

        final EditText textTitulo = new EditText(this);
        textTitulo.setText("titulo");

        linearLayout.addView(textId);
        linearLayout.addView(textTitulo);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("update entity")
                .setView(linearLayout)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            projetoe.minhamemoria.models.List contact = new projetoe.minhamemoria.models.List(textTitulo.getText().toString());
                            contact.setId(Long.parseLong(textId.getText().toString()));
                            boolean result = controller.update(contact);
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
                .setTitle("delete entity")
                .setView(linearLayout)
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            boolean result = controller.delete(Long.parseLong(textId.getText().toString()));
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
