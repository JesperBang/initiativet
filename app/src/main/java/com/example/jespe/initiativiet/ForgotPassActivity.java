package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    Button ResetBtn;
    EditText EmailInp;
    TextView BackBtn;
    RelativeLayout activity_forgot;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        //Buttons
        ResetBtn = (Button) findViewById(R.id.ResetBtn);

        //EditText
        EmailInp = (EditText) findViewById(R.id.EmailInp);

        //TextView
        BackBtn = (TextView) findViewById(R.id.BackBtn);

        //Layout
        activity_forgot = (RelativeLayout) findViewById(R.id.activity_forgot_password);

        //Firebase init
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        BackBtn.setOnClickListener(this);
        ResetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ResetBtn:
                //Method for hiding keyboard after submitting so the user can see snackbars easily
                // Check if no view has focus:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //Resetting pass
                try {
                    resetPassword(EmailInp.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar snackBar = Snackbar.make(activity_forgot,"Error: "+e.getMessage().replace("String","Input"),Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }
                break;
            case R.id.BackBtn:
                startActivity(new Intent(ForgotPassActivity.this,MainActivity.class));
                finish();
                break;
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar snackBar = Snackbar.make(activity_forgot,"Please check your mail for reset link: "+email,Snackbar.LENGTH_SHORT);
                            snackBar.show();

                            //Returning user to mainpage after 3.5 seconds, so that the user has a chance to reade the snackbar info.
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    // if you are redirecting from a fragment then use getActivity() as the context.
                                    startActivity(new Intent(ForgotPassActivity.this, MainActivity.class));
                                    finish();
                                }
                            };

                            //Running the runnable after 3.5 seconds delay.
                            Handler h = new Handler();
                            h.postDelayed(r, 3500);
                        }
                        else{
                            Snackbar snackBar = Snackbar.make(activity_forgot,"Failed: "+task.getException().getMessage(),Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                    }
                });
    }
}