package employeemanager.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private int departmentId;
    private String name;
    private final List<Employee> employees = new ArrayList<>();

    public Department(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public void addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
            employee.setDepartment(this);
        }
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }
}