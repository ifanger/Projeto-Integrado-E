package projetoe.minhamemoria.views.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.CalendarItemController;
import projetoe.minhamemoria.models.CalendarItem;

public class CalendarItemAdapter extends RecyclerView.Adapter {
    private List<CalendarItem> calendarItems;
    private Activity activity;
    private Context context;
    private CalendarItemController controller;

    public CalendarItemAdapter(List<CalendarItem> lists, Activity activity, CalendarItemController controller) {
        this.calendarItems = lists;
        this.activity = activity;
        this.context = activity;
        this.controller = controller;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView calendarItemTitle;
        final TextView calendarItemDate;
        final TextView calendarItemTime;
        final ImageButton calendarItemDelete;

        private ContactViewHolder(View view) {
            super(view);
            calendarItemTitle = (TextView) view.findViewById(R.id.text_calendar_title);
            calendarItemDate = (TextView) view.findViewById(R.id.text_calendar_date);
            calendarItemTime = (TextView) view.findViewById(R.id.text_calendar_time);
            calendarItemDelete = (ImageButton) view.findViewById(R.id.btn_delete_calendar_item);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_calendar_items, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;

        final CalendarItem calendarItem = calendarItems.get(position);
        contactViewHolder.calendarItemTitle.setText(calendarItem.getName());
        contactViewHolder.calendarItemDate.setText(calendarItem.getDate());
        contactViewHolder.calendarItemTime.setText(calendarItem.getTime());

        contactViewHolder.calendarItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askToDeleteDialog(calendarItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendarItems.size();
    }

    private void askToDeleteDialog(final CalendarItem calendarItem) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setTitle(R.string.str_delete_confirm)
                        .setMessage(String.format(context.getString(R.string.str_delete_confirm_message), calendarItem.getName()))
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.delete(calendarItem);
                                calendarItems.remove(calendarItem);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .create();
        alertDialog.show();
    }
}
