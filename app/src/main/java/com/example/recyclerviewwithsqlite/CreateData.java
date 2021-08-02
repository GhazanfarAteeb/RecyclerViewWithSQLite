package com.example.recyclerviewwithsqlite;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CreateData extends AppCompatDialogFragment {
    private passData listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstances) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_data,null);
        EditText salary = view.findViewById(R.id.salary);
        EditText name = view.findViewById(R.id.name);
        builder.setView(view)
                .setTitle("Add data")
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle data = new Bundle();
                        data.putString("name",name.getText().toString());
                        data.putDouble("salary",Double.parseDouble(salary.getText().toString()));
                        listener.sendData(data);
                    }
                });
        return builder.create();
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (passData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface passData {
        void sendData(Bundle data);
    }
}
