using System;
using System.Collections.Generic;
using System.IO;
using EmployeeManager.model;
using EmployeeManager.exceptions;

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
                var lines = filePath.ReadAllLines(filePath);

                foreach(var line in lines)
                {
                    if(string.isNullOrWhiteSpace(line))
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
    }
}