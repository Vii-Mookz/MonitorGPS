package com.example.vipavee.monitorgps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Vipavee on 23/08/2017.
 */

public abstract class AlertUserDialog extends DialogFragment implements DialogInterface{
    private String _displayMessage;
    private String _settingsActivityAction;

    public AlertUserDialog(String displayMessage, String settingsActivityAction) {
        _displayMessage = displayMessage != null ? displayMessage : "MESSAGE NOT SET";
        _settingsActivityAction = settingsActivityAction;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(_displayMessage);
        builder.setPositiveButton("OK", (OnClickListener) this);

        Dialog theDialog = builder.create();
        theDialog.setCanceledOnTouchOutside(false);

        return theDialog;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        switch(i) {
            case Dialog.BUTTON_POSITIVE:
                // Perform desired action response to user clicking "OK"
                if(_settingsActivityAction != null)
                    startActivity(new Intent(_settingsActivityAction));
                break;
        }
    }
}
