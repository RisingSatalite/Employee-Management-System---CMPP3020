package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;
import java.time.LocalDate;

public abstract class Employee {
	private int employeeId;
	private String firstName;
	private String lastName;
	private LocalDate startDate;
	private LocalDate dateOfBirth;
	
	private Department department;
	private Position position;
	private Manager manager;
	
	protected Employee(
			int employeeId,
			String firstName,
			String lastName,
			LocalDate startDate,
			LocalDate dateOfBirth
			)
	throws InvalidDataException {
		if (employeeId <= 0) {
			throw new InvalidDataException("Employee ID must be positive.");
		}
		if (firstName == null || firstName.isBlank()) {
			throw new InvalidDataException("First name is required");
		}
		
		this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.dateOfBirth = dateOfBirth;
	}
	
	protected Employee(
			int employeeId,
			String firstName,
			String lastName,
			LocalDate startDate,
			LocalDate dateOfBirth,
			Position position,
			Department department
			)
	throws InvalidDataException {
		if (employeeId <= 0) {
			throw new InvalidDataException("Employee ID must be positive.");
		}
		if (firstName == null || firstName.isBlank()) {
			throw new InvalidDataException("First name is required");
		}
		
		this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.department = department;
	}
	
	public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public abstract double getPay();
    
    public void reportToManager() {
    	if(manager != null) {
    		System.out.println(getFullName() + "Reports to manager " + 
    	manager.getFullName());
    	} else {
    		System.out.println(getFullName() + "Has no manager assigned. ");
    	}
    }
    
    @Override
    public String toString() {
    	return "Employee{" +
                "id=" + employeeId +
                ", name='" + getFullName() + '\'' +
                ", position=" + (position != null ? position.getTitle() : "None") +
                ", department=" + (department != null ? department.getName() : "None") +
                '}';
    }
}
