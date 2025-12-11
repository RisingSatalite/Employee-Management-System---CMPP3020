using System;

namespace EmployeeManager.Exceptions
{
    public class InvalidDataException : AppException
    {
        public InvalidDataException(string message) : base(message) { }

        public InvalidDataException(string message, Exception inner)
            : base(message, inner) { }
    }
}
