package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button started;
    String TAG="Rehan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);
        // getting instance of firebase authentication
        mAuth=FirebaseAuth.getInstance();

        started=findViewById(R.id.btngetstarted);

        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect("LOGIN");
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this,"User is already logged in",
                    Toast.LENGTH_SHORT).show();
            redirect("MAIN");
        }
    }


    /* Redirect To othter activities according to need */
    public void redirect(String name){
        Intent intent=null;
        if (name=="MAIN") {
            intent = new Intent(IntroPage.this, MainActivity.class);
        }
        else if(name=="LOGIN"){
            intent=new Intent(IntroPage.this, LoginActivity.class);
        }
        else{
            Log.i(TAG,"No Path Exists");
        }
        // starting that activity
        startActivity(intent);
        finish();
    }


}