using System;

namespace EmployeeManager.Models
{
    public class FullTimeEmployee : Employee
    {
     public double AnnualSalary { get; set; }
     public int VacationDays { get; set; }
     public string Benefits { get; set; }

     public FullTimeEmployee(int employeeId, string firstName, string lastName, DateTime startDate, DateTime dateOfBirth, double annualSalary, int vacationDays, string benefits) : base(employeeId, firstName, lastName, startDate, dateOfBirth)
        {
            AnnualSalary = annualSalary;
            VacationDays = vacationDays;
            Benefits = benefits;
        }

        public override double GetPay() => AnnualSalary / 12.0;

        public override void ReportToManager()
        {
            Console.WriteLine($"[FullTime] {FullName} reporting to manager.");
            base.ReportToManager();
        }   
    }
}