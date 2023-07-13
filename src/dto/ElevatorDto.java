package dto;

public class ElevatorDto {
    private int number;
    private int maxWeight;
    private int maxFloor;
    private int minFloor;
    private int autoCloseTime;
    private int stopTime;

    public ElevatorDto(int number, int maxWeight, int maxFloor, int minFloor,
                       int autoCloseTime, int stopTime) {
        this.number = number;
        this.maxWeight = maxWeight;
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
        this.autoCloseTime = autoCloseTime;
        this.stopTime = stopTime;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getAutoCloseTime() {
        return autoCloseTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public int getNumber() {
        return number;
    }
}
