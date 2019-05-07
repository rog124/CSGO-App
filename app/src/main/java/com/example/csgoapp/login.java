package com.example.csgoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button buttonLogin;
    private EditText editTextLoginEmail;
    private EditText editTextLoginPassword;
    private Button userRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FirebaseApp.initializeApp(this);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified())
        {
            finish();
            startActivity(new Intent(login.this,userdashboard.class));
        }

        progressDialog=new ProgressDialog(this);
        buttonLogin=(Button)findViewById(R.id.buttonLoginId);
        editTextLoginEmail=(EditText)findViewById(R.id.editTextLoginId);
        editTextLoginPassword=(EditText)findViewById(R.id.editTextPasswordId);
        userRegister=(Button) findViewById(R.id.userRegister);

        buttonLogin.setOnClickListener(this);
        userRegister.setOnClickListener(this);
    }

    private void loginUser()
    {
        String Email=editTextLoginEmail.getText().toString().trim();
        String Password=editTextLoginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(Email))
        {
            // Enter valid mail address
            Toast.makeText(this,"Enter valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password))
        {
            //Enter valid password or type password
            Toast.makeText(this, "Enter tour password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Wait Signing in .....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful())
                {
                    if(firebaseAuth.getCurrentUser().isEmailVerified())
                    {
                        Toast.makeText(login.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        //start user hltv profile
                        startActivity(new Intent(getApplicationContext(),userdashboard.class));
                    }
                    else
                    {
                        Toast.makeText(login.this,"Verify your Email address",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        if(v == buttonLogin)
        {
            loginUser();
        }
        if(v == userRegister)

        {
           finish();
           startActivity(new Intent(this,register.class));
        }

    }
}
