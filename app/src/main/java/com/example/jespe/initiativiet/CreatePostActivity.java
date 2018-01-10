package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    Button SubmitBtn;
    EditText textfield,subject;
    Snackbar snackbar;
    ConstraintLayout create_activity;
    List<ForumEntry> sampleForumEntries;
    private DatabaseReference fb;
    private DatabaseReference forumCloudEndPoint;
    Spinner dropdown, dropdown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        //Firebase Reference
        fb =  FirebaseDatabase.getInstance().getReference();
        forumCloudEndPoint = fb.child("forumentries");

        //EditText
        textfield = (EditText) findViewById(R.id.textfield);
        subject = (EditText) findViewById(R.id.subject);

        //Button
        SubmitBtn = (Button) findViewById(R.id.SubmitBtn);

        create_activity = (ConstraintLayout) findViewById(R.id.create_activity);

        //Action Listener
        SubmitBtn.setOnClickListener(this);

        //Dropdown menus
        //Dropdown menu for category
        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Miljø", "Finans", "Erhverv"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        //Dropdown for debat type
        dropdown2 = (Spinner)findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Normal", "1on1 Debat", "squad Debat"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

    }

    private void addInitialDataToFirebase() {
        try {
            if (textfield.getText().toString().length() < 10){
                snackbar = Snackbar.make(create_activity,"Post is too short! ",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else if (subject.getText().toString().contains("Title")){
                snackbar = Snackbar.make(create_activity,"Change Title of post! ",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                sampleForumEntries = getSampleForumEntries(textfield.getText().toString(),subject.getText().toString(), dropdown.getSelectedItem().toString(), dropdown2.getSelectedItem().toString() );
            }
        for (ForumEntry forumEntry : sampleForumEntries) {
            String key = forumCloudEndPoint.push().getKey();
            forumEntry.setJournalId(key);
            forumCloudEndPoint.child(key).setValue(forumEntry).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Snackbar snackBar = Snackbar.make(create_activity,"Your post was successful!",Snackbar.LENGTH_SHORT);
                        snackBar.show();

                        //Returning user to mainpage after 2.5 seconds, so that the user has a chance to reade the snackbar info.
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                // if you are redirecting from a fragment then use getActivity() as the context.
                                startActivity(new Intent(CreatePostActivity.this, ForumActivity.class));
                                finish();
                            }
                        };

                        //Running the runnable after 2.5 seconds delay.
                        Handler h = new Handler();
                        h.postDelayed(r, 2500);
                    }
                    else{
                        Snackbar snackBar = Snackbar.make(create_activity,"Failed to post on forum. Try again!",Snackbar.LENGTH_SHORT);
                        snackBar.show();
                    }
                }
            });
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static List<ForumEntry> getSampleForumEntries(String Content, String Title, String Tag, String Type) {
            List<ForumEntry> journalEntries = new ArrayList<>();
            //create forum Entry
            ForumEntry FE = new ForumEntry();
            FE.setTitle(Title);
            FE.setContent(Content);
            FE.setType(Type);
            FE.setTagName(Tag);
            Calendar cal = GregorianCalendar.getInstance();
            FE.setDateModified(cal.getTimeInMillis());
            FE.setDateCreated(cal.getTimeInMillis());


            journalEntries.add(FE);

            return journalEntries;
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SubmitBtn:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                addInitialDataToFirebase();

                //startActivity(new Intent(CreatePostActivity.this,ForumActivity.class));
                //finish();
                break;
        }
    }
}
