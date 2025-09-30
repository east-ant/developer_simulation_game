package com.example.developer_simulation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private boolean end=true;
    private FrameLayout frameLayout;
    private ImageButton blueBtn1,blueBtn2,blueBtn3, redBtn1,redBtn2, goldBtn;
    private TextView scoreText,successT,failT;
    private Button but1;
    private int score = 0;
    private Handler handler = new Handler();
    private Random random = new Random();
    int progress = 0,DECREASE_RATE = 1,INTERVAL = 100;
    //  처음 게이지      감소량             감소 시간 텀
    private ProgressBar progressBar;
    private Runnable decreaseRunnable;
    private static final int TIME_BLUE = 1500;
    private static final int TIME_RED = 1000;
    private static final int TIME_GOLD = 2000;

    private int nowhp, maxhp, nowtime, nowhappy, nowhobby, nowday,nowstudy,nowpower,nowstress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitygame);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //메인엑티비티에서 전달받은값
        Intent data = getIntent();

        nowday    = data.getIntExtra("nowday", nowday);
        nowtime   = data.getIntExtra("nowtime", nowtime);
        nowhp     = data.getIntExtra("nowhp", nowhp);
        maxhp     = data.getIntExtra("maxhp", maxhp);
        nowhappy  = data.getIntExtra("nowhappy", nowhappy);

        nowstudy  = data.getIntExtra("nowstudy", nowstudy);
        nowpower  = data.getIntExtra("nowpower", nowpower);
        nowhobby  = data.getIntExtra("nowhobby", nowhobby);
        nowstress = data.getIntExtra("nowstress", nowstress);

        frameLayout = findViewById(R.id.frameLayout1);
        scoreText = findViewById(R.id.score);

        blueBtn1 = findViewById(R.id.blue1);
        blueBtn2 = findViewById(R.id.blue2);
        blueBtn3 = findViewById(R.id.blue3);
        redBtn1 = findViewById(R.id.red1);
        redBtn2 = findViewById(R.id.red2);
        goldBtn = findViewById(R.id.gold1);
        progressBar = findViewById(R.id.timer);

        failT = findViewById(R.id.fail_Text);
        but1 = findViewById(R.id.comeback1);
        successT = findViewById(R.id.success_text);

        // 처음엔 버튼 다 숨김
        blueBtn1.setVisibility(View.INVISIBLE);
        blueBtn2.setVisibility(View.INVISIBLE);
        blueBtn3.setVisibility(View.INVISIBLE);
        redBtn1.setVisibility(View.INVISIBLE);
        redBtn2.setVisibility(View.INVISIBLE);
        goldBtn.setVisibility(View.INVISIBLE);

        startGameLoop();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startDecreaseProgress();
            }
        }, 1000);
    }
    private void startGameLoop() {
        if(end){
            spawnSpecificButtonLoop(blueBtn1, TIME_BLUE, 2000);
            spawnSpecificButtonLoop(blueBtn2, TIME_BLUE, 2200);
            spawnSpecificButtonLoop(blueBtn3, TIME_BLUE, 2400);

            // 레드 2개
            spawnSpecificButtonLoop(redBtn1, TIME_RED, 2000);
            spawnSpecificButtonLoop(redBtn2, TIME_RED, 3000);

            // 골드 1개
            spawnSpecificButtonLoop(goldBtn, TIME_GOLD, 4000);
        }
        // 블루볼 3개

    }
    private void spawnSpecificButtonLoop(final ImageButton button, final int visibleTime, final int spawnInterval) {
        if(end) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    spawnButton(button, visibleTime);
                    handler.postDelayed(this, spawnInterval);  // 주기적으로 반복
                }
            }, spawnInterval);
        }
    }
    private void spawnButton(final ImageButton button, int delayTime) {
        if(end) {
            // 클릭 리스너
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getVisibility() == View.VISIBLE) {
                        int id = button.getId();
                        if (id == R.id.blue1 || id == R.id.blue2 || id == R.id.blue3) {
                            score += 3;
                        } else if (id == R.id.red1 || id == R.id.red2) {
                            score += 5;
                        } else if (id == R.id.gold1) {
                            score += 10;
                        }
                        updateScore();
                        button.setVisibility(View.INVISIBLE);
                    }
                }
            });

            // 랜덤 위치 지정
            frameLayout.post(new Runnable() {
                @Override
                public void run() {
                    int maxX = frameLayout.getWidth() - button.getWidth();
                    int maxY = frameLayout.getHeight() - button.getHeight();

                    float x = random.nextInt(Math.max(maxX, 1));
                    float y = random.nextInt(Math.max(maxY, 1));

                    button.setX(x);
                    button.setY(y);
                    button.setVisibility(View.VISIBLE);
                }
            });

            // 일정 시간 후 자동 사라짐
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setVisibility(View.INVISIBLE);
                }
            }, delayTime);
        }
    }
    private void updateScore() {
        scoreText.setText("" + score);
            if (score >= 50 && end) {
                end = false;
                but1.setVisibility(View.VISIBLE);
                successT.setVisibility(View.VISIBLE);
            }
    }
    private void startDecreaseProgress() {

        decreaseRunnable = new Runnable() {
            @Override
            public void run() {
                if (!end) return;
                progress += DECREASE_RATE;
                if (progress >= 298) {
                    end = false;
                    progress = 300;
                    progressBar.setProgress(progress);
                    // 체력 0 되었을 때 처리 다시 메인으로 가게 작성

                    but1.setVisibility(View.VISIBLE);
                    failT.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(decreaseRunnable);
                }

                progressBar.setProgress(progress);
                handler.postDelayed(this, INTERVAL);
            }
        };

        handler.postDelayed(decreaseRunnable, INTERVAL);

    }

    public void comeback(View view) {
        if (progress >= 298) {
            nowhappy -= 5;
            nowstress +=5;
            if(nowhappy <=0){
                nowhappy = 0;
            }
            nowtime +=2;
        }

        if (score >= 50) {
            nowhobby += 3;
            nowhappy += 10;
            if(nowhappy >=50){
                nowhappy = 50;
            }
            nowtime +=2;
            nowstress -=10;
            if(nowstress <=0){
                nowstress = 0;
            }
        }
        //메인엑티비티에 값전달
        Intent returnIntent = new Intent();
        returnIntent.putExtra("nowday", nowday);
        returnIntent.putExtra("nowtime", nowtime);
        returnIntent.putExtra("nowhp", nowhp);
        returnIntent.putExtra("maxhp", maxhp);
        returnIntent.putExtra("nowhappy", nowhappy);
        returnIntent.putExtra("nowhobby", nowhobby);
        returnIntent.putExtra("nowstudy", nowstudy);
        returnIntent.putExtra("nowpower", nowpower);
        returnIntent.putExtra("nowstress", nowstress);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}

