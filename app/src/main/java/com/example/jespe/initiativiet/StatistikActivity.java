package com.example.jespe.initiativiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatistikActivity extends AppCompatActivity implements View.OnClickListener {

    Button ValgBtn,ForumBtn, StatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        ValgBtn = (Button) findViewById(R.id.ValgBtn);
        ForumBtn = (Button) findViewById(R.id.ForumBtn);
        StatBtn = (Button) findViewById(R.id.StatBtn);
        ValgBtn.setOnClickListener(this);
        ForumBtn.setOnClickListener(this);

        StatBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ValgBtn:
                Intent ivalg = new Intent(StatistikActivity.this, ValgActivity.class);
                startActivity(ivalg);
                finish();
                break;
            case R.id.ForumBtn:
                Intent iforum = new Intent(StatistikActivity.this, ForumActivity.class);
                startActivity(iforum);
                finish();
                break;
        }
    }
}
