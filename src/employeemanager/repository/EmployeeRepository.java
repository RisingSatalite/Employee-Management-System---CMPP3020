package employeemanager.repository;

import employeemanager.model.Department;
import employeemanager.model.Employee;
import employeemanager.model.Position;
import employeemanager.exceptions.*;

import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class EmployeeRepository {

	public List<Employee> loadEmployees(String filePath)
            throws FileStorageException, InvalidDataException {

        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            throw new FileStorageException("File does not exist: " + filePath);
        }

        List<Employee> employees = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",");
                
                //Need to determine the type of employ and default to the standard employee incase of any issues

                if (parts.length < 3) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                String fullName = parts[1].trim();

                double pay = Double.parseDouble(parts[4].trim());

                String[] nameParts = fullName.split(" ");
                String first = nameParts[0];
                String last = (nameParts.length > 1) ? nameParts[1] : "";

                Employee employee = new Employee(
                        id,
                        first,
                        last,
                        LocalDate.parse(parts[5], formatter),
                        LocalDate.parse(parts[6], formatter),
                        new Position(0, parts[2]),
                        new Department(0, parts[3])
                ) {
                    private final double loadedPay = pay;

                    @Override
                    public double getPay() {
                        return loadedPay;
                    }
                };

                employees.add(employee);
            }

            System.out.println("Successfully loaded " + employees.size()
                    + " employees from " + filePath);

            return employees;

        } catch (IOException e) {
            throw new FileStorageException("Error reading file " + filePath, e);
        }
    }
	
    public void saveEmployees(String filePath, List<Employee> employees)
            throws FileStorageException {

        Path path = Path.of(filePath);
        List<String> lines = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Employee employee : employees) {
        	
        	//Determine the type of employee. Then use there specific data to save to file.
        	
            lines.add(employee.getEmployeeId() + 
            		"," + employee.getFullName() + 
            		"," + employee.getPositionName() +
            		"," + employee.getDepartmentName() +
            		"," + employee.getPay() +
            		"," + employee.getStartDate().format(formatter) +
            		"," + employee.getDateOfBirth().format(formatter));
        }

        try {
            Files.write(path, lines);
            System.out.println("Saved " + employees.size() + " employees to " + filePath);
        } catch (IOException e) {
            throw new FileStorageException("Error writing file " + filePath, e);
        }
    }
}