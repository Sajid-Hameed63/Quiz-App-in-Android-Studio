package com.example.quizapp.activities;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.adapters.QuizAdapter;
import com.example.quizapp.models.Quiz;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Quiz> quizList = new ArrayList<>();
    DrawerLayout mainDrawer;
//    QuizAdapter adapter = new QuizAdapter(this, quizList);
    QuizAdapter adapter = new QuizAdapter(this, quizList );
    FirebaseFirestore firestore;
    NavigationView navigationView;
//    Item btnProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Error point
        navigationView = findViewById(R.id.navigationView);
        mainDrawer = (DrawerLayout) findViewById(R.id.mainDrawer);
//        populateDummyData();
        setUpViews();
    }

    public void setUpViews() {
        setUpFirestore();
        setUpDrawerLayout();
        setUpRecyclerView();
        setUpDatePicker();
    }

    public void setUpDatePicker() {
        FloatingActionButton floatingDate = findViewById(R.id.btnDatePicker);
        floatingDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker;
            datePicker = MaterialDatePicker.Builder.datePicker().build();
            datePicker.show(getSupportFragmentManager(), "DatePicker");
            MaterialDatePicker<Long> finalDatePicker = datePicker;
//            MaterialDatePicker<Long> finalDatePicker2 = datePicker;
            datePicker.addOnPositiveButtonClickListener(positive -> {
                Log.d("DatePicker", finalDatePicker.getHeaderText());
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
                String date = dateFormatter.format(positive);
                Log.d("DateConverted", date);
                Intent intent = new Intent(this, QuestionActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            });
            MaterialDatePicker<Long> finalDatePicker1 = datePicker;
            datePicker.addOnNegativeButtonClickListener(v1 -> {
                Log.d("DatePicker", finalDatePicker1.getHeaderText());
            });
            datePicker.addOnCancelListener(cancel -> Log.d("DatePicker", "Date Picker Cancelled!"));
        });

    }

    public void setUpFirestore() {
        firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("quizzes4");
        collectionReference.addSnapshotListener( (value, error) -> {
            if(value == null || error!= null){
                Toast.makeText(this, "Error fetching data!!", Toast.LENGTH_SHORT).show();
            }
//            Log.d("Data", value.toObjects().toString());
            Log.d("DataR", value.toObjects(Quiz.class).toString());
            quizList.clear();
            quizList.addAll(value.toObjects(Quiz.class));
            adapter.notifyDataSetChanged();
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.quizRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setUpDrawerLayout() {
        setSupportActionBar(findViewById(R.id.appBar));
        actionBarDrawerToggle = new ActionBarDrawerToggle( this, findViewById(R.id.mainDrawer), R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btnProfile){
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnFollowUs){
                Intent intent = new Intent(this, FollowUsActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnRateUs){
                Intent intent = new Intent(this, RateUsActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}