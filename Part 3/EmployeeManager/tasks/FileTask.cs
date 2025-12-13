using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using EmployeeManager.Models;
using EmployeeManager.Repository;
using EmployeeManager.Exceptions;

namespace EmployeeManager.Tasks
{
    public class FileTask
    {
        public enum Mode { READ, WRITE }

        private readonly string filePath;
        private readonly Mode mode;
        private readonly List<Employee> employees;
        private readonly EmployeeRepository repository;

        public FileTask(string filePath, Mode mode, List<Employee> employees, EmployeeRepository repository)
        {
            this.filePath = filePath;
            this.mode = mode;
            this.employees = employees;
            this.repository = repository;
        }

        public async Task RunAsync()
        {
            try
            {
                switch (mode)
                {
                    case Mode.READ:
                        var loaded = repository.LoadEmployees(filePath);
                        employees.Clear();
                        employees.AddRange(loaded);
                        break;

                    case Mode.WRITE:
                        repository.SaveEmployees(filePath, employees);
                        break;
                }
            }
            catch (AppException ex)
            {
                Console.Error.WriteLine($"FileTask Error ({mode}): {ex.Message}");
            }
        }
    }
}
