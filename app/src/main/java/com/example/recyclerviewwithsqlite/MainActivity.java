package com.example.recyclerviewwithsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreateData.passData,EmployeeAdapter.Data,EmployeeAdapter.Update {
    List<Employee> employeeList;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DatabaseHelper(this);
        employeeList = new ArrayList<Employee>();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                openDialog();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        data = openHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        if (data.getCount() != 0) {
            while (data.moveToNext()) {
                employeeList.add(new Employee(data.getInt(0), data.getString(1), data.getDouble(2)));
            }

            recyclerView.setAdapter(new EmployeeAdapter(employeeList, getSupportFragmentManager()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        registerForContextMenu(recyclerView);

    }

    private void openDialog() {
        CreateData createData = new CreateData();
        createData.show(getSupportFragmentManager(), "Add Data");
    }

    @Override
    public void sendData(Bundle bundle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, bundle.getString("name"));
        contentValues.put(DatabaseHelper.COL_3, bundle.getDouble("salary"));

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

        recyclerView = findViewById(R.id.recycler_view);
        data = openHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        if (data.moveToLast()) {
            employeeList.add(new Employee(data.getInt(0), data.getString(1), data.getDouble(2)));
        }

        recyclerView.setAdapter(new EmployeeAdapter(employeeList, getSupportFragmentManager()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recyclerView);

    }

    @Override
    public void deleteData(int position) {
        Employee employee = employeeList.get(position);
        db = openHelper.getReadableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_1 + "=" + employee.empId, null);
        employeeList.remove(employee);

        recyclerView.setAdapter(new EmployeeAdapter(employeeList, getSupportFragmentManager()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(recyclerView);
    }


    @Override
    public void updateData(int position) {
        UpdateData updateData = new UpdateData();
        updateData.show(getSupportFragmentManager(), "Update Existing Data");
        UpdateData.DataListener dataListener = new UpdateData.DataListener() {
            @Override
            public Bundle ReceiveData() {
                Employee employee = employeeList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("empID", employee.getEmpId());
                bundle.putString("name", employee.getName());
                bundle.putDouble("salary", employee.getSalary());

                return bundle;
            }
        };
        updateData.setDataListener(dataListener);
        UpdateData.PassData passData = new UpdateData.PassData() {
            @Override
            public void sendData(Bundle bundle) {
                db = openHelper.getReadableDatabase();
                db.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME +
                        " SET " +
                        DatabaseHelper.COL_2 + "='" + bundle.getString("name") + "'," +
                        DatabaseHelper.COL_3 + "=" + bundle.getDouble("salary") + " WHERE " +
                        DatabaseHelper.COL_1 + "=" + bundle.getInt("empID"));

                data = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
                employeeList.clear();
                employeeList = new ArrayList<Employee>();
                if (data.getCount() != 0) {
                    while (data.moveToNext()) {
                        System.out.println(data.getInt(0));
                        employeeList.add(new Employee(data.getInt(0), data.getString(1), data.getDouble(2)));
                    }
                }

                recyclerView.setAdapter(new EmployeeAdapter(employeeList, getSupportFragmentManager()));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        };
        updateData.setPassDataListener(passData);
    }
}