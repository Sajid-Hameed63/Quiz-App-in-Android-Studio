package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class ResultActivtiy extends AppCompatActivity {
    TextView textscore, textAnswer;
    Quiz[] quiz;
    Button buttonReturnToMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activtiy);
        textscore = (TextView) findViewById(R.id.txtScore);
        textAnswer = (TextView) findViewById(R.id.txtAnswer);
        buttonReturnToMainPage = findViewById(R.id.buttonReturnToMainPage);
        quiz = new Quiz[] {new Quiz()};
        buttonReturnToMainPage.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        setUpViews();
    }

    public void setUpViews() {
        String quizData = getIntent().getStringExtra("QUIZ");
        quiz[0] = new Gson().fromJson(quizData, (Type) Quiz.class);
        calculateScore();
        setUpAnswerViews();
    }

    public void setUpAnswerViews() {
        StringBuilder builder =  new StringBuilder();
//        for (Map.Entry<String, Question> )
        int questionNumber = 1;
        Map<String,Question> questions = quiz[0].getQuestion();
        for (Map.Entry<String, Question> questionMap: quiz[0].getQuestion().entrySet()){
            Question question = questionMap.getValue();
            builder.append("<font color='#18206F'> <b> Question " + questionNumber + ": " + question.getDescription()+"</b> </font><br/><br/>");
            builder.append("<font color='#009688'> <b> Answer: " + question.getAnswer()+"</b> </font><br/><br/>");
            questionNumber++;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textAnswer.setText(Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT));
        }
        else{
            textAnswer.setText(Html.fromHtml(builder.toString()));
        }
    }

    public void calculateScore() {
        int score = 0;
        for (Map.Entry<String, Question> questionMap: quiz[0].getQuestion().entrySet()){
            Question question = questionMap.getValue();
            if (question.getAnswer().equals(question.getUserAnswer())){
                score += 10;
            }
        }
        textscore.setText("Your Numbers: " + score);
    }
}