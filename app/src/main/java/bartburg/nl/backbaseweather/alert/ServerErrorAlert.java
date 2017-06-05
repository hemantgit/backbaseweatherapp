package bartburg.nl.backbaseweather.alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import bartburg.nl.backbaseweather.R;

/**
 * Created by Bart on 6/5/2017.
 */

public class ServerErrorAlert {
    public static void show(Context context) {
        try {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.title_server_error)
                    .setMessage(R.string.message_server_error)
                    .setPositiveButton(R.string.default_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } catch (Exception e) {
            //Ignored, should check if context can be used.
        }
    }
}
