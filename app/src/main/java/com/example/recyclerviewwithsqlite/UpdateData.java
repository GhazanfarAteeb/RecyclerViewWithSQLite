package com.example.recyclerviewwithsqlite;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UpdateData extends AppCompatDialogFragment {
    DataListener dataListener;
    PassData passDataListener;

    public Dialog onCreateDialog(Bundle savedInstances) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_data,null);
        EditText name = view.findViewById(R.id.update_name);
        EditText salary = view.findViewById(R.id.update_salary);
        Bundle bundle = dataListener.ReceiveData();
        String n = bundle.getString("name");
        double s = bundle.getDouble("salary");
        name.setText(n);
        salary.setText(Double.toString(s));
        builder.setView(view)
                .setTitle("Update Data")
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle data = new Bundle();
                        data.putInt("empID",bundle.getInt("empID"));
                        data.putString("name",name.getText().toString());
                        data.putDouble("salary",Double.parseDouble(salary.getText().toString()));
                        passDataListener.sendData(data);
                    }
                });
        return builder.create();
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }
    public void setPassDataListener(PassData passData) {
        this.passDataListener = passData;
    }

    public interface DataListener {
        Bundle ReceiveData();
    }
    public interface PassData {
        void sendData(Bundle bundle);
    }
}
