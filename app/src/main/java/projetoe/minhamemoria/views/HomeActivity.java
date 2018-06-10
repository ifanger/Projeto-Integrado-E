package projetoe.minhamemoria.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import projetoe.minhamemoria.R;
import projetoe.minhamemoria.models.AppUtils;
import projetoe.minhamemoria.models.Contact;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            AppUtils.requestPermissionToCall(this, "");
        }
    }

    public void onContactClick(View view) {
        showActivity(ContactActivity.class);
    }

    public void onListClick(View view) {
        showActivity(ListActivity.class);
    }

    public void onCalendarClick(View view) {
        showActivity(CalendarActivity.class);
    }

    public void onSettingsClick(View view) {
        showActivity(SettingsActivity.class);
    }

    public void onAlarmClick(View view) {
        showActivity(AlarmActivity.class);
    }

    public void onMedicineClick(View view) {
        showActivity(MedicineActivity.class);
    }

    private void showActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void onSOSClick(View view) {
        String defSos = AppUtils.getPref(this, getString(R.string.str_sos_key), getString(R.string.str_police));

        if(defSos.equals(getString(R.string.str_police)))
            AppUtils.callNumber(this, "190");
        else if(defSos.equals(getString(R.string.str_ems)))
            AppUtils.callNumber(this, "192");
        else if(defSos.equals(getString(R.string.str_firedp)))
            AppUtils.callNumber(this, "193");
        else if(defSos.equals(getString(R.string.str_report)))
            AppUtils.callNumber(this, "181");
        else
            Toast.makeText(this, "Não sei qual número telefonar!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AppUtils.requestPermissionHandler(this, requestCode, permissions, grantResults);
    }
}
