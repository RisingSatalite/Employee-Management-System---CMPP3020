package employeemanager.model;

public class Position {
    private int positionId;
    private String title;

    public Position(int positionId, String title) {
        this.positionId = positionId;
        this.title = title;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getTitle() {
        return title;
    }
}
