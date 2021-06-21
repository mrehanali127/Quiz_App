package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.adapters.OptionAdapter;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    TextView description;
    RecyclerView optionlist;
    Button previous;
    Button next;
    Button submit;
    private ArrayList<Quiz> quizzes = new ArrayList<>();
    private Map<String, Question> questions;
    private int index=1;

    private FirebaseFirestore db;
    private String TAG="Rehan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Log.i("Rehan","check1");
        description=findViewById(R.id.Description);
        optionlist=findViewById(R.id.optionList);
        previous=findViewById(R.id.btnPrevious);
        next=findViewById(R.id.btnNext);
        submit=findViewById(R.id.btnSubmit);
       // bindviews();
        setUpFirestore();
        setUpEventListener();
    }


    private void setUpEventListener() {
        // When User clicks on Previous Button
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                bindviews();
            }
        });
        // When user click on next Button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                bindviews();
            }
        });

        // When user submit his quiz
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SUBMIT",questions.toString());
            }
        });

    }

    // Fetch Data from firestore w.r.t Title
    private void setUpFirestore(){
        db=FirebaseFirestore.getInstance();
        String  date= getIntent().getStringExtra("DATE");
        if(date!=null) {
            db.collection("quizzes").whereEqualTo("title", date)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.R)
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                quizzes.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.i(TAG, document.getId() + " => " + document.getData());
                                    //Log.i(TAG, document.toObject(Quiz.class).toString());
                                    Log.i(TAG, document.toObject(Quiz.class).title);
                                    // Add quiz into quizzes List
                                    quizzes.add(document.toObject(Quiz.class));
                                }
                                // Getting questions from selected Quiz
                                questions=quizzes.get(0).questions;
                                bindviews();
                            } else {
                                Log.i(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }

    }


    // Bind View (Change View dynmically according to user)
    private void bindviews(){
        previous.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        // If first question
        if(index==1){
            next.setVisibility(View.VISIBLE);

        }
        // if last question
        else if(index==questions.size()){
            previous.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
        //middle questions
        else{
            previous.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }

        // Getting question by passing index
        Question question=questions.get("question"+index);
        Log.i("Rehan",question.toString());
        description.setText(question.description);
        OptionAdapter optionAdapter=new OptionAdapter(R.layout.option_item,question);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        optionlist.setAdapter(optionAdapter);
        optionlist.setLayoutManager(linearLayoutManager);
        //optionlist.setHasFixedSize(true);

    }
}