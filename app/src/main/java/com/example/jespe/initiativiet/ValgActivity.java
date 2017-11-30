package com.example.jespe.initiativiet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.BufferUnderflowException;

import static java.security.AccessController.getContext;

public class ValgActivity extends AppCompatActivity implements View.OnClickListener {

    Button StatsBtn, ForumBtn, ValgBtn, LogoutBtn;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valg);



        StatsBtn = (Button) findViewById(R.id.StatsButton);
        ForumBtn = (Button) findViewById(R.id.ForumBtn);
        ValgBtn = (Button) findViewById(R.id.ValgBtn);
        LogoutBtn = (Button) findViewById(R.id.LogoutBtn);


        ForumBtn.setOnClickListener(this);
        StatsBtn.setOnClickListener(this);
        LogoutBtn.setOnClickListener(this);

        ValgBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));

        //Firebase init
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        //For testing
        System.out.println(auth.getCurrentUser().getUid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StatsButton:
                Intent istat = new Intent(ValgActivity.this, StatistikActivity.class);
                startActivity(istat);
                break;
            case R.id.ForumBtn:
                Intent iforum = new Intent(ValgActivity.this, ForumActivity.class);
                startActivity(iforum);
                break;
            case R.id.LogoutBtn:
                logout(v);
                break;
        }

    }

    private void logout(View v) {
        auth.signOut();

        try {
            if(auth.getCurrentUser() == null) {
                startActivity(new Intent(ValgActivity.this,MainActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Snackbar snackBar = Snackbar.make(v,"User signed out",Snackbar.LENGTH_SHORT);
        snackBar.show();
    }
}
