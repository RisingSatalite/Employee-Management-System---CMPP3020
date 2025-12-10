namespace EmployeeManager.Models
{
    public class Position
    {
        public int PositionId { get; set; }
        public string Title { get; set; }

        public Position(int positionId, string title)
        {
            PositionId = positionId;
            Title = title;
        }
    }
}