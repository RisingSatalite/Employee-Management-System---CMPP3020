package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;

import java.time.LocalDate;

public class Instructor extends FullTimeEmployee {

    public Instructor(int employeeId,
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

    public void teach() {
        System.out.println(getFullName() + " is teaching a class.");
    }

    @Override
    public void reportToManager() {
        System.out.println("[Instructor] " + getFullName() + " reports to department chair.");
    }
}
