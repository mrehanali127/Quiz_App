package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.quizapp.R;
import com.example.quizapp.adapters.QuizAdapter;
import com.example.quizapp.models.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     private ActionBarDrawerToggle drawerToggle;
     ArrayList<Quiz> quizzes=new ArrayList<>();
     RecyclerView recyclerView;
     private FirebaseFirestore db;
     public String TAG="Rehan";



    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PopulateDummy();
        setUpViews();


        recyclerView=findViewById(R.id.quizRecyclerview);
        QuizAdapter adapter=new QuizAdapter(R.layout.quiz_item,quizzes);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void setUpViews(){

        setUpFireStore();
        setUpDrawerLayout();
    }

    public void setUpDrawerLayout(){
        setSupportActionBar(findViewById(R.id.AppBar));
        drawerToggle= new ActionBarDrawerToggle(this,findViewById(R.id.MainDrawer),R.string.app_name,R.string.app_name);
        drawerToggle.syncState();
    }

    public void setUpFireStore(){
        db=FirebaseFirestore.getInstance();
        Log.i(TAG,"check1");
        db.collection("quizzes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i(TAG,"check2");
                        if (task.isSuccessful()) {
                            Log.i(TAG,"check3");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.i(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void PopulateDummy(){
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));
        quizzes.add(new Quiz("12-10-2020","12-10-2020"));

    }
}