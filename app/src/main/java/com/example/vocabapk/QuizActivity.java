package com.example.vocabapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    int questionCount = 0;
    TextView question;
    Button option1, option2, option3, option4, next;
    List<Word> list;
    DatabaseReference wordsDB;
    long maxId = 0;
    int cnt = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question  = findViewById(R.id.question);
        option1  = findViewById(R.id.option1);
        option2  = findViewById(R.id.option2);
        option3  = findViewById(R.id.option3);
        option4  = findViewById(R.id.option4);
        next = findViewById(R.id.next);

        //String str = getIntent().getStringExtra("Category");
        list = new ArrayList<>();
        wordsDB = FirebaseDatabase.getInstance().getReference("Word");
        wordsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxId = snapshot.getChildrenCount();
                list.clear();
                for(DataSnapshot wordSnap: snapshot.getChildren()){
                    Word word = wordSnap.getValue(Word.class);
                    //if(word.getCategory().equals(str)){   // Don't Filter in this Case.
                        list.add(word);
                    //}
                }
                playGame(list);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cnt <= 10)
                            playGame(list);
                        if(cnt > 10)
                            Toast.makeText(QuizActivity.this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                        cnt++;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void nextQuestion(){

    }
    public void playGame(List<Word> list){
        int size = list.size();
        Random rand = new Random();
        int idx = rand.nextInt(size);
        Word elem = list.get(idx);
        option1.setText("verb");
        option2.setText("adverb");
        option3.setText("adjective");
        option4.setText("idiom");
        question.setText("Which part of speech does the word " + elem.getWord() + "\n" + "belongs to?");
        final boolean[] clicked = {false};
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elem.getCategory().equals("verb"))
                    clicked[0] = true;
                if(clicked[0] == true)
                    Toast.makeText(QuizActivity.this, "Correct:)", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(QuizActivity.this, "Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elem.getCategory().equals("adverb"))
                    clicked[0] = true;
                if(clicked[0] == true)
                    Toast.makeText(QuizActivity.this, "Correct:)", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(QuizActivity.this, "Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elem.getCategory().equals("adjective"))
                    clicked[0] = true;
                if(clicked[0] == true)
                    Toast.makeText(QuizActivity.this, "Correct:)", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(QuizActivity.this, "Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elem.getCategory().equals("idiom"))
                    clicked[0] = true;

                if(clicked[0] == true)
                    Toast.makeText(QuizActivity.this, "Correct :)", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(QuizActivity.this, "Wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}