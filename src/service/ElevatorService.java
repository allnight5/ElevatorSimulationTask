package service;

import entity.Elevator;
import entity.Passenger;
import enums.ElevatorDoorStatus;
import scheduler.ElevatorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ElevatorService {
    public void elevatorMove(Elevator elevator, boolean[] waitPassengerFloor);
    public void curFloorGetOnPassenger(Elevator elevator, boolean[] waitPassengerFloor,  boolean[] getInPassengerFloor);
    public void curFloorGetOffPassenger(Elevator elevator, boolean[] waitPassengerFloor);
    public void elevatorDoorStatus(Elevator elevator, ElevatorDoorStatus elevatorDoorStatus);
    public void elevatorStatusTransForm(Elevator elevator,boolean[] waitPassengerFloor);
    public void removeElevator(String[] args, Map<Integer, Elevator> elevatorsMap,
                               BufferedReader br) throws IOException;
    public void removeElevatorTread(String[] args, Map<Integer, ElevatorThread> schedulerMap,
                                    BufferedReader br) throws IOException;
    public void addWaitPassenger(Elevator elevator, Passenger passenger);
}
