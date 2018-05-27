package projetoe.minhamemoria.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import projetoe.minhamemoria.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onContactClick(View view) {
        Intent contactIntent = new Intent(this, ContactActivity.class);
        startActivity(contactIntent);
    }
}
