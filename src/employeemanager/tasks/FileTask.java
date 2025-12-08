package employeemanager.tasks;

import employeemanager.model.Employee;
import employeemanager.repository.EmployeeRepository;
import employeemanager.exceptions.AppException;

import java.util.List;

public class FileTask implements Runnable {

    public enum Mode { READ, WRITE }

    private String filePath;
    private Mode mode;
    private List<Employee> employees;
    private EmployeeRepository repository;

    public FileTask(String filePath,
                    Mode mode,
                    List<Employee> employees,
                    EmployeeRepository repository) {
        this.filePath = filePath;
        this.mode = mode;
        this.employees = employees;
        this.repository = repository;
    }

    @Override
    public void run() {
        try {
            switch (mode) {
                case READ -> repository.loadEmployees(filePath);
                case WRITE -> repository.saveEmployees(filePath, employees);
            }
        } catch (AppException ex) {
            System.err.println("FileTask error (" + mode + "): " + ex.getMessage());
        }
    }
}