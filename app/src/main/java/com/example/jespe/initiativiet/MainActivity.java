package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button LoginBtn;
    EditText EmailInp,PassInp;
    TextView SignupBtn,FPassBtn;

    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Btns
        LoginBtn = (Button) findViewById(R.id.loginbtn);

        //EditText
        EmailInp = (EditText) findViewById(R.id.login_email);
        PassInp  = (EditText) findViewById(R.id.login_password);

        //Temporary hardcoded login info for lazy teachers/testers
        EmailInp.setText("test@mail.com");
        PassInp.setText("lamepassword");

        //TextView
        SignupBtn = (TextView) findViewById(R.id.signup);
        FPassBtn  = (TextView) findViewById(R.id.forgotpass);

        //Layout
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);

        //ActionListener
        LoginBtn.setOnClickListener(this);
        FPassBtn.setOnClickListener(this);
        SignupBtn.setOnClickListener(this);

        //Firebase init
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        //Check session
        if(auth.getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this,ValgActivity.class));
    }

    //Action listener methods
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotpass:
                startActivity(new Intent(MainActivity.this,ForgotPassActivity.class));
                finish();
                break;
            case R.id.signup:
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
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
                try {
                    userlogin(EmailInp.getText().toString(), PassInp.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();

                    //Snackbar for lightweight feedback
                    Snackbar snackBar = Snackbar.make(activity_main,"Email and/or password cannot be empty",Snackbar.LENGTH_SHORT);
                    snackBar.show();
                }
                break;
        }
    }

    //login method
    private void userlogin(final String email, final String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    if(password.length() < 6) {
                        //Snackbar for lightweight feedback
                        Snackbar snackBar = Snackbar.make(activity_main,"Password length must be over 6",Snackbar.LENGTH_SHORT);
                        snackBar.show();
                    }else{
                        Snackbar snackbar = Snackbar.make(activity_main,"Error: "+task.getException().getMessage(),Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
                else{
                    startActivity(new Intent(MainActivity.this,Test.class));
                }
            }
        });
    }
}