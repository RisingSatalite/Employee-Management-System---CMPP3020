using System;

namespace EmployeeManager.Exceptions
{
    public class FileStorageException : AppException
    {
        public FileStorageException(string message) : base(message) { }

        public FileStorageException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}
