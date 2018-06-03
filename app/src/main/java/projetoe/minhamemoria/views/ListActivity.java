package projetoe.minhamemoria.views;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ListController;
import projetoe.minhamemoria.models.List;
import projetoe.minhamemoria.views.adapters.ListAdapter;

public class ListActivity extends AppCompatActivity {
    private java.util.List<List> lists;
    private ListController controller;
    private FloatingActionButton buttonNewList;
    private RecyclerView recyclerView;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle(R.string.str_list_lists);

        recyclerView = (RecyclerView) findViewById(R.id.list_lists);
        buttonNewList = (FloatingActionButton) findViewById(R.id.button_add_list);
        controller = new ListController(this);
        lists = controller.getLists();
        adapter = new ListAdapter(lists, this, controller);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        buttonNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewList();
            }
        });
    }

    private void onAddNewList()
    {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_new_list, null);
        final TextInputLayout listTitle = layout.findViewById(R.id.text_layout_title);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_new_list)
                .setView(layout)
                .setPositiveButton(R.string.str_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(listTitle.getEditText() == null)
                            return;

                        addList(
                                listTitle.getEditText().getText().toString().trim()
                        );
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }

    private void addList(String title) {
        try {
            List list = new List(title);
            list.setId(controller.insert(list));
            lists.add(list);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
