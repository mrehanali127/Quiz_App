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

public class LoginActivity extends AppCompatActivity {

    EditText emailaddr;
    EditText pass;
    Button btnsignin;
    TextView signup;
    String TAG="Rehan";

    // creating object of firebase authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailaddr=findViewById(R.id.ETEmailAdress1);
        pass=findViewById(R.id.ETPassword1);
        btnsignin=findViewById(R.id.btnlogin1);
        signup=findViewById(R.id.btnsignup1);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                //intent.putExtra("COUNTER",counter);
                startActivity(intent);
                finish();
            }
        });

    }

    private void SignIn(){
        String email=emailaddr.getText().toString();
        String password=pass.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Email & password Can't be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG,"check1");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG,"check2");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //Toast.makeText(LoginActivity.this, "Signed In Successfully",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            //intent.putExtra("COUNTER",counter);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG,"check3");
                    }
                });

    }
}