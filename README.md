# 엘리베이터 시뮬레이션

---
Run 시키시면 됩니다.

직접 입력하시면서 하고 싶으시다면 

Main의 종료위의 
//모든 스레드 
종료의 조건문(if)을 주석 처리하시고
그 바로 위의
//동작 유지의 
반복문(while)을 주석처리하시고
Main의 종료 아래 주석을 모두 살리시면됩니다.

하지만 출력 되는것과 입력되는 것에 충돌로 인하여 돌아가고 있을때는
출력문으로 인하여 올바른 인식이 되지 않기 때문에 모든 승객을 내보내고 입력하시거나

ElevatorThread elevatorThread = new ElevatorThread(elevator, (i+1)*2000);

대기시간 부분 (i+1)*2000
대기시간 부분을 길게 주어 그 멈춘 시간안에 입력하시거나
print문 빼시거나 하시면 됩니다.

---
# 만들면서 한 고민

스레드가 필요할 이유가 뭘까 생각했는데
<span style="color:red; font-size:20px;">하나의</span> 엘리베이터가 아니라 <span style="color:green; font-size:20px;">여러대의</span> 엘리베이터가 있다면
하나씩 돌면서 요청을 처리하면 되는데 10대의중에서 9대의 엘리베이터에서 계속 문을 열고있다는 요청이 들어오고? 1대는 1층마다 이동하고 멈추고 취소 하고를 한다면?
각 엘리베이터마다 스레드 없이 처리하기에는 문제가 발생할 것이다 라는 고민이 들었습니다.

그러면 이제아파트 엘리베이터 마다 스레드를 만들어 줘야하는걸까 하는 고민이 들게 되었는데
<span style="color:green; font-size:20px;">두개의 엘리베이터를 하나의 스레드</span>에 넣어뒀을경우

하나의 엘리베이터에서 문제가 발생했을때 다른 엘리베이터도 하나의 스레드 안에 들어있기에 멈추게 된다고 생각하여

앞에서 스레드가 필요한 이유와 같은 생각이 들었습니다. 

그래서 엘리베이터 객체 생성시 스케줄러를 생성하여
스케줄러에 해당 엘리베티어 객체를 넣고 스레드를 동작시키며
해당 map에 스케줄러를 넣어주고 해당 엘리베이터를 삭제하고 싶다면 entity맵에서 삭제하고
스케줄러는 stop 시키면 될것 같았고

처음에는 스케줄러만 있으면 되겠다 하고 엘리베이터 맵을 삭제하려했으나
제가 왜 넣어 놨는지 고민했습니다.
생각해보니 잠시 작동을 멈췄을때 스케줄러를 종료시킬수있으나 해당 엘리베이터는 남아있다가
다시 동작하면 동작시켜줘야하기 때문에 스케줄러와 엘리베이터맵 두개를 만들었습니다. 


---
## 엘리베이터 동작

---

1.엘리베이터가 이동할때에는 이동하는데
중간에 눌렀을때 현재 위치에서
멈추는 속도보다 도착시간이 느리다면 무시하고
지나친다.

2.탑승할때 탑승총량보다 탑승무게가 크다면
낮아질때까지 경고음을 울리며 출발하지 않는다.
탑승 무게가 크지 않다면 현재 승객의 무게를 탑승 무게에 더하고 출발한다.

3.현재층에서 내리는 승객이 있다면 그 승객을 제거하고
그 무게만큼 탑승 총량에서 제거한다.

4.자동으로 닫히는 시간
(이시간전에 밖이나 안에서 열기를 누른다면 열리고
출발하지 않고 자동닫히는 시간은 0으로 초기화 된다.
리스트로 만들어 두고 사용하는것이
확장성은 좋을 것이다.)

5.문 닫는 버튼 
(autoClose를 작동시간-2 로 바꾼다)
2초 안에 열기버튼이나 밖에서 해당 방향을
누를시 다시 멈추고 문이열린다.
시간은 조절할 수 있다.
이것을 리스트로 만들어 두고 사용하는것이
확장성은 좋을 것이다.)

6.문을 계속 열고있게 하는조건(
열기 버튼을 계속눌러 autoClose를 0으로 초기화 시킨다.
밖에서 해당 방향 버튼을 누르고 있어 autoClose를 0으로 초기화 시킨다.
)

7.올라가는데 현재층 도착 후 보다 올라가는데 없고 내려가는게 있다면
아래로 내려가게 바꾼다.
반대로 내려가는데 현재층에서 도착 후 내려가는게 없고
올라가는게 있다면 올라가도록한다.

8.엘리베이터는 Main에서 추가하거나 삭제할 수 있으며 
-1 고유번호를 입력시 반복문을 빠져나가게한다.

---

## 승객

1. 탑승자는 승객의 고유 번호, 현재 층, 무게, 내리는 층을 가진다.
2. 엘리베이터 탑승총량보다
   탑승한 승객들의 무게+ 이제 탑승하려는 사람의 무게가 크다면 탑승할수 없고 다시 대기한다.
3. 탑승자는 Main에서 추가할 수 있으며 -1 고유번호를 입력시 반복문을 빠져나가게한다.

--- 
