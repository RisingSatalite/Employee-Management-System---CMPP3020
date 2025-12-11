using System;
using EmployeeManager.model;

namespace EmployeeManager.util
{
    public class EmployeePrinter
    {
        public static void Print(Employee employee)
        {
            Console.WriteLine($"[Employee] {employee.FullName()} pay= {employee.Pay()}");
        }

        public static void Print(Manager manager)
        {
            Console.WriteLine($"[Manager] {manager.FullName()} Team Size= {manager.Team.Count} Pay= {manager.Pay()}");
        }

        public static void Print(PartTimeEmployee partTime)
        {
            Console.WriteLine($"[PartTime] {partTime.FullName()} Weekly pay= {partTime.Pay()}");
        }
    }
}