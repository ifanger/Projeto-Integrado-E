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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ListItemController;
import projetoe.minhamemoria.models.ListItem;

public class ListItemAdapter extends RecyclerView.Adapter {
    private List<ListItem> items;
    private Activity activity;
    private Context context;
    private ListItemController controller;

    public ListItemAdapter(List<ListItem> items, Activity activity, ListItemController controller) {
        this.items = items;
        this.activity = activity;
        this.context = activity;
        this.controller = controller;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView itemTitle;
        final CheckBox checkBox;
        final ImageButton buttonRemove;

        private ContactViewHolder(View view) {
            super(view);
            itemTitle = view.findViewById(R.id.text_title);
            checkBox = view.findViewById(R.id.check_box);
            buttonRemove = view.findViewById(R.id.button_remove);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_lists_items, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ContactViewHolder contactViewHolder = (ContactViewHolder) holder;

        final ListItem item = items.get(position);

        contactViewHolder.itemTitle.setText(item.getTitle());
        contactViewHolder.checkBox.setChecked(item.isChecked());

        contactViewHolder.buttonRemove.setImageResource(R.drawable.ic_close_white_48dp);
        contactViewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askToDeleteDialog(item);
            }
        });

        contactViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setChecked(contactViewHolder.checkBox.isChecked());
                controller.update(item);
            }
        });

        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactViewHolder.checkBox.setChecked(!contactViewHolder.checkBox.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void askToDeleteDialog(final ListItem item) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setTitle(R.string.str_delete_confirm)
                        .setMessage(String.format(context.getString(R.string.str_delete_confirm_message), item.getTitle()))
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.delete(item);
                                items.remove(item);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .create();
        alertDialog.show();
    }
}
