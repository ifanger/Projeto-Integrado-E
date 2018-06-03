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
import android.widget.TextView;

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ContactListController;
import projetoe.minhamemoria.models.AppUtils;
import projetoe.minhamemoria.models.Contact;

public class ContactListAdapter extends RecyclerView.Adapter {
    private List<Contact> contactList;
    private Activity activity;
    private Context context;
    private ContactListController controller;

    public ContactListAdapter(List<Contact> contactList, Activity activity) {
        this.contactList = contactList;
        this.activity = activity;
        this.context = activity;
        this.controller = new ContactListController(context);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView contactName;
        final TextView contactNumber;
        final ImageButton buttonDelete;
        final ImageButton buttonCall;

        private ContactViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.text_name);
            contactNumber = (TextView) view.findViewById(R.id.text_number);
            buttonDelete = (ImageButton) view.findViewById(R.id.button_edit);
            buttonCall = (ImageButton) view.findViewById(R.id.button_remove);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_contact_list, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;

        final Contact contact = contactList.get(position);
        contactViewHolder.contactName.setText(contact.getName());
        contactViewHolder.contactNumber.setText(contact.getNumber());

        contactViewHolder.buttonDelete.setImageResource(R.drawable.ic_close_white_48dp);
        contactViewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askToDeleteDialog(contact);
            }
        });

        contactViewHolder.buttonCall.setImageResource(R.drawable.ic_call_white_24dp);
        contactViewHolder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.callContact(activity, contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    /**
     * Displays an alert asking the user.
     * @param contact Contact to delete.
     */
    private void askToDeleteDialog(final Contact contact) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context)
                        .setTitle(R.string.str_delete_confirm)
                        .setMessage(String.format(context.getString(R.string.str_delete_confirm_message), contact.getName()))
                        .setPositiveButton(R.string.str_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.delete(contact);
                                contactList.remove(contact);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.str_no, null)
                        .create();
        alertDialog.show();
    }
}
