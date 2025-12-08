package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;
import java.time.LocalDate;

public class PartTimeEmployee extends Employee {

    private double hourlyRate;
    private int maxHoursPerWeek;
    private LocalDate contractEndDate;

    public PartTimeEmployee(int employeeId,
                            String firstName,
                            String lastName,
                            LocalDate startDate,
                            LocalDate dateOfBirth,
                            double hourlyRate,
                            int maxHoursPerWeek,
                            LocalDate contractEndDate) throws InvalidDataException {
        super(employeeId, firstName, lastName, startDate, dateOfBirth);

        if (hourlyRate < 0) {
            throw new InvalidDataException("Hourly rate cannot be negative.");
        }

        this.hourlyRate = hourlyRate;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.contractEndDate = contractEndDate;
    }

    @Override
    public double getPay() {
        return hourlyRate * maxHoursPerWeek;
    }

    @Override
    public void reportToManager() {
        System.out.println("[PartTime] " + getFullName() + " reporting to manager.");
        super.reportToManager();
    }
}
