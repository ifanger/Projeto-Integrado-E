package projetoe.minhamemoria.models;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import projetoe.minhamemoria.R;

public class AppUtils {
    public static final int CALL_PERMISSION_REQ_CODE = 382;
    public static final String PREF_CONTACT_CALL_BEFORE_REQUEST = "pccbr";

    public static String formatTime(String time) throws InvalidInputTimeException {
        if(!time.contains(":"))
            throw new InvalidInputTimeException();

        String strHour = time.split(":")[0];
        String strMinute = time.split(":")[1];

        strHour = "00".substring(strHour.length()) + strHour;
        strMinute = "00".substring(strMinute.length()) + strMinute;

        return strHour + ":" + strMinute;
    }

    public static class InvalidInputTimeException extends Throwable {
    }

    public static void setPref(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, "");
    }

    public static void callContact(final Activity activity, final Contact contact) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + contact.getLongNumber(null)));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .setTitle(R.string.str_permission_needed)
                    .setMessage(R.string.str_permission_call)
                    .setPositiveButton(R.string.str_agree, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat
                                    .requestPermissions(activity, new String[] { Manifest.permission.CALL_PHONE }, AppUtils.CALL_PERMISSION_REQ_CODE);

                            setPref(activity, PREF_CONTACT_CALL_BEFORE_REQUEST, contact.getNumber());
                        }
                    })
                    .setNegativeButton(R.string.str_cancel, null)
                    .create();
            alertDialog.show();
            return;
        }

        activity.startActivity(callIntent);
    }
}
