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

import java.util.List;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.controllers.ListController;
import projetoe.minhamemoria.controllers.ListItemController;
import projetoe.minhamemoria.models.ListItem;
import projetoe.minhamemoria.views.adapters.ListItemAdapter;

public class ListItemActivity extends AppCompatActivity {
    private long parentListId = -1;
    private projetoe.minhamemoria.models.List parentList;
    private List<ListItem> items;
    private ListController listController;
    private ListItemController controller;
    private FloatingActionButton buttonNewItem;
    private RecyclerView recyclerView;
    private ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        parentListId = getIntent().getLongExtra("item_id", -1);
        controller = new ListItemController(this);
        listController = new ListController(this);

        if(parentListId == -1)
            finishActivity(0);

        try {
            parentList = listController.get(parentListId);
        } catch (Exception e) {
            finishActivity(0);
        }

        items = controller.getItems(parentListId);

        setTitle(parentList.getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.list_items);
        buttonNewItem = (FloatingActionButton) findViewById(R.id.button_add_item);
        adapter = new ListItemAdapter(items, this, controller);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        buttonNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewItem();
            }
        });
    }

    private void onAddNewItem()
    {
        final View layout = getLayoutInflater().inflate(R.layout.dialog_new_list, null);
        final TextInputLayout itemTitle = layout.findViewById(R.id.text_layout_title);

        itemTitle.setHint(getString(R.string.str_new_item));

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.str_new_item)
                .setView(layout)
                .setPositiveButton(R.string.str_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(itemTitle.getEditText() == null)
                            return;

                        addItem(
                                itemTitle.getEditText().getText().toString().trim()
                        );
                    }
                })
                .setNeutralButton(R.string.str_cancel, null)
                .create();
        alertDialog.show();
    }

    private void addItem(String title) {
        try {
            ListItem item = new ListItem(parentListId, title);
            item.setId(controller.insert(item));
            items.add(item);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
