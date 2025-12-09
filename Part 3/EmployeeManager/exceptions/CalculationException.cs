namespace EmployeeManager.Exceptions
{
    public class CalculationException : AppException
    {
        public CalculationException(string message) : base(message) { }
        public CalculationException(string message, Exceptions inner) : base(message, inner) { }
    }
}