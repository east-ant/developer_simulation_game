package com.example.developer_simulation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class happyending extends AppCompatActivity {
    int aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_happyending);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int study = getIntent().getIntExtra("nowstudy", 0); // ← 전달 받은 nowstudy 값

        // 예: TextView로 출력
        TextView studyTextView = findViewById(R.id.textView23); // 해당 TextView는 activity_happyending.xml에 있어야 함

        if(study >=35){
            aa = 2000;
        } else if (study >=29) {
            aa = 2500;
        } else if (study >=20) {
            aa = 2000;
        }
        studyTextView.setText("연봉 :  "+ aa +"↑"  );
    }
}