# 👨‍💻 Developer Simulation Game

Android Studio를 활용한 모바일 육성 시뮬레이션 게임입니다.  
개발자 캐릭터를 5일 동안 관리하며 무사히 졸업시키는 것이 목표입니다.

## 📌 프로젝트 정보
- **개발 기간**: 2025.05.13 - 2025.06.22
- **개발 인원**: 1인 (개인 프로젝트)
- **개발 환경**: Android Studio
- **사용 언어**: Java, XML
- **플랫폼**: Android

## 🎮 게임 특징
- **시간 관리 시스템**: 하루를 8시~22시까지 2시간 단위로 관리
- **다양한 스탯**: 체력, 행복, 지능, 근력, 취미, 스트레스 6가지 능력치
- **미니게임**: 공부(OX퀴즈), 게임, 운동(탭 게임) 3가지 활동
- **멀티 엔딩**: 지능 수치와 행복도에 따른 해피/배드 엔딩

## 🎯 게임 목표
**5일 안에 개발자를 무사히 졸업시키기**
- 졸업 조건: 지능 수치 20 이상 달성
- 실패 조건: 행복도 0 이하 또는 지능 부족

## 🕹️ 게임 시스템

### 📊 능력치 시스템
- **체력**: 활동에 필요한 기본 자원 (낮잠으로 회복)
- **행복도**: 스트레스에 영향받으며 0이 되면 게임오버
- **지능**: 졸업을 위한 핵심 스탯 (공부로 증가)
- **근력**: 운동으로 증가
- **취미**: 게임으로 증가
- **스트레스**: 활동에 따라 누적, 다음날 행복도 감소에 영향

### 🎲 활동 시스템
#### 📚 공부하기 (OX 퀴즈)
- 체력 -20 소모
- 개발 관련 OX 문제 풀이
- 정답 시 지능 증가
- 지능이 높을수록 졸업 확률 상승

#### 🎮 게임하기
- 체력 -20 소모
- 미니게임 플레이
- 취미 수치 증가
- 스트레스 해소

#### 💪 운동하기 (탭 게임)
- 체력 -25 소모
- 타이밍에 맞춰 탭하여 게이지 유지
- 성공 시 최대 체력 +5, 근력 +5
- 실패 시 체력만 소모

#### 😴 휴식
- **낮잠**: 체력 +30 회복
- **취침**: 다음 날로 이동, 체력 전체 회복
- 스트레스 10당 행복도 -10 페널티

## 🎬 엔딩 시스템
- **해피 엔딩**: D-Day 0일에 지능 20 이상
- **배드 엔딩**: 행복도 0 또는 지능 부족

## 💡 주요 구현 기능

### 1. Activity 간 데이터 전달
<pre>
// Intent를 통한 게임 상태 저장 및 복원
intent.putExtra("nowday", nowday);
intent.putExtra("nowhp", nowhp);
startActivityForResult(intent, REQ_GAME);

startActivityForResult를 활용한 양방향 데이터 통신
미니게임 종료 후 메인 화면으로 상태 전달

2. 타이머 기반 탭 게임
javadecreaseRunnable = new Runnable() {
    @Override
    public void run() {
        progress -= DECREASE_RATE;
        // 게이지 감소 로직
        handler.postDelayed(this, INTERVAL);
    }
};
</pre>

Handler를 활용한 실시간 게이지 감소
터치 타이밍에 따른 성공/실패 판정

3. 시간 진행 시스템

활동별 시간 소모 (2시간 단위)
22시 이후 자동 야간 모드 전환
체력 0 이하 시 자동으로 22시로 이동

4. 스탯 연동 시스템

스트레스 누적 시 다음날 행복도 감소
체력 고갈 시 페널티 적용
실시간 ProgressBar로 시각화

## 🗂️ 프로젝트 구조
<pre>
developer_simulation/
├── MainActivity.java        # 메인 게임 화면 및 스탯 관리
├── GameActivity.java        # 미니게임 액티비티
├── oxquiz.java              # OX 퀴즈 액티비티
├── happyending.java         # 해피 엔딩 화면
├── badending.java           # 배드 엔딩 화면
└── res/
    ├── layout/
    │   └── activitymain.xml # 메인 UI 레이아웃
    └── drawable/            # 게임 이미지 리소스
</pre>

📱 기술적 구현 사항
<pre>
  ConstraintLayout: 복잡한 UI 레이아웃 구성
  ProgressBar: 체력, 행복도 시각화
  Handler & Runnable: 타이머 기반 게임 로직
  Intent & Bundle: Activity 간 데이터 전달
  ImageButton: 인터랙티브 UI 요소
  View Visibility Control: 시간대별 UI 전환
</pre>
# 스크린샷

<img width="303" height="653" alt="image" src="https://github.com/user-attachments/assets/069b405b-c5fb-4520-b0af-fe571ce05ce1" />


방 안에서 활동 선택 및 스탯 관리

🎮 게임 팁

스트레스 관리를 잘 해야 행복도를 유지할 수 있습니다
체력을 적절히 관리하며 활동하세요
지능 20 달성을 위해 계획적으로 공부하세요
가끔 게임과 운동으로 밸런스를 맞추세요

🔧 개선 가능 사항

 더 다양한 미니게임 추가
 난이도 선택 기능
 업적 시스템
 BGM 및 효과음 추가
 저장/불러오기 기능

📝 배운 점

Android Activity 생명주기 및 데이터 전달 방식 이해
Handler를 활용한 비동기 처리 및 타이머 구현
ConstraintLayout을 통한 반응형 UI 설계
게임 밸런스 설계 및 사용자 경험 고려
