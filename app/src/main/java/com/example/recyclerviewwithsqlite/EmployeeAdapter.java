package com.example.recyclerviewwithsqlite;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employeeList;
    private int position;
    FragmentManager fragmentManager;
    public EmployeeAdapter(List<Employee> employeeList, FragmentManager fragmentManager) {
        this.employeeList = employeeList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View employeeView = inflater.inflate(R.layout.list_item_view,parent,false);

        ViewHolder viewHolder = new ViewHolder(employeeView);
        return viewHolder;
    }


    void setPosition(int position) {
        this.position = position;
    }
    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        TextView name = holder.name;
        TextView salary = holder.salary;
        name.setText(employee.getName());
        salary.setText(Double.toString(employee.getSalary()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView name;
        public TextView salary;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text_view);
            salary = itemView.findViewById(R.id.salary_text_view);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Update listener;
                    try {
                        listener = (Update) v.getContext();
                        listener.updateData(position);
                    } catch (ClassCastException ex) {
                        throw new ClassCastException(v.getContext().toString());
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(0, R.id.edit, 0, "Edit");
            MenuItem delete = menu.add(0, R.id.delete, 1, "Delete");
            edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Update listener;
                    try {
                        listener = (Update) v.getContext();
                        listener.updateData(position);
                    } catch (ClassCastException ex) {
                        throw new ClassCastException(v.getContext().toString());
                    }
                    return true;
                }
            });
            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Data listener;
                    try {
                        listener = (Data) v.getContext();
                        listener.deleteData(position);
                    } catch (ClassCastException ex) {
                        throw new ClassCastException(v.getContext().toString());
                    }

                    return true;
                }
            });

        }
    }
    //Adding comment
    public interface Data {
        void deleteData(int position);
    }
    public interface Update{
        void updateData(int position);
    }
}