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
import projetoe.minhamemoria.controllers.AlarmClockController;
import projetoe.minhamemoria.models.AlarmClock;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity Debug";
    private AlarmClockController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new AlarmClockController(this);
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
                            AlarmClock contact = new AlarmClock(textTitulo.getText().toString(), false, "00:00");
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
        List<AlarmClock> contactList = controller.getAlarms();

        StringBuilder contactListstr = new StringBuilder();

        for (AlarmClock a: contactList) {
            contactListstr.append(a.getId()).append(" - ");
            contactListstr.append(a.getName()).append(" - ");
            contactListstr.append(a.isRepeat()).append(" - ");
            contactListstr.append(a.getTime()).append("\n");
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
                            AlarmClock contact = new AlarmClock(textTitulo.getText().toString(), false, "00:00");
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
