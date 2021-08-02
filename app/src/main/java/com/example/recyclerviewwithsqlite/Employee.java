package com.example.recyclerviewwithsqlite;

public class Employee {
    public int empId;
    public String name;
    public double salary;
    public Employee(int empId, String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.empId=empId;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getEmpId() {
        return empId;
    }
}
