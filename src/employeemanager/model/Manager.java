package employeemanager.model;

import employeemanager.exceptions.InvalidDataException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager extends FullTimeEmployee {

    private String managementPosition;
    private final List<Employee> team = new ArrayList<>();

    public Manager(int employeeId,
                   String firstName,
                   String lastName,
                   LocalDate startDate,
                   LocalDate dateOfBirth,
                   double annualSalary,
                   int vacationDays,
                   String benefits,
                   String managementPosition) throws InvalidDataException {
        super(employeeId, firstName, lastName, startDate, dateOfBirth,
              annualSalary, vacationDays, benefits);
        this.managementPosition = managementPosition;
    }

    public int getTeamSize() {
        return team.size();
    }

    public void addTeamMember(Employee employee) {
        if (!team.contains(employee)) {
            team.add(employee);
            employee.setManager(this);
        }
    }

    public void removeTeamMember(Employee employee) {
        team.remove(employee);
        if (employee.getManager() == this) {
            employee.setManager(null);
        }
    }

    public List<Employee> getTeam() {
        return Collections.unmodifiableList(team);
    }
    
    @Override
    public void reportToManager() {
        System.out.println("[Manager] " + getFullName() + " reports to director/VP.");
    }
}
