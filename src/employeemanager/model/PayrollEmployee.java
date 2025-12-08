package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;

import java.time.LocalDate;

public class PayrollEmployee extends FullTimeEmployee {

    public PayrollEmployee(int employeeId,
                           String firstName,
                           String lastName,
                           LocalDate startDate,
                           LocalDate dateOfBirth,
                           double annualSalary,
                           int vacationDays,
                           String benefits) throws InvalidDataException {
        super(employeeId, firstName, lastName, startDate, dateOfBirth,
              annualSalary, vacationDays, benefits);
    }

    public void processPayroll(Employee employee) {
        System.out.println("Processing payroll for " + employee.getFullName() +
                " Amount: " + employee.getPay());
    }

    @Override
    public void reportToManager() {
        System.out.println("[Payroll] " + getFullName() + " reports to finance manager.");
    }
}