using System;
using InvalidDataException = EmployeeManager.Exceptions.InvalidDataException;

namespace EmployeeManager.Models
{
    public class PartTimeEmployee : Employee
    {
        public double HourlyRate { get; set; }
        public int MaxHoursPerWeek { get; set; }
        public DateTime ContractEndDate { get; set; }

        public PartTimeEmployee(
            int employeeId,
            string firstName,
            string lastName,
            DateTime startDate,
            DateTime dateOfBirth,
            double hourlyRate,
            int maxHoursPerWeek,
            DateTime contractEndDate)
            : base(employeeId, firstName, lastName, startDate, dateOfBirth)
        {
            if (hourlyRate < 0)
                throw new InvalidDataException("Hourly rate cant be negative");

            HourlyRate = hourlyRate;
            MaxHoursPerWeek = maxHoursPerWeek;
            ContractEndDate = contractEndDate;
        }

        public override double GetPay() => HourlyRate * MaxHoursPerWeek;

        public override void ReportToManager()
        {
            Console.WriteLine($"[PartTime] {FullName} reporting to manager");
            base.ReportToManager();
        }
    }
}
