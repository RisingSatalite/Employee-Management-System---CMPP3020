using System;
using InvalidDataException = EmployeeManager.Exceptions.InvalidDataException;


namespace EmployeeManager.Models
{
    public abstract class Employee
    {
        public int EmployeeId { get; private set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime StartDate { get; private set; }
        public DateTime DateOfBirth { get; private set; }

        public Department? Department { get; set; }
        public Position? Position { get; set; }
        public Manager? Manager { get; set; }

        public string FullName => $"{FirstName} {LastName}";

        protected Employee(int employeeId, string firstName, string lastName,
                           DateTime startDate, DateTime dateOfBirth)
        {
            if (employeeId <= 0)
                throw new InvalidDataException("Employee ID must be positive.");
            if (string.IsNullOrWhiteSpace(firstName))
                throw new InvalidDataException("First name is required.");

            EmployeeId = employeeId;
            FirstName = firstName;
            LastName = lastName;
            StartDate = startDate;
            DateOfBirth = dateOfBirth;
        }

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
            var positionTitle = Position?.Title ?? "None";
            var deptName = Department?.Name ?? "None";
            return $"Employee{{id={EmployeeId}, name='{FullName}', position={positionTitle}, department={deptName}}}";
        }
    }
}
