package employeemanager.app;

import employeemanager.model.*;
import employeemanager.repository.EmployeeRepository;
import employeemanager.tasks.FileTask;
import employeemanager.util.EmployeePrinter;
import employeemanager.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmployeeApp {

    private static final String FILE_PATH = "employees.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EmployeeRepository repository = new EmployeeRepository();
        List<Employee> employees = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        List<Position> positions = new ArrayList<>();

        preloadBaseDepartments(departments);
        preloadBasePositions(positions);
        preloadBaseEmployees(employees, positions, departments);

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addEmployeeMenu(scanner, employees, positions, departments);
                    case "2" -> viewEmployees(employees);
                    case "3" -> deleteEmployeeMenu(scanner, employees);
                    case "4" -> saveEmployeesToFile(repository, employees);
                    case "5" -> loadEmployeesFromFile(repository, employees);
                    case "6" -> viewEmployeeManagerAssignments(employees);
                    case "7" -> runPolymorphismDemo(employees);
                    case "8" -> runBackgroundSaveWithFileTask(repository, employees);
                    case "0" -> {
                        System.out.println("Exiting program...");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (AppException error) {
                System.err.println("Error: " + error.getMessage());
            } catch (NumberFormatException error) {
                System.err.println("Invalid number format. Please try again.");
            }
        }

        scanner.close();
    }

    // ===== Menu & basic actions =====

    private static void printMenu() {
        System.out.println("\n=== Employee Management Menu ===");
        System.out.println("1. Add employee");
        System.out.println("2. View employees");
        System.out.println("3. Delete employee");
        System.out.println("4. Save employees to file");
        System.out.println("5. Load employees from file");
        System.out.println("6. View employees and their managers");
        System.out.println("7. Run polymorphism/static-binding demo");
        System.out.println("8. Background save using FileTask (Runnable)");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void viewEmployees(List<Employee> employees) {
        System.out.println("\n=== Current Employees ===");

        if (employees.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }

        for (Employee employee : employees) {
            System.out.printf(
                    "ID: %d, Name: %s, Type: %s, Pay: %.2f%n",
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    employee.getClass().getSimpleName(),
                    employee.getPay()
            );
        }
    }

    private static void deleteEmployeeMenu(Scanner scanner, List<Employee> employees) {
        System.out.print("\nEnter employee ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Employee toRemove = null;
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == id) {
                toRemove = employee;
                break;
            }
        }

        if (toRemove != null) {
            employees.remove(toRemove);
            System.out.println("Removed employee: " + toRemove.getFullName());
        } else {
            System.out.println("No employee found with ID " + id);
        }
    }

    private static void saveEmployeesToFile(EmployeeRepository repository,
                                            List<Employee> employees)
            throws AppException {

        if (employees.isEmpty()) {
            System.out.println("No employees to save.");
            return;
        }

        repository.saveEmployees(FILE_PATH, employees);
    }

    private static void loadEmployeesFromFile(EmployeeRepository repository,
                                              List<Employee> employees)
            throws AppException {
        List<Employee> loaded = repository.loadEmployees(FILE_PATH);
        employees.clear();
        employees.addAll(loaded);
        System.out.println("Loaded " + loaded.size() + " employees into the system.");
    }

    // ===== Adding employees =====

    private static void addEmployeeMenu(Scanner scanner, List<Employee> employees, List<Position> positions, List<Department> departments)
            throws InvalidDataException {

        System.out.println("\nAdd Employee");
        System.out.println("a) Full-time");
        System.out.println("b) Part-time");
        System.out.print("Select type (a/b): ");
        String type = scanner.nextLine().trim().toLowerCase();

        // Ensure unique ID
        int id = getUniqueEmployeeId(scanner, employees);

        System.out.print("Enter first name: ");
        String first = scanner.nextLine().trim();

        System.out.print("Enter last name: ");
        String last = scanner.nextLine().trim();

        // Dummy dates for prototype
        LocalDate startDate = LocalDate.now();
        LocalDate dob = LocalDate.of(2000, 1, 1);
        
        
        System.out.print("Position");
        for (int i = 0; i < positions.size(); i++) {
            System.out.println("Enter " + i + " for " + positions.get(i).getTitle());
        }
        System.out.print("Enter the number to select, 0 for no position at this time");
        int number = Integer.parseInt(scanner.nextLine().trim());
        Position selectedPosition = null;
        if (number >= 1 && number <= positions.size()) {
        	selectedPosition = positions.get(number-1);
        }

        System.out.print("Department");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println("Enter " + i + " for " + departments.get(i).getName());
        }
        System.out.print("Enter the number to select, 0 for no position at this time");
        number = Integer.parseInt(scanner.nextLine().trim());
        Department selectedDepartment = null;
        if (number >= 1 && number <= positions.size()) {
        	selectedDepartment = departments.get(number-1);
        }

        Employee newEmployee = null;

        if (type.equals("a")) {
            // ---------- FULL-TIME ----------
            System.out.println("\nSelect full-time role:");
            System.out.println("1) Regular FullTimeEmployee");
            System.out.println("2) Manager");
            System.out.println("3) Instructor");
            System.out.println("4) PayrollEmployee");
            System.out.print("Choice: ");
            String roleChoice = scanner.nextLine().trim();

            System.out.print("Enter annual salary: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter vacation days: ");
            int vacation = Integer.parseInt(scanner.nextLine().trim());

            String benefits = collectBenefits(scanner);  // menu-based benefits

            switch (roleChoice) {
            case "2":
                newEmployee = new Manager(
                        id, first, last, startDate, dob,
                        salary, vacation, benefits, "Manager", selectedPosition, selectedDepartment
                );
                break;

            case "3":
                newEmployee = new Instructor(
                        id, first, last, startDate, dob,
                        salary, vacation, benefits, selectedPosition, selectedDepartment
                );
                break;

            case "4":
                newEmployee = new PayrollEmployee(
                        id, first, last, startDate, dob,
                        salary, vacation, benefits, selectedPosition, selectedDepartment
                );
                break;

            case "1":
            default:
                newEmployee = new FullTimeEmployee(
                        id, first, last, startDate, dob,
                        salary, vacation, benefits, selectedPosition, selectedDepartment
                );
                break;
        }

        } else if (type.equals("b")) {
            // ---------- PART-TIME ----------
            System.out.print("Enter hourly rate: ");
            double hourly = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter max hours per week: ");
            int hours = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter contract end year (e.g. 2026): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter contract end month (1–12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter contract end day (1–31): ");
            int day = Integer.parseInt(scanner.nextLine().trim());

            LocalDate contractEnd = LocalDate.of(year, month, day);

            PartTimeEmployee parttime = new PartTimeEmployee(
                    id, first, last, startDate, dob,
                    hourly, hours, contractEnd, selectedPosition, selectedDepartment
            );

            System.out.print("Is this part-time employee an Instructor? (y/n): ");
            String isInstructor = scanner.nextLine().trim().toLowerCase();
            if (isInstructor.equals("y")) {
                System.out.println("Marked as a part-time Instructor.");
            }

            newEmployee = parttime;

        } else {
            System.out.println("Invalid type. Cancelled adding employee.");
            return;
        }

        employees.add(newEmployee);
        System.out.println("Employee added: " + newEmployee.getFullName());

        assignManagerForEmployee(scanner, employees, newEmployee);
    }

    // ===== Helpers =====

    private static int getUniqueEmployeeId(Scanner scanner, List<Employee> employees) {
        while (true) {
            System.out.print("Enter employee ID: ");
            String input = scanner.nextLine().trim();

            try {
                int id = Integer.parseInt(input);

                boolean exists = false;
                for (Employee e : employees) {
                    if (e.getEmployeeId() == id) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("An employee with ID " + id + " already exists. Please choose another.");
                } else {
                    return id;
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Please enter a valid integer ID.");
            }
        }
    }

    private static String collectBenefits(Scanner scanner) {
        List<String> availableBenefits = Arrays.asList(
                "Health Insurance",
                "Dental",
                "Gym Membership"
        );

        List<String> selected = new ArrayList<>();
        boolean adding = true;

        while (adding) {
            System.out.println("\nSelect a benefit to add:");
            for (int i = 0; i < availableBenefits.size(); i++) {
                System.out.println((i + 1) + ") " + availableBenefits.get(i));
            }
            System.out.println("0) Done");

            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) {
                    adding = false;
                } else if (choice >= 1 && choice <= availableBenefits.size()) {
                    String benefit = availableBenefits.get(choice - 1);

                    if (!selected.contains(benefit)) {
                        selected.add(benefit);
                        System.out.println("Added: " + benefit);
                    } else {
                        System.out.println("Already added.");
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (selected.isEmpty()) {
            return "None";
        }

        return String.join(", ", selected);
    }

    private static void assignManagerForEmployee(Scanner scanner,
                                                 List<Employee> employees,
                                                 Employee newEmployee) {

        List<Manager> managers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                managers.add((Manager) employee);
            }
        }

        if (managers.isEmpty()) {
            System.out.println("No managers available to assign.");
            return;
        }

        System.out.println("\nAvailable managers:");
        for (Manager manager : managers) {
            System.out.println("ID: " + manager.getEmployeeId() +
                    ", Name: " + manager.getFullName());
        }

        System.out.print("Enter manager ID to assign (or 0 for no manager): ");
        String input = scanner.nextLine().trim();

        try {
            int managerId = Integer.parseInt(input);
            if (managerId == 0) {
                System.out.println("No manager assigned.");
                return;
            }

            Manager selected = null;
            for (Manager manager : managers) {
                if (manager.getEmployeeId() == managerId) {
                    selected = manager;
                    break;
                }
            }

            if (selected == null) {
                System.out.println("No manager found with ID " + managerId + ". No manager assigned.");
            } else {
                newEmployee.setManager(selected);
                selected.addTeamMember(newEmployee);
                System.out.println("Assigned manager " + selected.getFullName() +
                        " to employee " + newEmployee.getFullName());
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. No manager assigned.");
        }
    }

    // ===== Preload base employees =====

    private static void preloadBaseEmployees(List<Employee> employees, List<Position> positions, List<Department> department) {
        try {
            FullTimeEmployee alice = new FullTimeEmployee(
                    1001, "Alice", "Smith",
                    LocalDate.of(2022, 1, 10),
                    LocalDate.of(1995, 3, 15),
                    80000, 15, "Health Insurance, Dental",
                    positions.get(2), department.get(2)
            );
            employees.add(alice);

            PartTimeEmployee bob = new PartTimeEmployee(
                    1002, "Bob", "Jones",
                    LocalDate.of(2023, 5, 1),
                    LocalDate.of(2000, 7, 20),
                    25.0, 20,
                    LocalDate.of(2026, 5, 1),
                    positions.get(2), department.get(2)
            );
            employees.add(bob);

            Manager carol = new Manager(
                    1003, "Carol", "Brown",
                    LocalDate.of(2020, 9, 1),
                    LocalDate.of(1990, 1, 5),
                    95000, 20, "Health Insurance, Dental, Retirement Plan", 
                    "IT Manager", positions.get(4), department.get(3)
            );
            employees.add(carol);

            Instructor dan = new Instructor(
                    1004, "Dan", "Green",
                    LocalDate.of(2019, 9, 1),
                    LocalDate.of(1988, 10, 10),
                    90000, 20, "Health Insurance, Dental",
                    positions.get(2), department.get(2)
            );
            employees.add(dan);

            PayrollEmployee eve = new PayrollEmployee(
                    1005, "Eve", "White",
                    LocalDate.of(2021, 3, 1),
                    LocalDate.of(1992, 11, 11),
                    85000, 15, "Health Insurance, Dental",
                    positions.get(1), department.get(1)
            );
            employees.add(eve);

            // Link some initial manager relationships
            carol.addTeamMember(alice);
            carol.addTeamMember(bob);

            System.out.println("Preloaded " + employees.size() + " employees into the system.");

        } catch (InvalidDataException error) {
            System.err.println("Error creating base employees: " + error.getMessage());
        }
    }
    
    private static void preloadBaseDepartments(List<Department> departments) {
    	Department department1 = new Department(1,"Payroll");
    	Department department2 = new Department(2,"IT");
    	Department department3 = new Department(3,"Staff");
    	Department department4 = new Department(4,"Administration");
    	departments.add(department1);
    	departments.add(department2);
    	departments.add(department3);
    	departments.add(department4);
    }
    
    private static void preloadBasePositions(List<Position> positions) {
    	Position position1 = new Position(1,"Accountant");
    	Position position2 = new Position(2,"IT Staff");
    	Position position3 = new Position(3,"Teacher");
    	Position position4 = new Position(4,"Administrator");
    	Position position5 = new Position(5,"Manager");
    	positions.add(position1);
    	positions.add(position2);
    	positions.add(position3);
    	positions.add(position4);
    	positions.add(position5);
    }
    
    private static void viewEmployeeManagerAssignments(List<Employee> employees) {
        System.out.println("\n=== Employees & Their Managers ===");

        if (employees.isEmpty()) {
            System.out.println("No employees in the system.");
            return;
        }

        for (Employee employee : employees) {
            String managerName = (employee.getManager() != null)
                    ? employee.getManager().getFullName()
                    : "None";

            System.out.printf(
                    "ID: %d, Name: %s, Manager: %s%n",
                    employee.getEmployeeId(),
                    employee.getFullName(),
                    managerName
            );
        }
    }
    
    private static void runPolymorphismDemo(List<Employee> employees) {
        System.out.println("\n=== Polymorphism & Static Binding Demo ===");

        if (employees.isEmpty()) {
            System.out.println("No employees loaded.");
            return;
        }

        System.out.println("\n-- Dynamic Binding (Overridden Methods) --");
        for (Employee employee : employees) {
            System.out.println(
                    employee.getClass().getSimpleName() + " | " +
                    employee.getFullName() + " | Pay=" + employee.getPay()
            );
            employee.reportToManager(); 
        }

        System.out.println("\n-- Static Binding (Method Overloading via EmployeePrinter) --");
        for (Employee employee : employees) {
            EmployeePrinter.print(employee);
        }

        System.out.println("\n=== End of Demo ===");
    }

    private static void runBackgroundSaveWithFileTask(EmployeeRepository repository,
    		List<Employee> employees) {
    	
    	System.out.println("\n=== Background Save Demo ===");
    	
    	if (employees.isEmpty()) {
    		System.out.println("No employees to save.");
    		return;
    	}
    	
    	FileTask task = new FileTask(
    			FILE_PATH,
    			FileTask.Mode.WRITE,
    			employees,
    			repository
    			);
    	
    	Thread thread = new Thread(task);
    	System.out.println("Starting background save thread...\n");
    	thread.start();
    	
    	System.out.println("Main program continuing while saving occurs in background. ");
    	
    }
}
