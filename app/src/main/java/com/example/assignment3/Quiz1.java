package com.example.assignment3;


import java.util.Timer;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import java.util.Calendar;
import android.os.Handler;
import java.util.ArrayList;
import java.util.TimerTask;
import android.view.Gravity;
import java.io.Serializable;
import android.widget.Toast;
import android.app.Activity;
import android.widget.Button;
import java.util.Collections;
import android.content.Intent;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.os.CountDownTimer;
import java.text.SimpleDateFormat;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Timer quizTimer;
    public int counter;
    RadioGroup radioGroup;
    TimerTask quizTimerTask;
    int cursor = 0, quizTime = 60;
    TextView question, time;
    RadioButton option, radio1, radio2, radio3;
    ArrayList<String> queries, allchoices, results, chosen;

    void setVals() {
        question.setText("Q No " +Integer.toString(cursor + 1)+ ": "+ queries.get(cursor));
        radioGroup.clearCheck();
        ArrayList<String> temp = new ArrayList<String>(3);
        temp.add(allchoices.get(cursor * 3));
        temp.add(allchoices.get((cursor * 3) + 1));
        temp.add(allchoices.get((cursor * 3) + 2));
        Collections.shuffle(temp);
        radio1.setText(temp.get(0));
        radio2.setText(temp.get(1));
        radio3.setText(temp.get(2));
    }

    void saveChoice() {
        if(radioGroup.getCheckedRadioButtonId()==-1)
            chosen.set(cursor, "Not Selected");
        else {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            option = (RadioButton) findViewById(selectedId);

            chosen.set(cursor, option.getText().toString());
        }
    }
    void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void goToPrevious(View view) {
        if(cursor == 0) {
            showToast("You are already on First Question:");
        }
        else {

            saveChoice();
            cursor--;
            setVals();
        }
    }

    public void goToNext(View view)
    {
        if(cursor == 9) {
            showToast("This is the Last Question:");
        }
        else {
            saveChoice();
            cursor++;
            setVals();
        }
    }

    public int result()
    {
        int count = 0;
        for (int i = 0; i < 10; i ++) {
            if(results.get(i).equals(chosen.get(i)))
                count++;
        }

        return count;
    }

    public void submitQuiz(View view)
    {
        saveChoice();
        int count = result();
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("Count", count);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = new Intent(this, MainActivity2.class);
        radioGroup = findViewById(R.id.radioGroup);
        question = findViewById(R.id.question);
        radio1 = findViewById(R.id.choice1);
        radio2 = findViewById(R.id.choice2);
        radio3 = findViewById(R.id.choice3);
        chosen = new ArrayList<String>(10);

        String[] questions = getResources().getStringArray(R.array.questions);
        queries = new ArrayList<String>(asList(questions));
        Collections.shuffle(queries);
        String[] choices = getResources().getStringArray(R.array.choices);
        allchoices = new ArrayList<String>(asList(choices));
        String[] corrects = getResources().getStringArray(R.array.corrects);
        results = new ArrayList<String>(asList(corrects));

        for (int i = 0; i < 10; i++) {
            chosen.add("");
        }
        setVals();

        time = (TextView) findViewById(R.id.time);
        new CountDownTimer(quizTime * 1000, 1000) {
            public void onTick(long millisUntilFinished){
                time.setText(String.valueOf(quizTime - counter) + " Remaining");
                counter++;
            }
            public  void onFinish() {
                saveChoice();
                int count = result();
                intent2.putExtra("Count", count);
                startActivity(intent2);
            }
        }.start();
    }
}