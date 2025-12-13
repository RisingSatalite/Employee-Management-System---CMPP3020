using System.Collections.Generic;

namespace EmployeeManager.Models
{
    public class Department
    {
        public int DepartmentId { get; set; }
        public string Name { get; set; }
        public readonly List<Employee> _employees = new();

        public Department(int departmentId, string name)
        {
            DepartmentId = departmentId;
            Name = name;
        } 

        public void AddEmployee(Employee employee)
        {
            if (!_employees.Contains(employee))
            {
                _employees.Add(employee);
                employee.Department = this;
            }
        }

        public IReadOnlyList<Employee> Employees => _employees.AsReadOnly();
    }
}