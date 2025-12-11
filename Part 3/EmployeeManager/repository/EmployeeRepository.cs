using System;
using System.Collections.Generic;
using System.IO;
using EmployeeManager.Models;
using EmployeeManager.Exceptions;

namespace EmployeeManager.repository
{
    public class EmployeeRepository
    {
        public List<Employee> LoadEmployees(string filePath)
        {
            if(!File.Exists(filePath))
                throw new FileStorageException($"File does not exist: {filePath}");

            var employees = new List<Employee>();

            try
            {
                var lines = File.ReadAllLines(filePath);

                foreach(var line in lines)
                {
                    if(string.IsNullOrWhiteSpace(line))
                        continue;
                    
                    var parts = line.Split(',');

                    if (parts.Length < 3){
                        Console.Error.WriteLine($"Skipping malformed line: {line}");
                        continue;
                    }

                    int id = int.Parse(parts[0].Trim());
                    string fullName = parts[1].Trim();
                    double pay = double.Parse(parts[4].Trim());

                    var nameParts = fullName.Split(' ');
                    string first = nameParts[0];
                    string last = nameParts.Length > 1 ? nameParts[1] : "";

                    var employee = new AnonymousEmployee(id, first, last, pay);

                    employees.Add(employee);
                }

                Console.WriteLine($"Successfully loaded {employees.Count} employees from {filePath}");
                return employees;
            }
            catch (IOException ex)
            {
                throw new FileStorageException($"Error reading file {filePath}", ex);
            }
        }

        public void SaveEmployees(string filePath, List<Employee> employees)
        {
            var lines = new List<string>();

            foreach (var employee in employees)
            {
                lines.add($"{employee.EmployeeId}, {employee.FullName}, {employee.Position}, {employee.Department}, {employee.Pay()}");
            }

            try
            {
                File.WriteAllLines(filePath, lines);
                Console.WriteLine($"Saved {employees.Count} employees to {filePath}");
            }

            catch (IOException ex)
            {
                throw new FileStorageException($"Error writing file {filePath}", ex);
            }      
        }

        private class AnonymousEmployee : Employee
        {
            private readonly double loadedPay;

            public AnonymousEmployee(int employeeId, string firstName, string lastName, double loadedPay) : base(employeeId, firstName, lastName, DateTime.Now, new DateTime(2000, 1, 1))
            {
                this.loadedPay = loadedPay;
            }

            public override double Pay() => loadedPay;
        }
    }
}