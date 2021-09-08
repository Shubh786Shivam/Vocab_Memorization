package com.example.vocabapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddWord extends AppCompatActivity {
    EditText Word, explanation, category;
    Button Add;
    DatabaseReference reff;
    Word word;
    long maxId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Word = findViewById(R.id.Word);
        explanation = findViewById(R.id.explanation);
        category = findViewById(R.id.category);
        Add = findViewById(R.id.Add);
        word = new Word();
        reff = FirebaseDatabase.getInstance().getReference().child("Word");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxId = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryStr = category.getText().toString().trim();
                String wordStr = Word.getText().toString().trim();
                String explanationStr = explanation.getText().toString().trim();
                word.setWord(wordStr);
                word.setExplanation(explanationStr);
                word.setCategory(categoryStr);
                reff.child(String.valueOf(maxId+1)).setValue(word);

            }
        });
    }
}