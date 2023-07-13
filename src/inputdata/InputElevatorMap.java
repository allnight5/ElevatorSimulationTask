package inputdata;

import dto.ElevatorDto;
import entity.Elevator;
import scheduler.ElevatorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class InputElevatorMap {
    public static void inputMap(String[] args,
                                Map<Integer, Elevator> elevatorsMap,
                                Map<Integer, ElevatorThread> schedulerMap,
                                BufferedReader br) throws IOException {

        System.out.println("몇 개의 엘리베이터를 추가 하시겠어요?");
        System.out.print("갯수 입력: ");
        int numElevators = Integer.parseInt(br.readLine());

        if(numElevators<1){
            System.out.println("1이하로 생성하여 생성을 취소합니다.");
            return;
        }

        System.out.println("각 엘리베이터 생성 값 입력");
        for (int i = 0; i < numElevators; i++) {
            System.out.println((i + 1) + " 번째 엘리베이터 생성 값 입력");
            System.out.println("입력값은 엘리베이터 1.번호, 2.총량, 3.지상마지막층, 4.지하마지막층");
            System.out.println("                 4.지하마지막층, 5.정지시간, 6.자동으로 닫히는 시간");
            System.out.println("순서대로 202 300 10 5 100 5");
            System.out.println("위의 값이 입력되어야 합니다.");
            br = new BufferedReader(new InputStreamReader(System.in));
            try {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                int number = Integer.parseInt(st.nextToken());
                if(number < 0) break;
                int capacity = Integer.parseInt(st.nextToken());
                int maxFloor = Integer.parseInt(st.nextToken());
                int minFloor = Integer.parseInt(st.nextToken());
                int stopTime = Integer.parseInt(st.nextToken());
                int autoCloseTime = Integer.parseInt(st.nextToken());
                ElevatorDto elevatorDto = new ElevatorDto(number, capacity, maxFloor, minFloor, stopTime, autoCloseTime);
                Elevator elevator = new Elevator(elevatorDto);
                elevatorsMap.put(number, elevator);
                ElevatorThread elevatorThread = new ElevatorThread(elevator, (i+1)*1000);
                Thread schedulerThread = new Thread(elevatorThread);
                schedulerThread.start();
                schedulerMap.put(number, elevatorThread);
                System.out.println("생성 완료.");
            } catch (IOException | NumberFormatException | NoSuchElementException e) {
                System.out.println("[ERROR] 올바른 값을 입력해주시기 바랍니다.");
                System.out.println("[ERROR] 종료 하고 싶으시다면 엘리베이터번호를 음수를 입력해주시기 바랍니다.");
                i--;
            }
        }
    }

}