package com.example.jespe.initiativiet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    RelativeLayout SignUpActivity;

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
        SignUpActivity = (RelativeLayout) findViewById(R.id.activity_signup);

        //ActionListeners
        RegisterBtn.setOnClickListener(this);
        logmein.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

        //Firebase Init
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logmein:
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.forgotpass:
                startActivity(new Intent(SignupActivity.this,ForgotPassActivity.class));
                finish();
                break;
            case R.id.loginbtn:
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
                        if(!task.isSuccessful())
                        {
                            snackbar = Snackbar.make(SignUpActivity,"Error: "+task.getException(),Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else{
                            snackbar = Snackbar.make(SignUpActivity,"Register success! ",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
    }

}
