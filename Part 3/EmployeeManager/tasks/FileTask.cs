using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using EmployeeManager.model;
using EmployeeManager.repository;
using EmployeeManager.exceptions;

namespace EmployeeManager.Tasks
{
    public class FileTask
    {
        public enum Mode { READ, WRITE }

        private readonly string filePath;
        private readonly Mode mode;
        private readonly List<Employee> employees;
        private readonly EmployeeRepository repository;


    public async Task RunAsync()
        {
            try
            {
                switch (mode)
                {
                    case Mode.READ:
                        repository.LoadEmployees(filePath);
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