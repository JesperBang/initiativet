package com.example.jespe.initiativiet;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (savedInstanceState == null) {
            //Load "Main" Fragment
            Fragment frag = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new FirstFragment())
                    .commit();
        }

    }
}
