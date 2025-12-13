using System;

namespace EmployeeManager.Exceptions
{
    public class CalculationException : AppException
    {
        public CalculationException(string message) : base(message) { }

        public CalculationException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}
