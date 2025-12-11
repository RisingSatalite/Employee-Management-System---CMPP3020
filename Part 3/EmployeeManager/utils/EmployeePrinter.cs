using System;
using EmployeeManager.Models;

namespace EmployeeManager.util
{
    public static class EmployeePrinter
    {
        public static void Print(Employee employee)
        {
            Console.WriteLine($"[Employee] {employee.FullName} pay= {employee.GetPay()}");
        }

        public static void Print(Manager manager)
        {
            Console.WriteLine(
                $"[Manager] {manager.FullName} Team Size= {manager.Team.Count} Pay= {manager.GetPay()}"
            );
        }

        // Fully-qualified type name to avoid any namespace confusion
        public static void Print(EmployeeManager.Models.PartTimeEmployee partTime)
        {
            Console.WriteLine($"[PartTime] {partTime.FullName} Weekly pay= {partTime.GetPay()}");
        }
    }
}
