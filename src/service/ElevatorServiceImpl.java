package service;

import entity.Elevator;
import entity.Passenger;
import enums.ElevatorDoorStatus;
import enums.ElevatorStatus;
import scheduler.ElevatorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

public class ElevatorServiceImpl implements ElevatorService{
    @Override
    public void elevatorMove(Elevator elevator, boolean[] waitPassengerFloor) {
        elevatorStatusTransForm(elevator,  waitPassengerFloor);
        if(elevator.getElevatorStatus().equals(ElevatorStatus.STOP)){
            return;
        }else if(elevator.getElevatorStatus().equals(ElevatorStatus.UP)){
            elevator.moveCurrentFloor(elevator.getCurrentFloor()+1);
        }else{
            elevator.moveCurrentFloor(elevator.getCurrentFloor()-1);
        }
    }

    @Override
    public void curFloorGetOnPassenger(Elevator elevator, boolean[] waitPassengerFloor, boolean[] getInPassengerFloor) {
        elevator.addGetInPassenger(elevator.getCurrentFloor(), waitPassengerFloor, getInPassengerFloor);
    }

    @Override
    public void curFloorGetOffPassenger(Elevator elevator, boolean[] getInPassengerFloor) {
        elevator.removeGetInPassengers(elevator.getCurrentFloor(), getInPassengerFloor);
    }

    @Override
    public void elevatorDoorStatus(Elevator elevator, ElevatorDoorStatus elevatorDoorStatus) {
        elevator.transformDoorState(elevatorDoorStatus);
    }

    @Override
    public void addWaitPassenger(Elevator elevator, Passenger passenger) {

        elevator.addWaitPassenger(passenger);
    }
    @Override
    public void elevatorStatusTransForm(Elevator elevator,  boolean[] waitPassengerFloor) {
        int endPoint  = elevator.getMinFloor() +elevator.getMaxFloor();
        int currentFloor = elevator.getCurrentFloor();
        if(elevator.getElevatorStatus().equals(ElevatorStatus.UP)){
            for(int i=currentFloor; i < endPoint; i++){
                if(waitPassengerFloor[i]){
                    //문을 닫고 위쪽 방향으로 출발한다.
                    return;
                }
            }
            for(int i=currentFloor; i >= 0; i--){
                if(waitPassengerFloor[i]){
                    //여기서 문을 닫고 여는것이 아닌 방향이 바뀌었다고 말을 해준다.
                    elevator.transformElevatorStatus(ElevatorStatus.DOWN);
                    return;
                }
            }
        }

        else if(elevator.getElevatorStatus().equals(ElevatorStatus.DOWN)){
            for(int i=currentFloor; i >= 0; i--){
                if(waitPassengerFloor[i]){
                    //문을 닫고 위쪽 방향으로 출발한다.
                    return;
                }
            }
            for(int i=currentFloor; i < endPoint; i++){
                if(waitPassengerFloor[i]){
                    //여기서 문을 닫고 여는것이 아닌 방향이 바뀌었다고 말을 해준다.
                    elevator.transformElevatorStatus(ElevatorStatus.UP);
                    return;
                }
            }
        }

        else{
            for(int i=currentFloor; i < endPoint; i++){
                if(waitPassengerFloor[i]){
                    elevator.transformElevatorStatus(ElevatorStatus.UP);
                    return;
                }
            }
            for(int i=currentFloor; i >= 0; i--){
                if(waitPassengerFloor[i]){
                    elevator.transformElevatorStatus(ElevatorStatus.DOWN);
                    return;
                }
            }
        }
        //문을 닫고 요청이 들어올때까지 가만히 있는데
        elevator.transformElevatorStatus(ElevatorStatus.STOP);
    }


    @Override
    public void removeElevatorTread(String[] args, Map<Integer, ElevatorThread> schedulerMap,
                                    BufferedReader br) throws IOException {
        int count =1;
        StringTokenizer st;
        System.out.println("현재 작동중인 엘리베이터 번호 ");
        for(int i : schedulerMap.keySet()){
            if(count%4 == 0){
                System.out.println();
            }
            System.out.print(i + "    ");
            count++;
        }
        System.out.println();
        System.out.println("정지시킬 엘리베이터 번호를 입력해주세요 : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int removeTread = Integer.parseInt(st.nextToken());
        schedulerMap.get(removeTread).stop();
        schedulerMap.remove(removeTread);
    }

    @Override
    public void removeElevator(String[] args, Map<Integer, Elevator> elevatorsMap,
                                    BufferedReader br) throws IOException {
        int count =1;
        StringTokenizer st;
        System.out.println("현재 존재하는 엘리베이터 번호 ");
        for(int i : elevatorsMap.keySet()){
            if(count%4 == 0){
                System.out.println();
            }
            System.out.print(i + "    ");
            count++;
        }
        System.out.println();
        System.out.println("폐기할 엘리베이터 번호를 입력해주세요 : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int removeTread = Integer.parseInt(st.nextToken());
        elevatorsMap.remove(removeTread);
    }
}
