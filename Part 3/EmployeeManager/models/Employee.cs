using System;

namespace EmployeeManager.Models
{
    public abstract class Employee
    {
        public int EmployeeId { get; private set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime StartDate { get; private set;}
        public DateTime DateOfBirth { get; private set;}

        public Department Department { get; set; }
        public Position Position { get; set; }
        public Manager Manager{ get; set; }

        protected Employee(int employeeId, string firstName, string lastName, DateTime startDate, DateTime dateOfBirth)
        {
            if (employeeId <= 0) throw new Exceptions.InvalidDataException("Employee ID must be positive.");
            if (string.IsNullOrEmpty(firstName)) throw new Exceptions.InvalidDataException("First name is required");

            EmployeeId = employeeId;
            FirstName = firstName;
            LastName = lastName;
            StartDate = startDate;
            DateOfBirth = dateOfBirth;
        }
    
        public string FullName => $"{FirstName} {LastName}";

        public abstract double GetPay();

        public virtual void ReportToManager()
        {
            if (Manager != null)
                Console.WriteLine($"{FullName} reports to manager {Manager.FullName}");
            else
                Console.WriteLine($"{FullName} is not assigned to a manager");
        }

        public override string ToString()
        {
            return $"Employee{{id={EmployeeId}, name='{FullName}', position={(Position?.Title ?? "None")}, department={(Department?.Name ?? "None")}}}";
        }
    }
}