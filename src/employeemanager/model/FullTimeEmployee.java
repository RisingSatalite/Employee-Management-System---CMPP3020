package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;
import java.time.LocalDate;

public class FullTimeEmployee extends Employee{

	private double annualSalary;
	private int vacationDays;
	private String benefits;
	
	public FullTimeEmployee(int employeeId,
            String firstName,
            String lastName,
            LocalDate startDate,
            LocalDate dateOfBirth,
            double annualSalary,
            int vacationDays,
            String benefits) throws InvalidDataException {
		super(employeeId, firstName, lastName, startDate, dateOfBirth);
		this.annualSalary = annualSalary;
		this.vacationDays = vacationDays;
		this.benefits = benefits;
	}
	@Override
    public double getPay() {
        return annualSalary / 12.0;
    }

    @Override
    public void reportToManager() {
        System.out.println("[FullTime] " + getFullName() + " reporting to manager.");
        super.reportToManager();
    }
}
