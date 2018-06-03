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
import projetoe.minhamemoria.controllers.ListController;
import projetoe.minhamemoria.models.AppUtils;
import projetoe.minhamemoria.views.ListItemActivity;

public class ListAdapter extends RecyclerView.Adapter {
    private List<projetoe.minhamemoria.models.List> lists;
    private Activity activity;
    private Context context;
    private ListController controller;

    public ListAdapter(List<projetoe.minhamemoria.models.List> lists, Activity activity, ListController controller) {
        this.lists = lists;
        this.activity = activity;
        this.context = activity;
        this.controller = controller;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView listTitle;
        final ImageButton buttonRemove;
        final ImageButton buttonEdit;

        private ContactViewHolder(View view) {
            super(view);
            listTitle = (TextView) view.findViewById(R.id.text_title);
            buttonRemove = (ImageButton) view.findViewById(R.id.button_remove);
            buttonEdit = (ImageButton) view.findViewById(R.id.button_edit);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_lists, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;

        final projetoe.minhamemoria.models.List list = lists.get(position);
        contactViewHolder.listTitle.setText(list.getTitle());

        contactViewHolder.buttonRemove.setImageResource(R.drawable.ic_close_white_48dp);
        contactViewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askToDeleteDialog(list);
            }
        });

        contactViewHolder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditListDialog(list, holder.getAdapterPosition());
            }
        });

        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ListItemActivity.class);
                intent.putExtra("item_id", list.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    private void showEditListDialog(final projetoe.minhamemoria.models.List list, final int position) {
        final View layout = activity.getLayoutInflater().inflate(R.layout.dialog_new_list, null);
        final TextInputLayout listTitle = layout.findViewById(R.id.text_layout_title);

        listTitle.getEditText().setText(list.getTitle());

        final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(activity)
                .setTitle(R.string.str_edit_list)
                .setView(layout)
                .setPositiveButton(R.string.str_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(listTitle.getEditText() == null)
                            return;

                        try {
                            list.setTitle(listTitle.getEditText().getText().toString().trim());
                            controller.update(list);
                            lists.set(position, list);
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }

    /**
     * Displays an alert asking the user.
     * @param list List to delete.
     */
    private void askToDeleteDialog(final projetoe.minhamemoria.models.List list) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setTitle(R.string.str_delete_confirm)
                        .setMessage(String.format(context.getString(R.string.str_delete_confirm_message), list.getTitle()))
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.delete(list);
                                lists.remove(list);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .create();
        alertDialog.show();
    }
}
