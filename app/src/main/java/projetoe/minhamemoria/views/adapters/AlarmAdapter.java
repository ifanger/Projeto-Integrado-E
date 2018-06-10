package projetoe.minhamemoria.views.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.AlarmClockController;
import projetoe.minhamemoria.models.AlarmClock;

public class AlarmAdapter extends RecyclerView.Adapter {
    private List<AlarmClock> alarms;
    private Activity activity;
    private Context context;
    private AlarmClockController controller;

    public AlarmAdapter(List<AlarmClock> alarms, Activity activity, AlarmClockController controller) {
        this.alarms = alarms;
        this.activity = activity;
        this.context = activity;
        this.controller = controller;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView alarmTitle;
        final TextView alarmTime;
        final ImageView alarmRepeat;
        final ImageButton alarmDelete;

        private ViewHolder(View view) {
            super(view);
            alarmTitle = (TextView) view.findViewById(R.id.txt_alarm_title);
            alarmTime = (TextView) view.findViewById(R.id.txt_alarm_time);
            alarmRepeat = (ImageView) view.findViewById(R.id.image_alarm_repeat);
            alarmDelete = (ImageButton) view.findViewById(R.id.btn_delete_alarm);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_alarm, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        final AlarmClock alarm = alarms.get(position);
        viewHolder.alarmTitle.setText(alarm.getName());
        viewHolder.alarmTime.setText(alarm.getTime());
        viewHolder.alarmRepeat.setVisibility(alarm.isRepeat() ? View.VISIBLE : View.GONE);

        viewHolder.alarmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askToDeleteDialog(alarm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    private void askToDeleteDialog(final AlarmClock calendarItem) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setTitle(R.string.str_delete_confirm)
                        .setMessage(String.format(context.getString(R.string.str_delete_confirm_message), calendarItem.getName()))
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.delete(calendarItem);
                                alarms.remove(calendarItem);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .create();
        alertDialog.show();
    }
}
