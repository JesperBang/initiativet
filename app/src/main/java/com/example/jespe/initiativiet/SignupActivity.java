package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button RegisterBtn;
    TextView logmein,forgotpass;
    EditText EmailInp, PassInp;
    RelativeLayout SignUp_Activity;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Buttons
        RegisterBtn = (Button) findViewById(R.id.loginbtn);

        //TextViews
        logmein     = (TextView) findViewById(R.id.logmein);
        forgotpass  = (TextView) findViewById(R.id.forgotpass);

        //EditText
        EmailInp    = (EditText) findViewById(R.id.EmailInp);
        PassInp     = (EditText) findViewById(R.id.PassInp);

        //Layout
        SignUp_Activity = (RelativeLayout) findViewById(R.id.activity_signup);

        //ActionListeners
        RegisterBtn.setOnClickListener(this);
        logmein.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

        //Firebase Init
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logmein:
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.forgotpass:
                startActivity(new Intent(SignupActivity.this,ForgotPassActivity.class));
                finish();
                break;
            case R.id.loginbtn:
                //Method for hiding keyboard after submitting so the user can see snackbars easily
                // Check if no view has focus:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //Logging in user with email and pass
                signUpUser(EmailInp.getText().toString(),PassInp.getText().toString());
                break;
        }
    }
    private void signUpUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            //Error if pass is too short
                            snackbar = Snackbar.make(SignUp_Activity,"Error: "+task.getException().getMessage(),Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else{
                            //Success message
                            snackbar = Snackbar.make(SignUp_Activity,"Register success! ",Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            //Returning user to mainpage after 3.5 seconds, so that the user has a chance to reade the snackbar info.
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    // if you are redirecting from a fragment then use getActivity() as the context.
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            };

                            //Running the runnable after 3.5 seconds delay.
                            Handler h = new Handler();
                            h.postDelayed(r, 3500);

                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
    }
}