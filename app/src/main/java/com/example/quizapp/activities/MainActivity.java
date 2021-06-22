package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.quizapp.R;
import com.example.quizapp.adapters.QuizAdapter;
import com.example.quizapp.models.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
     private ActionBarDrawerToggle drawerToggle;
     ArrayList<Quiz> quizzes=new ArrayList<>();
     RecyclerView recyclerView;
    FloatingActionButton btndatepicker;
    DrawerLayout mainDrawer;
    NavigationView navigation;
     private FirebaseFirestore db;
     public String TAG="Rehan";

     // Instance of QuizAdapter
    QuizAdapter adapter=new QuizAdapter(R.layout.quiz_item,quizzes);


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //PopulateDummy();
        setUpViews();
        recyclerView=findViewById(R.id.quizRecyclerview);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }


    public void setUpViews(){

        setUpFireStore();    // Fire Handling Firestore Manipulation
        setUpDrawerLayout();  // Handling Drawer
        setUpDatePicker();     // DatePicker Handling
    }

    public void setUpDatePicker(){
        btndatepicker=findViewById(R.id.btnDatePicker);
        MaterialDatePicker.Builder materialDateBuilder=MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker<Long> materialDatePicker=materialDateBuilder.build();
        btndatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        // When clicked on OK button
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPositiveButtonClick(Long selection) {
                    // Get the DAte and send it to QuestionActivity Class
                    // Formatter
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy", Locale.US);
                    Date selectedDate=new Date(selection);
                    Log.d("DATE2",selectedDate.toString());
                    String date=dateFormat.format(selectedDate);
                    Log.d("DATE2",date);
                    Intent intent=new Intent(MainActivity.this, QuestionActivity.class);
                    // Sending Date Also to QuestionActivity
                    intent.putExtra("DATE",date);
                    startActivity(intent);
            }
        });

        // When User clicks on cancel button
        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DATE",materialDatePicker.getHeaderText());
            }
        });

    }

    // Setup Drawer on left side
    public void setUpDrawerLayout(){
        setSupportActionBar(findViewById(R.id.AppBar));
        navigation=findViewById(R.id.NavView);
        mainDrawer=findViewById(R.id.MainDrawer);
        drawerToggle= new ActionBarDrawerToggle(this,findViewById(R.id.MainDrawer),R.string.app_name,R.string.app_name);
        drawerToggle.syncState();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int Id=item.getItemId();
                if (Id == R.id.btnProfile) {
                    Log.d("Rehan","chek1");
                    //navigation.getMenu().getItem(0).setChecked(true);
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                Log.d("Rehan","check2");
                mainDrawer.closeDrawers();
                return true;
            }

        });



    }


    // Handling Fire Store Manipulation
    public void setUpFireStore(){
        db=FirebaseFirestore.getInstance();
        // Fetching Data from Fire store
        db.collection("quizzes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i(TAG,"check2");
                        if (task.isSuccessful()) {
                            Log.i(TAG,"check3");
                            quizzes.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG, document.getId() + " => " + document.getData());
                                // Add new Fetched Quiz to quizzes List
                                quizzes.add(document.toObject(Quiz.class));
                                // Should notify that Dataset changed
                                adapter.notifyDataSetChanged();
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


    // Just for Demonstration purposes
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