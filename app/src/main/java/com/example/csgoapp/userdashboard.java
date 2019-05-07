package com.example.csgoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userdashboard extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView userMailDash;
    private ImageButton logoutButton;
    private LinearLayout profile,topteams,topplayer,stats,highlights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null && firebaseAuth.getCurrentUser().isEmailVerified())
        {
            finish();
            startActivity(new Intent(this,login.class));
        }

        FirebaseUser user= firebaseAuth.getCurrentUser();


        userMailDash=(TextView)findViewById(R.id.userEmailDash);

        userMailDash.setText("Hello "+ user.getEmail());
        userMailDash.setTextSize(12);
        logoutButton=(ImageButton)findViewById(R.id.logoutButtonId);
        profile=(LinearLayout)findViewById(R.id.profile);
        topplayer=(LinearLayout)findViewById(R.id.topplayer);
        topteams=(LinearLayout)findViewById(R.id.topteams);
        stats=(LinearLayout)findViewById(R.id.stats);
        highlights=(LinearLayout)findViewById(R.id.highlights);
        logoutButton.setOnClickListener(this);
        profile.setOnClickListener(this);
        topteams.setOnClickListener(this);
        topplayer.setOnClickListener(this);
        stats.setOnClickListener(this);
        highlights.setOnClickListener(this);
        }
        private void signOut()
        {

        }
    @Override
    public void onClick(View v) {

        if (v == logoutButton)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,login.class));
        }
        if(v == profile)
        {
            startActivity(new Intent(this,profile.class));
        }
        if(v == topteams)
        {
            startActivity(new Intent(this,topteams.class));
        }
        if(v == topplayer)
        {
            startActivity(new Intent(this,topplayer.class));
        }
        if(v == stats)
        {
            startActivity(new Intent(this,stats.class));
        }
        if(v == highlights)
        {
            startActivity(new Intent(this,highlights.class));
        }

    }
}

