using System;
using System.Collections.Generic;
using System.Threading.Tasks;

using EmployeeManager.Models;
using EmployeeManager.util;
using EmployeeManager.Exceptions;
using EmployeeManager.Repository;
using EmployeeManager.Tasks;

namespace EmployeeManager
{
    internal class Program
    {
        private const string DefaultDataFile = "employees.csv";

        static async Task Main(string[] args)
        {
            Console.WriteLine("=== Employee Manager Demo ===\n");

            // 1. Create departments and positions
            var departments = CreateDepartments();
            var positions = CreatePositions();

            // 2. Create some sample employees and wire relationships
            var employees = CreateSampleEmployees(departments, positions);

            Console.WriteLine("In-memory employees:\n");
            foreach (var e in employees)
            {
                EmployeePrinter.Print(e);   // overloads for Manager / PartTimeEmployee will kick in
            }

            Console.WriteLine("\nReporting relationships:\n");
            foreach (var e in employees)
            {
                e.ReportToManager();
            }

            // 3. Run payroll using a PayrollEmployee
            var payrollEmployee = FindPayrollEmployee(employees);
            if (payrollEmployee != null)
            {
                Console.WriteLine("\nProcessing payroll:\n");
                foreach (var e in employees)
                {
                    payrollEmployee.ProcessPayroll(e);
                }
            }

            // 4. Show an InvalidDataException example
            TryCreateInvalidPartTimeEmployee();

            // 5. Save employees to file, then load them back
            var repository = new EmployeeRepository();

            Console.WriteLine($"\nSaving employees to '{DefaultDataFile}'...");
            var saveTask = new FileTask(
                DefaultDataFile,
                FileTask.Mode.WRITE,
                employees,
                repository
            );
            await saveTask.RunAsync();

            Console.WriteLine($"\nLoading employees from '{DefaultDataFile}'...");
            var loadedEmployees = new List<Employee>();
            var loadTask = new FileTask(
                DefaultDataFile,
                FileTask.Mode.READ,
                loadedEmployees,
                repository
            );
            await loadTask.RunAsync();

            Console.WriteLine("\nEmployees loaded from file:");
            foreach (var e in loadedEmployees)
            {
                EmployeePrinter.Print(e);
            }

            Console.WriteLine("\nDemo complete. Press any key to exit.");
            Console.ReadKey();
        }

        // ---------------- helper methods ----------------

        private static Dictionary<string, Department> CreateDepartments()
        {
            var departments = new Dictionary<string, Department>();

            var it = new Department(1, "IT");
            var hr = new Department(2, "HR");
            var teaching = new Department(3, "Teaching");

            departments["IT"] = it;
            departments["HR"] = hr;
            departments["Teaching"] = teaching;

            return departments;
        }

        private static Dictionary<string, Position> CreatePositions()
        {
            var positions = new Dictionary<string, Position>
            {
                ["Developer"] = new Position(1, "Developer"),
                ["Manager"] = new Position(2, "Manager"),
                ["Instructor"] = new Position(3, "Instructor"),
                ["Payroll"] = new Position(4, "Payroll Specialist"),
                ["PartTime"] = new Position(5, "Part-Time Staff")
            };

            return positions;
        }

        private static List<Employee> CreateSampleEmployees(
            Dictionary<string, Department> departments,
            Dictionary<string, Position> positions)
        {
            var employees = new List<Employee>();

            var itDept = departments["IT"];
            var hrDept = departments["HR"];
            var teachingDept = departments["Teaching"];

            // Manager
            var manager = new Manager(
                employeeId: 1,
                firstName: "Alice",
                lastName: "Anderson",
                startDate: new DateTime(2020, 1, 15),
                dateOfBirth: new DateTime(1985, 5, 3),
                annualSalary: 110000,
                vacationDays: 25,
                benefits: "Full benefits",
                managementPosition: "IT Manager"
            );
            manager.Department = itDept;
            manager.Position = positions["Manager"];
            itDept.AddEmployee(manager);
            employees.Add(manager);

            // Full-time developer
            var dev = new FullTimeEmployee(
                employeeId: 2,
                firstName: "Bob",
                lastName: "Smith",
                startDate: new DateTime(2021, 3, 1),
                dateOfBirth: new DateTime(1990, 9, 10),
                annualSalary: 90000,
                vacationDays: 20,
                benefits: "Health + Dental"
            );
            dev.Department = itDept;
            dev.Position = positions["Developer"];
            dev.Manager = manager;
            itDept.AddEmployee(dev);
            manager.AddTeamMember(dev);
            employees.Add(dev);

            // Part-time employee
            var partTime = new PartTimeEmployee(
                employeeId: 3,
                firstName: "Charlie",
                lastName: "Lee",
                startDate: new DateTime(2022, 9, 15),
                dateOfBirth: new DateTime(2001, 2, 20),
                hourlyRate: 30.0,
                maxHoursPerWeek: 20,
                contractEndDate: new DateTime(2026, 12, 31)
            );
            partTime.Department = hrDept;
            partTime.Position = positions["PartTime"];
            partTime.Manager = manager;
            hrDept.AddEmployee(partTime);
            manager.AddTeamMember(partTime);
            employees.Add(partTime);

            // Instructor
            var instructor = new Instructor(
                employeeId: 4,
                firstName: "Dana",
                lastName: "Wu",
                startDate: new DateTime(2019, 9, 1),
                dateOfBirth: new DateTime(1988, 7, 22),
                annualSalary: 80000,
                vacationDays: 15,
                benefits: "Standard benefits"
            );
            instructor.Department = teachingDept;
            instructor.Position = positions["Instructor"];
            instructor.Manager = manager;
            teachingDept.AddEmployee(instructor);
            manager.AddTeamMember(instructor);
            employees.Add(instructor);

            // Payroll employee
            var payroll = new PayrollEmployee(
                employeeId: 5,
                firstName: "Evan",
                lastName: "Garcia",
                startDate: new DateTime(2018, 4, 10),
                dateOfBirth: new DateTime(1984, 11, 5),
                annualSalary: 75000,
                vacationDays: 15,
                benefits: "Extended health"
            );
            payroll.Department = hrDept;
            payroll.Position = positions["Payroll"];
            payroll.Manager = manager;
            hrDept.AddEmployee(payroll);
            manager.AddTeamMember(payroll);
            employees.Add(payroll);

            return employees;
        }

        private static PayrollEmployee? FindPayrollEmployee(IEnumerable<Employee> employees)
        {
            foreach (var e in employees)
            {
                if (e is PayrollEmployee p)
                    return p;
            }
            return null;
        }

        private static void TryCreateInvalidPartTimeEmployee()
        {
            Console.WriteLine("\nTesting InvalidDataException using PartTimeEmployee with negative rate:");
            try
            {
                var invalid = new PartTimeEmployee(
                    employeeId: 999,
                    firstName: "Test",
                    lastName: "Negative",
                    startDate: DateTime.Today,
                    dateOfBirth: new DateTime(2000, 1, 1),
                    hourlyRate: -5.0,          // invalid on purpose
                    maxHoursPerWeek: 10,
                    contractEndDate: DateTime.Today.AddYears(1)
                );
            }
            catch (InvalidDataException ex)
            {
                Console.WriteLine($"Caught InvalidDataException: {ex.Message}");
            }
            catch (AppException ex)
            {
                Console.WriteLine($"Caught AppException: {ex.Message}");
            }
        }
    }
}
