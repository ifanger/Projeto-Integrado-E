package projetoe.minhamemoria.views;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.AlarmClockController;
import projetoe.minhamemoria.models.AlarmClock;
import projetoe.minhamemoria.views.adapters.AlarmAdapter;

public class MedicineActivity extends AppCompatActivity implements View.OnClickListener {
    AlarmClockController controller;
    FloatingActionButton btnAddAlarm;
    List<AlarmClock> alarms;
    RecyclerView alarmListView;
    AlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmListView = (RecyclerView) findViewById(R.id.list_alarms);
        btnAddAlarm = (FloatingActionButton) findViewById(R.id.btn_add_alarm);

        try {
            controller = new AlarmClockController(this);
            alarms = controller.getAlarmMedicines();

            adapter = new AlarmAdapter(alarms, this, controller);
            RecyclerView.LayoutManager layout = new GridLayoutManager(this, 2);

            alarmListView.setHasFixedSize(true);
            alarmListView.setLayoutManager(layout);
            alarmListView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            btnAddAlarm.setOnClickListener(this);
        } catch (AlarmClock.NameException e) {
            e.printStackTrace();
        } catch (AlarmClock.TimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_new_alarm, null);
        final TextInputLayout markerTitle = layout.findViewById(R.id.text_layout_alarm_title);
        final Button markerTime = layout.findViewById(R.id.btn_set_alarm_time);
        final Switch alarmRepeat = layout.findViewById(R.id.alarm_repeat);
        final Calendar now = Calendar.getInstance();

        markerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                StringBuilder builder = new StringBuilder();
                                builder.append(String.format("%02d:", hourOfDay));
                                builder.append(String.format("%02d", minute));
                                markerTime.setText(builder.toString());
                            }
                        },
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        true);

                timePickerDialog.show(getFragmentManager(), "timePickerDialog");
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_new_calendar_item)
                .setView(layout)
                .setPositiveButton(R.string.str_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(markerTitle.getEditText() == null)
                            return;

                        try {
                            AlarmClock alarm = new AlarmClock(
                                    markerTitle.getEditText().getText().toString(),
                                    alarmRepeat.isChecked(),
                                    markerTime.getText().toString());

                            alarm.setMedicine(true);

                            controller.add(alarm);
                            alarms.add(alarm);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(MedicineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (AlarmClock.TimeException e) {
                            e.printStackTrace();
                            Toast.makeText(MedicineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (AlarmClock.NameException e) {
                            e.printStackTrace();
                            Toast.makeText(MedicineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }
}
