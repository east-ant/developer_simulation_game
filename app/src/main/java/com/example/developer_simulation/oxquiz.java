package com.example.developer_simulation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class oxquiz extends AppCompatActivity {

    private String[] questions = new String[30];
    private boolean[] answers = new boolean[30];
    private TextView tvQuestion,life;
    private Button btnO, btnX,comeback;
    private Random rand = new Random();
    // 현재 퀴즈 인덱스, 점수
    private int currentIndex = 0;
    private int score = 0;

    // 이미 뽑은 문제
    private boolean[] used = new boolean[30];
    private int usedCount = 0;

    private int fulllife=5;
    private int nowlife = fulllife;

    private int nowhp, maxhp, nowtime, nowhappy, nowhobby, nowday,nowstudy,nowpower,nowstress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oxquiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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


        tvQuestion = findViewById(R.id.quiztext);
        btnO = findViewById(R.id.Right);
        btnX = findViewById(R.id.Wrong);
        life= findViewById(R.id.quiz_life);
        comeback = findViewById(R.id.comeback1);

        loadQuestion();

        currentIndex = getRandomIndex();
        tvQuestion.setText(questions[currentIndex]);
    }
    //문제와 정답 로드
    private void loadQuestion() {
        InputStream qInput = getResources().openRawResource(R.raw.quiz);
        InputStream aInput = getResources().openRawResource(R.raw.answer);
        BufferedReader qReader = new BufferedReader(new InputStreamReader(qInput));
        BufferedReader aReader = new BufferedReader(new InputStreamReader(aInput));

        try {
            String qLine;
            String aLine;
            int idx = 0;

            while (idx < 30
                    && (qLine = qReader.readLine()) != null
                    && (aLine = aReader.readLine()) != null) {
                questions[idx] = qLine.trim();
                answers[idx] = aLine.trim().equalsIgnoreCase("O");
                idx++;
            }
        } catch (IOException e) {
            Toast.makeText(this, "오류 발생.", Toast.LENGTH_LONG).show();
        }
    }

    //문제 랜덤
    private int getRandomIndex() {
        while (true) {
            int r = rand.nextInt(questions.length); // 0~29
            //중복 체크
            if (!used[r]) {
                used[r] = true;
                usedCount++;
                return r;
            }
        }
    }

    //정답 확인
    private void checkAnswer(boolean userChoice) {
        boolean correct = answers[currentIndex];

        if (userChoice == correct) {
            score++;
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show();
        } else {
            nowlife--;
            life.setText(nowlife+"");
            Toast.makeText(this, "오답!", Toast.LENGTH_SHORT).show();
        }
        //퀴즈 성공
        if (score >= 5 ) {
            Toast.makeText(this, "공부 성공!", Toast.LENGTH_LONG).show();
            tvQuestion.setText("성공!!");
            comeback.setVisibility(View.VISIBLE);
            btnO.setVisibility(View.INVISIBLE);
            btnX.setVisibility(View.INVISIBLE);
        }
        //0목숨일 때
        if (nowlife == 0) {
            Toast.makeText(this, "공부 실패..", Toast.LENGTH_LONG).show();
            tvQuestion.setText("실패...");
            comeback.setVisibility(View.VISIBLE);
            btnO.setVisibility(View.INVISIBLE);
            btnX.setVisibility(View.INVISIBLE);
        }
    }

    //다음 문제
    private void moveToNext() {
        if (score >= 5) {
            return;
        }
        currentIndex++;
        tvQuestion.setText(questions[currentIndex]);
    }

    public void onClickO(View view) {
        checkAnswer(true);
        moveToNext();
    }

    public void onClickX(View view) {
        checkAnswer(false);
        moveToNext();
    }

    public void backmain(View view) {

        if (score >= 5) {
            Toast.makeText(this, "퀴즈 성공! (5문제 정답 달성)", Toast.LENGTH_LONG).show();
            nowstudy +=10;
            nowhappy -=10;
            nowstress +=5;
            nowtime +=2;
        }
        //0목숨일 때
        if (nowlife == 0) {
            Toast.makeText(this, "공부 실패", Toast.LENGTH_LONG).show();
            nowstudy +=3;
            nowhappy -=15;
            nowstress +=6;
            nowtime +=2;
        }
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
