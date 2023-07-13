import dto.ElevatorDto;
import entity.Elevator;
import entity.Passenger;
import enums.ElevatorStatus;
import scheduler.ElevatorThread;
import service.ElevatorService;
import service.ElevatorServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
            스레드가 일정 갯수 이상 넘으면 안되어 제한하여 관리하고 싶다면
            <제한된 스레드관리> 원하는 개수만큼 생성
            ExecutorService executorServiceWithNum = Executors.newFixedThreadPool(numElevators);
        */
        Map<Integer, Elevator> elevatorsMap = new HashMap<>();
        Map<Integer, ElevatorThread> elevatorTreadMap = new HashMap<>();

        ElevatorService elevatorService = new ElevatorServiceImpl();

        //초기에 4개의 스레드를 생성
        int elevatorNumber = 4;
        int maxFloor = 11;
        int minFloor = 6;
        for(int i=0; i<elevatorNumber; i++){
            ElevatorDto elevatorDto = new ElevatorDto(i, 1000, maxFloor,
                    minFloor, 5, 2);
            Elevator elevator = new Elevator(elevatorDto);
            int number = elevatorDto.getNumber();
            elevatorsMap.put(number, elevator);
            ElevatorThread elevatorThread = new ElevatorThread(elevator, (i+1)*2000);
            Thread schedulerThread = new Thread(elevatorThread);
            schedulerThread.start();
            elevatorTreadMap.put(number, elevatorThread);
        }

        int passengers = elevatorNumber*3;
        //12명의 탑승인원 추가
        for(int i=0; i< passengers; i++){
            Random random = new Random();
            int max = maxFloor+ minFloor-2;
            int boardingFloor = random.nextInt(max);
            int destinationFloor = random.nextInt(max);
            Passenger passenger = new Passenger(i%elevatorNumber, boardingFloor, destinationFloor, 90);
            ElevatorThread elevator = elevatorTreadMap.get(i%elevatorNumber);
            System.out.println("승객 :" +i+" : 를 "+elevator.getElevator().getNumber()+" 엘리베이터에 추가합니다.");
            elevator.transFormWaitPassengerFloor(boardingFloor);
            elevatorService.addWaitPassenger(elevator.getElevator(), passenger);
        }

        while(true){
            int count =0;
            for(int i=0; i< elevatorTreadMap.size();i++){
                if(elevatorTreadMap.get(i).getElevator().getElevatorStatus().equals(ElevatorStatus.STOP)){
                    count++;
                }
            }
            if(count >= elevatorNumber) {
                System.out.println("모든 승객이 빠져나와");
                System.out.println("엘리베이터 시뮬레이션을 종료합니다");
                System.out.println("감사합니다.");
                break;
            }
        }

        if(!elevatorTreadMap.isEmpty()){
            System.out.println("남은 스레드 종료 시작");
            for (int key : elevatorTreadMap.keySet()) {
                elevatorTreadMap.get(key).stop();
            }
            System.out.println("스레드 전체 종료 완료");
        }
        /*  ---------------------------종료-------------------------------- */
        /*  ---------------------------종료-------------------------------- */
        /*  ---------------------------종료-------------------------------- */
        /*  ---------------------------종료-------------------------------- */
        /*  ---------------------------종료-------------------------------- */
        /*  ---------------------------종료-------------------------------- */

        /*
        입력시 출력이 콘솔에 나오지 않아야한다.
        출력문을 입력문으로 착각하기때문에 오류가 발생한다.
        하고 싶다면 대기시 출력되는 내용문을 제거하면
        모든 작동중인 엘리베이터가 대기시 입력이 가능하며
        다른 방법으로는 Scanner을 통하여
        하나씩 입력받아 하는 방법이 있을것 같습니다.
        감사합니다.

        BufferedReader br = null;
        int choice=0;
        while(choice != -1){
            System.out.println("1. (수동입력)엘리베티어 추가 생성  ||   2. (수동입력)탑승 인원 추가");
            System.out.println("3. (정지하고 싶은 엘리베이터 번호)엘리베이터 삭제(스레드하나 제거)");
            System.out.println("4. 존재하는 엘리베이터 폐기");
            System.out.println("그 외 빠져 나가기");
            br = new BufferedReader(new InputStreamReader(System.in));

            try {
                choice = Integer.parseInt(br.readLine());
            }catch (NumberFormatException e){
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("엘리베이터 추가 생성");
                    InputElevatorMap.inputMap(args, elevatorsMap, elevatorTreadMap, br);
                }
                case 2 -> {
                    System.out.println("탑승인원 추가");
                    InputPassenger.inputPassenger(args, elevatorTreadMap, br);
                }
                case 3 -> {
                    System.out.println("작동중 엘리베이터 정지");
                    if(elevatorTreadMap.size() ==0){
                        System.out.println("작동중인 엘리베이터가 존재 하지 않습니다.");
                        break;
                    }
                    elevatorService.removeElevatorTread(args, elevatorTreadMap, br);
                }
                case 4 ->{
                    System.out.println("존재하는 엘리베이터 폐기");
                    if(elevatorsMap.size() == 0){
                        System.out.println("엘리베이터가 존재 하지 않습니다.");
                        break;
                    }
                    elevatorService.removeElevator(args, elevatorsMap, br);
                }
                default -> choice = -1;
            }
        }
        br.close();
        */

//        if(!elevatorTreadMap.isEmpty()){
//            System.out.println("남은 스레드 종료 시작");
//            for (int key : elevatorTreadMap.keySet()) {
//                elevatorTreadMap.get(key).stop();
//            }
//            System.out.println("스레드 전체 종료 완료");
//        }
//
//        System.out.println("엘리베이터 시뮬레이션 종료");
    }

}