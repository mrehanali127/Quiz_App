package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText emailaddr;
    EditText pass;
    EditText confirm;
    Button btnSignUp;
    TextView login;
    String TAG="Rehan";
    // creating object of firebase authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailaddr=findViewById(R.id.ETEmailAdress);
        pass=findViewById(R.id.ETPassword);
        confirm=findViewById(R.id.ETConfirmPassword);
        btnSignUp=findViewById(R.id.btnsignup);
        login=findViewById(R.id.btnlogin);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                //intent.putExtra("COUNTER",counter);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(SignupActivity.this,"Success",Toast.LENGTH_SHORT).show();
        }
    }

    // Main Functionality of SignUp User
    private void SignupUser(){
        String email=emailaddr.getText().toString();
        String password=pass.getText().toString();
        String confirm_password=confirm.getText().toString();

        if(email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()){
            Toast.makeText(SignupActivity.this, "Email & password Can't be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(confirm_password))){
            Toast.makeText(SignupActivity.this, "Password and confirm password do not match",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}