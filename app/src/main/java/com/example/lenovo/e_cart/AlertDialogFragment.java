package com.example.lenovo.e_cart;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class AlertDialogFragment extends DialogFragment {

    public static final String OkOrCancel = "OkOrCancel";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.alert_dialog_fragent, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_dialog_title)
                .setPositiveButton(R.string.Alert_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        sendResult(Activity.RESULT_OK, true);


                    }
                })
                .setNegativeButton(R.string.Alert_Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        sendResult(Activity.RESULT_OK, false);


                    }
                })
                .setView(v)
                .create();
    }


    private void sendResult(int resultCode, Boolean b) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(OkOrCancel, b);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
