package scheduler;

import entity.Elevator;
import service.ElevatorService;
import service.ElevatorServiceImpl;

public class ElevatorThread implements Runnable {
    private Elevator elevator;
    //기다리는 승객
    private boolean[] waitPassengerFloor;

    private boolean[] getInPassengerFloor;
    //올라갈 경우 true , 내려갈 경우 false
    private boolean running;
    private int sleepTime;
    private ElevatorService elevatorService;
    public ElevatorThread(Elevator elevator, int sleepTime) {
        this.elevator = elevator;
        this.sleepTime = sleepTime;
        this.waitPassengerFloor = new boolean[elevator.getMaxFloor()+elevator.getMinFloor()];
        this.getInPassengerFloor = new boolean[elevator.getMaxFloor()+elevator.getMinFloor()];
        this.running = true;
        elevatorService = new ElevatorServiceImpl();
    }

    @Override
    public void run() {
        while (running) {
            try {
                elevatorService.curFloorGetOnPassenger(elevator, waitPassengerFloor, getInPassengerFloor);
                elevatorService.curFloorGetOffPassenger(elevator, getInPassengerFloor);
                elevatorService.elevatorMove(elevator, waitPassengerFloor);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Elevator getElevator(){
        return this.elevator;
    }
    public boolean[] getWaitPassengerFloor(){
        return  this.waitPassengerFloor;
    }
    public boolean[] getGetInPassengerFloor(){
        return  this.getInPassengerFloor;
    }
    public void stop() {
        running = false;
    }

    public void transFormWaitPassengerFloor(int floor){
        this.waitPassengerFloor[floor] = !this.waitPassengerFloor[floor];
    }
    public void transFormGetInPassengerFloor(int floor){
        this.getInPassengerFloor[floor] = !this.getInPassengerFloor[floor];
    }
}