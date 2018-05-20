package projetoe.minhamemoria;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import projetoe.minhamemoria.controllers.AlarmClockController;
import projetoe.minhamemoria.models.AlarmClock;
import projetoe.minhamemoria.models.AppUtils;
import projetoe.minhamemoria.views.MainActivity;

public class DebugActivity extends AppCompatActivity {
    private static final String TAG = "DebugActivity_Debug";
    private AlarmClockController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        controller = new AlarmClockController(this);
        updateSelect();
    }

    public void onInsert(View view) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        final EditText textTitulo = new EditText(this);
        textTitulo.setText("Titulo");

        final Button btnTempo = new Button(this);
        btnTempo.setText("Definir Tempo");
        btnTempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                final TimePickerDialog mTimePicker = new TimePickerDialog(DebugActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Log.d(TAG, selectedHour + ":" + selectedMinute);
                        try {
                            btnTempo.setText(AppUtils.formatTime(selectedHour + ":" + selectedMinute));
                        } catch (AppUtils.InvalidInputTimeException e) {
                            e.printStackTrace();
                        }
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        linearLayout.addView(textTitulo);
        linearLayout.addView(btnTempo);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("new entity")
                .setView(linearLayout)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            AlarmClock contact = new AlarmClock(textTitulo.getText().toString(), false, btnTempo.getText().toString());
                            controller.insert(contact);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DebugActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        updateSelect();
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
        alertDialog.show();
    }

    public void updateSelect() {
        List<AlarmClock> contactList = controller.getAlarms();

        StringBuilder contactListstr = new StringBuilder();

        for (AlarmClock a: contactList) {
            contactListstr.append(a.getId()).append(" - ");
            contactListstr.append(a.getName()).append(" - ");
            contactListstr.append(a.isRepeat()).append(" - ");
            contactListstr.append(a.getTime()).append("\n");
        }

        ((TextView)findViewById(R.id.textLog)).setText(contactListstr);
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
                                Toast.makeText(DebugActivity.this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(DebugActivity.this, "Não foi atualizado!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DebugActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        updateSelect();
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
                                Toast.makeText(DebugActivity.this, "Deletado com sucesso!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(DebugActivity.this, "Não foi deletado!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DebugActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        updateSelect();
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
        alertDialog.show();
    }
}
