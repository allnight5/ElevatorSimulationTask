package entity;

public class Passenger {
    //탑승 엘리베이터 번호
    private int elevatorNumber;
    //탑승 층
    private int boardingFloor;
    //하차 층
    private int destinationFloor;
    //몸무게
    private int weight;

    public Passenger(int elevatorNumber, int boardingFloor, int destinationFloor, int weight) {
        this.elevatorNumber = elevatorNumber;
        this.boardingFloor = boardingFloor;
        this.weight = weight;
        this.destinationFloor = destinationFloor;
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public int getBoardingFloor() {
        return boardingFloor;
    }

    public int getWeight() {
        return weight;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}