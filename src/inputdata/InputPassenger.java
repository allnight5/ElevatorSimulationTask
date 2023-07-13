package inputdata;


import entity.Passenger;
import scheduler.ElevatorThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

public class InputPassenger {
    public static void inputPassenger(String[] args,
                                      Map<Integer, ElevatorThread> elevatorTreadMap,
                                      BufferedReader br) throws IOException {

        System.out.println("엘리베이터 탑승인원 추가");
        System.out.println("탑승 인원을 얼마나 생성하시겠어요?");
        System.out.print("추가 인원 입력: ");
        int Passengers = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for(int i=0; i<Passengers; i++){
            System.out.println("존재하는 탑승 엘리베이터번호, 탑승층, 무게, 하차층을");
            System.out.println("순서대로 입력해주시기 바랍니다.");
            br = new BufferedReader(new InputStreamReader(System.in));
            try {
                st = new StringTokenizer(br.readLine().trim());
                int elevatorNumber = Integer.parseInt(st.nextToken());
                if(elevatorNumber <0) break;
                int currentFloor = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                int destinationFloor = Integer.parseInt(st.nextToken());
                ElevatorThread elevatorThread = elevatorTreadMap.get(elevatorNumber);
                elevatorThread.transFormWaitPassengerFloor(currentFloor);
                elevatorThread.getElevator()
                        .addWaitPassenger(new Passenger(elevatorNumber, currentFloor, weight, destinationFloor));

            }catch (Exception e){
                System.out.println("[ERROR] 존재하는 엘리베이터 번호와");
                System.out.println("[ERROR] 올바른 형태로 값을 입력해주시기 바랍니다.");
                System.out.println("[ERROR] 종료 하고 싶으시다면 엘리베이터번호를 음수를 입력해주시기 바랍니다.");
                i--;
            }
        }

    }
}
