using System;
using System.Collections.Generic;

namespace EmployeeManager.Models
{
    public class Manager : FullTimeEmployee
    {
        public string ManagementPosition { get; set; }
        private readonly List<Employee> _team = new List<Employee>();

        public Manager(int employeeId, string firstName, string lastName, DateTime startDate, DateTime dateOfBirth, double annualSalary, int vacationDays, string benefits, string managementPosition) : base(employeeId, firstName, lastName, startDate, dateOfBirth, annualSalary, vacationDays, benefits, managementPosition)
        {
            ManagementPosition = managementPosition;
        }

        public int TeamSize => _team.Count;

        public void AddTeamMember(EmployeeManager employee)
        {
            if (!_team.Contains(employee))
            {
                _team.Add(employee);
                employee.Manager = this;
            }
        }

        public void RemoveTeamMember (EmployeeManager employee)
        {
            _team.Remove(employee);
            if (employee.Manager == this)
                employee.Manager = null;
        }

        public IReadOnlyLost<Employee> Team => _team.AsReadOnly();

        public override void ReportToManager() => Console.WriteLine($"[Manager] {FullName} reports to director");
    }
}