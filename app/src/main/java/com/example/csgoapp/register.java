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

import static android.widget.Toast.makeText;

public class register extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified())
        {
            finish();
            startActivity(new Intent(this,userdashboard.class));
        }
        progressDialog=new ProgressDialog(this);
        buttonRegister=(Button)findViewById(R.id.registerUserId);
        editTextEmail=(EditText)findViewById(R.id.editTextEmailId);
        editTextPassword=(EditText)findViewById(R.id.passwordid);
        textViewSignIn=(TextView)findViewById(R.id.textViewSignIn);
        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser()
    {
        String Email=editTextEmail.getText().toString().trim();
        String Password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(Email))
        {
            //email is empty
            makeText(this,"Please Enter valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password))
        {
            //password is empty
            makeText(this,"Please enter valid password",Toast.LENGTH_SHORT).show();
            return;
        }
        // if validations are ok then we need to procced further means register user on firebase
        progressDialog.setMessage("Registering user..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if mail is not registered

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                makeText(register.this, "User Registered verify your mail address", Toast.LENGTH_SHORT).show();
                                editTextEmail.getText().clear();
                                editTextPassword.getText().clear();
                            } else {
                                Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                // if email already registered
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(register.this, "Email Already Registered ,Verify it", Toast.LENGTH_SHORT).show();
                    editTextEmail.getText().clear();
                    editTextPassword.getText().clear();
                }
                //  else
                // {
                //    finish();
                //    makeText(register.this,"Registration Failed,Try again",Toast.LENGTH_SHORT).show();
                // }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister)
        {
            registerUser();
        }
        if(v == textViewSignIn)
        {
            finish();
            Intent intent=new Intent(register.this,login.class);
            startActivity(intent);
        }
    }
}
