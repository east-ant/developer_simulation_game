package com.example.developer_simulation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar, life_gau,happy_gau;
    private Button tapButton;
    ImageView health_down, health_up,nightImage,dayImage, player1, player2;
    boolean health_boolean = false;
    private TextView hp1,happinessText,Mtime,study1,power1,hobby1,stress1,D_day,nameTextView;
    private ImageButton gamebtn,healthbtn,bookbtn, daybed, nightbed;

    private TextView opt1,opt2,opt3,opt4;


    int firstday=1 ,first_time=8, firsthp=100, firsthappy=50, firsthobby=0 , firststudy=0, firststress=0, firstpower=5;
    int nowday = firstday, nowtime = first_time, maxhp = firsthp, nowhp = firsthp, nowhappy=firsthappy ,nowhobby = firsthobby, nowstudy = firststudy,  nowstress = firststress, nowpower = firstpower, nowD_day = 5;
    int progress = 50,MAX_PROGRESS = 100,DECREASE_RATE = 2,INCREASE_AMOUNT = 5,INTERVAL = 100,but_count = 0,decreaseHp = 25,increaseHp = 5;
    //  처음 게이지     최대 게이지          감소량             증가량                감소 시간 텀     버튼 카운트     체력 감소    최대 체력 증가
    private Handler handler = new Handler();
    private Runnable decreaseRunnable;

    int option_count;
    private static final int REQ_GAME = 1001;
    private static final int REQ_QUIZ = 1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitymain);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressBar = findViewById(R.id.progressBar2);//헬스 게이지
        tapButton = findViewById(R.id.tapButton); //헬스 탭
        health_up = findViewById(R.id.helth2);  //헬스2
        health_down = findViewById(R.id.helth1); //헬스1
        hp1 = findViewById(R.id.Hp);
        life_gau = findViewById(R.id.life_gauge);
        happy_gau = findViewById(R.id.happy_gauge);
        Mtime =  findViewById(R.id.maintime);
        nightImage = findViewById(R.id.night);
        dayImage = findViewById(R.id.day);
        gamebtn = findViewById(R.id.game);
        healthbtn = findViewById(R.id.weight);
        bookbtn = findViewById(R.id.book);
        daybed = findViewById(R.id.daybed1);
        nightbed = findViewById(R.id.nightbed1);
        D_day = findViewById(R.id.D_day1);
        player1 = findViewById(R.id.person1);
        player2 = findViewById(R.id.person2);

        study1 = findViewById(R.id.stduy);
        power1 = findViewById(R.id.power);
        hobby1 = findViewById(R.id.hobby);
        stress1 = findViewById(R.id.stress);

        opt1 = findViewById(R.id.optionText);
        opt2 = findViewById(R.id.optionText1);
        opt3 = findViewById(R.id.optionText2);
        opt4 = findViewById(R.id.optionText3);

        progressBar.setMax(MAX_PROGRESS);
        progressBar.setProgress(progress);

         // 취미 텍스트
        happinessText = findViewById(R.id.happy); // 행복 텍스트

        nameTextView = findViewById(R.id.name);
        String userName = getIntent().getStringExtra("username");
        nameTextView.setText("이름 : "+userName);  // 예시 출력
    }
    //
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_GAME:
                //GameActivity에서 돌려받은값
                nowday   = data.getIntExtra("nowday", nowday);
                nowtime  = data.getIntExtra("nowtime", nowtime);

                nowhp    = data.getIntExtra("nowhp", nowhp);
                maxhp    = data.getIntExtra("maxhp", maxhp);
                nowhappy = data.getIntExtra("nowhappy", nowhappy);

                nowstudy = data.getIntExtra("nowstudy", nowstudy);
                nowpower = data.getIntExtra("nowpower", nowpower);
                nowhobby = data.getIntExtra("nowhobby", nowhobby);
                nowstress = data.getIntExtra("nowstress", nowstress);

                //받은 값으로 UI 갱신
                life_gau.setMax(maxhp);
                life_gau.setProgress(nowhp);
                happy_gau.setProgress(nowhappy);

                hp1.setText("체력 : " + nowhp + "/" + maxhp);
                happinessText.setText("행복 : "+nowhappy+"/50");
                hobby1.setText("취미     " + nowhobby);
                stress1.setText("스트레스     "+nowstress);
                personlocate();
                Mtime.setText(nowday + "일차 " + nowtime + ":00 시");
                checking();
                break;

            case REQ_QUIZ:
                // OxQuizActivity에서 돌려받은값
                nowday   = data.getIntExtra("nowday", nowday);
                nowtime  = data.getIntExtra("nowtime", nowtime);

                nowhp    = data.getIntExtra("nowhp", nowhp);
                maxhp    = data.getIntExtra("maxhp", maxhp);
                nowhappy = data.getIntExtra("nowhappy", nowhappy);

                nowstudy = data.getIntExtra("nowstudy", nowstudy);
                nowpower = data.getIntExtra("nowpower", nowpower);
                nowhobby = data.getIntExtra("nowhobby", nowhobby);
                nowstress = data.getIntExtra("nowstress", nowstress);

                //받은 값으로 UI 갱신
                life_gau.setMax(maxhp);
                life_gau.setProgress(nowhp);
                hp1.setText("체력 : " + nowhp + "/" + maxhp);

                happy_gau.setProgress(nowhappy);
                happinessText.setText("행복 : "+nowhappy+"/50");
                hobby1.setText("취미     " + nowhobby);
                Mtime.setText(nowday + "일차 " + nowtime + ":00 시");
                stress1.setText("스트레스     "+nowstress);
                study1.setText("지능     "+nowstudy);
                personlocate();
                checking();

                break;
        }

    }
    public void helth_tap(View view) {
        health_boolean = true;
        nowhp = nowhp - decreaseHp;
        life_gau.setProgress(nowhp);
        hp1.setText("체력 : "+ (nowhp)+"/"+maxhp);
        if (health_boolean) {
            progressBar.setVisibility(View.VISIBLE);
            health_up.setVisibility(View.VISIBLE);
            health_down.setVisibility(View.VISIBLE);
            tapButton.setVisibility(View.VISIBLE);

            //게이지 감소
            decreaseRunnable = new Runnable() {
                @Override
                public void run() {
                    progress -= DECREASE_RATE;
                    //실패
                    if (progress <= 0) {
                        progress = 0;
                        progressBar.setProgress(progress);
                        // 게이지가 0이면 더이상 감소하지 않음
                        progress=60;
                        health_boolean = false;
                        tapButton.setOnClickListener(null);
                        progressBar.setVisibility(View.GONE);
                        health_up.setVisibility(View.GONE);
                        health_down.setVisibility(View.GONE);
                        tapButton.setVisibility(View.GONE);
                        handler.removeCallbacks(decreaseRunnable);

                        personlocate();
                        nowtime +=2;
                        Mtime.setText(nowday+"일차 "+nowtime+":00 시");
                        checking();

                        return;
                    }//성공
                    if (progress >=95) {
                        progress=60;
                        health_boolean = false;
                        tapButton.setOnClickListener(null);
                        progressBar.setVisibility(View.GONE);
                        health_up.setVisibility(View.GONE);
                        health_down.setVisibility(View.GONE);
                        tapButton.setVisibility(View.GONE);
                        handler.removeCallbacks(decreaseRunnable);

                        personlocate();
                        maxhp = maxhp + increaseHp;
                        life_gau.setMax(maxhp);
                        hp1.setText("체력 : "+ (nowhp)+"/"+maxhp);
                        nowtime +=2;
                        nowpower +=5;
                        Mtime.setText(nowday+"일차 "+nowtime+":00 시");
                        power1.setText("근력     "+nowpower);
                        checking();
                        return;
                    }
                    progressBar.setProgress(progress);
                    handler.postDelayed(this, INTERVAL);
                }
            };
            handler.postDelayed(decreaseRunnable, INTERVAL);

            // 버튼 터치 시 게이지 증가
            tapButton.setOnClickListener(v -> {
                progress += INCREASE_AMOUNT;
                if (progress > MAX_PROGRESS) progress = MAX_PROGRESS;
                progressBar.setProgress(progress);
                progressBar.setVisibility(View.VISIBLE);
                but_count++;

                if (but_count == 3) {
                    health_up.setVisibility(View.INVISIBLE);
                    health_down.setVisibility(View.VISIBLE);
                } else if (but_count == 6) {
                    health_up.setVisibility(View.VISIBLE);
                    health_down.setVisibility(View.INVISIBLE);
                    but_count = 0;
                }
            });
        }
    }

    public  void personlocate(){
        Random random = new Random();
        int rand = random.nextInt(2);
        if(rand == 0){
            player1.setVisibility(View.INVISIBLE);
            player2.setVisibility(View.VISIBLE);
        }else {
            player1.setVisibility(View.VISIBLE);
            player2.setVisibility(View.INVISIBLE);
        }

    }
    public void night_invisible(){
        nightImage.setVisibility(View.VISIBLE);
        dayImage.setVisibility(View.INVISIBLE);
        gamebtn.setVisibility(View.INVISIBLE);
        bookbtn.setVisibility(View.INVISIBLE);
        healthbtn.setVisibility(View.INVISIBLE);
        daybed.setVisibility(View.INVISIBLE);
        nightbed.setVisibility(View.VISIBLE);
    }
    public void game_tap(View view) {
        nowhp = nowhp-20;
        life_gau.setProgress(nowhp);
        hp1.setText("체력 : "+ (nowhp)+"/"+maxhp);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("nowday", nowday);
        intent.putExtra("nowtime", nowtime);

        intent.putExtra("nowhp", nowhp);
        intent.putExtra("maxhp", maxhp);
        intent.putExtra("nowhappy", nowhappy);

        intent.putExtra("nowstudy", nowstudy);
        intent.putExtra("nowpower", nowpower);
        intent.putExtra("nowhobby", nowhobby);
        intent.putExtra("nowstress", nowstress);

        startActivityForResult(intent,REQ_GAME);

    }

    public void book_clicked(View view) {
        nowhp = nowhp-20;
        life_gau.setProgress(nowhp);
        hp1.setText("체력 : "+ (nowhp)+"/"+maxhp);

        Intent intent = new Intent(MainActivity.this, oxquiz.class);
        intent.putExtra("nowday", nowday);
        intent.putExtra("nowtime", nowtime);

        intent.putExtra("nowhp", nowhp);
        intent.putExtra("maxhp", maxhp);
        intent.putExtra("nowhappy", nowhappy);

        intent.putExtra("nowstudy", nowstudy);
        intent.putExtra("nowpower", nowpower);
        intent.putExtra("nowhobby", nowhobby);
        intent.putExtra("nowstress", nowstress);

        startActivityForResult(intent,REQ_QUIZ);

    }
    public void daybedclicked(View view) {
        nowhp +=30;
        if(nowhp >maxhp){
            nowhp=maxhp;
        }
        life_gau.setProgress(nowhp);
        hp1.setText("체력 : "+ (nowhp)+"/"+maxhp);
        nowtime +=2;
        Mtime.setText(nowday+"일차 "+nowtime+":00 시");
        personlocate();

        if (nowtime >= 22) {
            night_invisible();
        }
    }
    public void nightbedclicked(View view) {
        nowtime = 8;
        nowday += 1;
        Mtime.setText(nowday + "일차 " + nowtime + ":00 시");
        nowD_day -= 1;
        D_day.setText("D-" + nowD_day);

        personlocate();
        if (nowhp <= 0) {
            nowhp = maxhp / 2;
            life_gau.setProgress(nowhp);
            hp1.setText("체력 : " + (nowhp) + "/" + maxhp);
        } else if (nowhp > 0) {
            nowhp = maxhp;
            life_gau.setProgress(maxhp);
            hp1.setText("체력 : " + (maxhp) + "/" + maxhp);
        }
        if (nowstress >= 20) {
            nowhappy -= 20;
            happy_gau.setProgress(nowhappy);
            happinessText.setText("행복 : " + nowhappy + "/50");
        } else if (nowstress >= 10) {
            nowhappy -= 10;
            happy_gau.setProgress(nowhappy);
            happinessText.setText("행복 : " + nowhappy + "/50");
        }

        if (nowhappy <= 0) {
            /////////////////////////////////////////베드엔딩 이동///////////////
            Intent intent = new Intent(MainActivity.this, badending.class);
            startActivity(intent);
            finish();
        }
        nightImage.setVisibility(View.INVISIBLE);
        dayImage.setVisibility(View.VISIBLE);
        gamebtn.setVisibility(View.VISIBLE);
        bookbtn.setVisibility(View.VISIBLE);
        healthbtn.setVisibility(View.VISIBLE);
        daybed.setVisibility(View.VISIBLE);
        nightbed.setVisibility(View.INVISIBLE);
        if (nowD_day == 0) {
            if (nowstudy < 20) {
                Intent intent = new Intent(MainActivity.this, badending.class);
                intent.putExtra("nowstudy", nowstudy);
                startActivity(intent);
                finish();
            } else if (nowstudy >= 20) {
                Intent intent = new Intent(MainActivity.this, happyending.class);
                intent.putExtra("nowstudy", nowstudy);
                startActivity(intent);
                finish();
            }


        }
    }
    public void checking(){
        if (nowtime >= 22) {
            night_invisible();
        }
        if (nowhappy <= 0) {
            /////////////////////////////////////////베드엔딩 이동///////////////
            Intent intent = new Intent(MainActivity.this, badending.class);
            startActivity(intent);
            finish();
        }
        if(nowhp <=0){
            Mtime.setText(nowday+"일차 "+"22"+":00 시");
            Toast.makeText(MainActivity.this, "탈진해버렸다..", Toast.LENGTH_LONG).show();
            nowhappy -=10;
            happy_gau.setProgress(nowhappy);
            happinessText.setText("행복 : "+nowhappy+"/50");
            nowstress +=5;
            stress1.setText("스트레스     "+nowstress);
            night_invisible();
        }
    }
    public void option (View view){
        option_count++;
        if (option_count == 1) {
            opt1.setVisibility(View.VISIBLE);
            opt2.setVisibility(View.VISIBLE);
            opt3.setVisibility(View.VISIBLE);
            opt4.setVisibility(View.VISIBLE);
        } else if (option_count == 2) {
            opt1.setVisibility(View.INVISIBLE);
            opt2.setVisibility(View.INVISIBLE);
            opt3.setVisibility(View.INVISIBLE);
            opt4.setVisibility(View.INVISIBLE);
            option_count = 0;
        }
    }
}
