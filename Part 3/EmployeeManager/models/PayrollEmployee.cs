using System;

namespace EmployeeManager.Models
{
    public class PayrollEmployee : FullTimeEmployee
    {
        public PayrollEmployee(int employeeId, string firstName, string lastName, DateTime startDate, DateTime dateOfBirth, double annualSalary, int vacationDays, string benefits) : base(employeeId, firstName, lastName, startDate, dateOfBirth, annualSalary, vacationDays, benefits) { }

        public void ProcessPayroll (Employee employee)
        {
            Console.WriteLine($"Processing payroll for {employee.FullName} Amount: {employee.GetPay()}");
        } 

        public override void ReportToManager() => Console.WriteLine($"[Payroll] {FullName} reports to the finance Manager");
    }
}