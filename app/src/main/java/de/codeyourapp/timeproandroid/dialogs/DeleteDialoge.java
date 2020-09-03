package de.codeyourapp.timeproandroid.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import de.codeyourapp.timeproandroid.Activitys.MainActivity;

public class DeleteDialoge extends AppCompatDialogFragment {

    public int position;

    public DeleteDialoge(int position){
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warnung")
                .setMessage("Wollen Sie wirklich das Projekt löschen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        MainActivity.adapter.removeProject(position);
                    }
        }).setNegativeButton("Nein",null);
        return builder.create();
    }
}
