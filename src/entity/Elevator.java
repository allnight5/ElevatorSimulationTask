package entity;

import dto.ElevatorDto;
import enums.ElevatorDoorStatus;
import enums.ElevatorStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Elevator {
    //엘리베이터 번호
    private int number;

    //엘리베이터 최대용량
    private int maxWeight;
    //현재 전체무게
    private int currentTotalWeight;
    private int maxFloor;
    private int minFloor;
    //자동으로 닫히는 시간
    private int autoCloseTime;
    //내려오면서 멈추는시간
    private int stopTime;
    //현재층
    private int currentFloor;
    //엘리베이터 상태 STOP,UP,DOWN이 존재한다 초기상태 UP.
    private ElevatorStatus elevatorStatus;

    //문의 상태 CLOSE, OPEN 두가지가 존재한다.
    private ElevatorDoorStatus elevatorDoorStatus;

    //map이기 때문에 엘리베이터는 탑승자객체가 필요없고 내릴 층과 무게만 가지고 있으면 된다.
    //만약 내릴 층이 바뀔수 있다면 Intger가 아닌 아래와 마찬가지로 List<Passenger>로 바꿔야한다.
    private Map<Integer, Integer> getInPassengers;

    // 기다리는 층과 사람은 객체를 가지고 있어야한다.
    private Map<Integer, List<Passenger>> waitPassengers;

    public Elevator(ElevatorDto elevatorDto) {
        this.number = elevatorDto.getNumber();
        this.maxWeight = elevatorDto.getMaxWeight();
        this.maxFloor = elevatorDto.getMaxFloor();
        this.minFloor = elevatorDto.getMinFloor();
        this.stopTime = elevatorDto.getStopTime();
        this.autoCloseTime = elevatorDto.getAutoCloseTime();
        this.elevatorStatus= ElevatorStatus.UP;
        this.elevatorDoorStatus = ElevatorDoorStatus.CLOSE;
        this.currentFloor = 1;
        this.waitPassengers= new HashMap<>();
        this.getInPassengers = new HashMap<>();
    }

    public void addGetInPassenger(int currentFloor, boolean[] waitPassengerFloor, boolean[] getInPassengerFloor) {
        /**
         * 1. 해당층 도착시 waitPassengers맵에서 확인후 탑승
         * 2. 탑승후 현재탑승무게와 탑승하려는 passenger의 무게를 확인
         * 3. 넘어간다면 waitPassengers맵에 넣고 종료
         * 4. 이하라면 getOnPassengers맵에 넣어서 탑승 완료후 출발
         */
        /*
            해당 층 도착시 waitPassengers에서 기다리던 사람이 있는지 확인하고;
            몸무게가 적은 사람부터 넣는다거나 큰사람부터 넣는다 이런건 마음대로
            어째서인가?? 현실에서 이런걸로 발생할 문제는 랜덤이기 때문에.
            큐를 통해서 앞에서 부터 빼는 방법도.
            스택을 통해서 뒤에서 빼는방법도 있다.
        */
        if(!this.waitPassengers.containsKey(currentFloor)) return;

        List<Passenger> passengerList = this.waitPassengers.get(currentFloor);

        for(int i=passengerList.size()-1; i>=0; i--){
            Passenger passenger = passengerList.get(i);
            if(this.currentTotalWeight + passenger.getWeight() > this.maxWeight){
                return;
            }
            getInPassengerFloor[currentFloor] = true;
            passengerList.remove(i);
            this.currentTotalWeight += passenger.getWeight();
            this.getInPassengers.put(currentFloor, getInPassengers.getOrDefault(currentFloor, 0)+passenger.getWeight());
        }

        if(passengerList.size() !=0){
            this.waitPassengers.put(currentFloor, passengerList);
            waitPassengerFloor[currentFloor] = true;
        }else{
            this.waitPassengers.remove(currentFloor);
            waitPassengerFloor[currentFloor] = false;
        }
    }

    public void addWaitPassenger(Passenger passenger) {
        int boardingFloor = passenger.getBoardingFloor();
        List<Passenger> passengerList;
        if(this.waitPassengers.containsKey(boardingFloor)){
            passengerList = this.waitPassengers.get(boardingFloor);
        }else{
            passengerList = new ArrayList<>();
        }

        passengerList.add(passenger);
        this.waitPassengers.put(boardingFloor, passengerList);
    }
    //탑승인원 하차
    public void removeGetInPassengers(int currentFloor, boolean[] getInPassengerFloor) {
        if (getInPassengers.containsKey(currentFloor) && getInPassengerFloor[currentFloor]) {
            getInPassengerFloor[currentFloor] = false;
            int weight = getInPassengers.remove(currentFloor);
            this.currentTotalWeight -= weight;
            System.out.println(this.number + "번 엘리베이터 승객들이" + currentFloor + "층에서 내립니다.");
            System.out.println("-------------------------------------------------------------");
            return;
        }
        if(!this.elevatorStatus.equals(ElevatorStatus.STOP)){
            System.out.println(currentFloor+"층에서 ");
            System.out.println(this.number + "번 엘리베이터에 승객이 없어 문을 닫고" + this.elevatorStatus+"중입니다.");
            System.out.println(this.elevatorStatus+"중입니다.");
            System.out.println("-------------------------------------------------------------");
        }
    }

    public int getNumber() {
        return number;
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

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Map<Integer, Integer> getGetInPassengers() {
        return getInPassengers;
    }

    public int getCurrentTotalWeight() {
        return currentTotalWeight;
    }

    public ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public ElevatorDoorStatus getElevatorDoorStatus() {
        return elevatorDoorStatus;
    }

    public Map<Integer, List<Passenger>> getWaitPassengers() {
        return waitPassengers;
    }

    public void changeNumber(int number) {
        this.number = number;
    }

    public void changeAutoCloseTime(int autoCloseTime) {
        this.autoCloseTime = autoCloseTime;
    }

    public void changeStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    public void moveCurrentFloor(int currentFloor){
        this.currentFloor = currentFloor;
    }

    public void transformElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

    public void transformDoorState(ElevatorDoorStatus elevatorDoorStatus) {
        this.elevatorDoorStatus = elevatorDoorStatus;
    }

    public void transformGetInPassengers(Map<Integer, Integer> getInPassengers) {
        this.getInPassengers = getInPassengers;
    }

    public void transformWaitPassengers(Map<Integer, List<Passenger>> waitPassengers) {
        this.waitPassengers = waitPassengers;
    }
}
