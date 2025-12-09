using System;

namespace EmployeeManager.Models
{
    public class Instructor : FullTimeEmployee
    {
        public Instructor(int employeeId, string firstName, string lastName, DateTime startDate, DateTime dateOfBirth, double annualSalary, int vacationDays, string benefits) : base(employeeId, firstName, lastName, startDate, dateOfBirth, annualSalary, vacationDays, benefits) { }

        public void Teach() => Console.WriteLine($"{FullName} is instructing a class");

        public override void ReportToManager() => Console.WriteLine($"[Instructor] {FullName} reports to the department chair");
    }
}