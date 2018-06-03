package projetoe.minhamemoria.views;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.models.AppUtils;

public class SettingsActivity extends AppCompatActivity {
    private Button btnSOSConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSOSConfig = findViewById(R.id.button_sos_config);
        btnSOSConfig.setText(AppUtils.getPref(this, getString(R.string.str_sos_key), getString(R.string.str_police)));
        btnSOSConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SettingsActivity.this);
                builderSingle.setTitle("Selecione o número: ");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add(getString(R.string.str_police));
                arrayAdapter.add(getString(R.string.str_ems));
                arrayAdapter.add(getString(R.string.str_firedp));
                arrayAdapter.add(getString(R.string.str_report));

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUtils.setPref(SettingsActivity.this,  getString(R.string.str_sos_key), arrayAdapter.getItem(which));
                    }
                });
                builderSingle.show();
            }
        });
    }
}
