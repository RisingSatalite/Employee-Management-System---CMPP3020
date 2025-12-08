package employeemanager.util;

import employeemanager.model.Employee;
import employeemanager.model.Manager;
import employeemanager.model.PartTimeEmployee;

public class EmployeePrinter {

    public static void print(Employee employee) {
        System.out.println("[Employee] " + employee.getFullName() + 
        		" pay=" + employee.getPay());
    }

    public static void print(Manager manager) {
        System.out.println("[Manager] " + manager.getFullName() +
                " teamSize=" + manager.getTeamSize() +
                " pay=" + manager.getPay());
    }

    public static void print(PartTimeEmployee parttime) {
        System.out.println("[PartTime] " + parttime.getFullName() +
                " weeklyPay=" + parttime.getPay());
    }
}