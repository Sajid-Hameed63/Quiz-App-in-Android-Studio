package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.adapters.OptionAdapter;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<Quiz> quizzes;
    Map<String,Question> questions;
    int index;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        quizzes = new ArrayList<>();
        questions = Map.of();
        index = 1;
        setUpFirestore();
        setEventListener();
    }

    public void setEventListener() {
        Button ButtonbtnPrevious = findViewById(R.id.btnPrevious);
        Button ButtonbtnNext = findViewById(R.id.btnNext);
        Button ButtonbtnSubmit = findViewById(R.id.btnSubmit);
        ButtonbtnPrevious.setOnClickListener(previous -> {
            index--;
            bindViews();
        });
        ButtonbtnNext.setOnClickListener(next -> {
            index++;
            bindViews();
        });
        ButtonbtnSubmit.setOnClickListener(submit -> {
            Log.d("FinalQuiz", questions.toString());

            Intent intent = new Intent(this, ResultActivtiy.class);
            String json = new Gson().toJson(quizzes.get(0));
            Log.d("FINISH",json);
            intent.putExtra("QUIZ",json);
            startActivity(intent);
            finish();
        });

    }

    public void setUpFirestore() {
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        String date = getIntent().getStringExtra("Date");
        if(date != null){
            firestore.collection("quizzes4").whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener(v -> {
                        if(v != null && !v.isEmpty()){
//                            Log.d("Data", v.toObjects(Quiz.class).toString());
                            quizzes = (ArrayList<Quiz>) v.toObjects(Quiz.class);
                            questions = quizzes.get(0).getQuestion();
                            bindViews();
                        }

                    });
        }
    }

    public void bindViews() {
        Button ButtonbtnPrevious = findViewById(R.id.btnPrevious);
        Button ButtonbtnNext = findViewById(R.id.btnNext);
        Button ButtonbtnSubmit = findViewById(R.id.btnSubmit);

        ButtonbtnPrevious.setVisibility(View.INVISIBLE);
        ButtonbtnNext.setVisibility(View.INVISIBLE);
        ButtonbtnSubmit.setVisibility(View.INVISIBLE);

        if(index == 1){
            // first question
            ButtonbtnNext.setVisibility(View.VISIBLE);
        }
        else if(index == questions.size()){
            // last question
            ButtonbtnSubmit.setVisibility(View.VISIBLE);
            ButtonbtnPrevious.setVisibility(View.VISIBLE);
        }
        else{
            // middle questions
            ButtonbtnPrevious.setVisibility(View.VISIBLE);
            ButtonbtnNext.setVisibility(View.VISIBLE);
        }

        Question question = questions.get("question" + index);


        // Dummy Question
//        Question question = new Question(
//                "What is my name?",
//                "Sajid",
//                "Asim",
//                "Riaz",
//                "Azam",
//                "Sajid"
//        );


        TextView textDescription = findViewById(R.id.description);
        textDescription.setText(question.description);
        OptionAdapter optionAdapter = new OptionAdapter(this, question);
        RecyclerView optionList = findViewById(R.id.optionList);
        optionList.setLayoutManager(new LinearLayoutManager(this));
        optionList.setAdapter(optionAdapter);
        optionList.setHasFixedSize(true);
    }
}