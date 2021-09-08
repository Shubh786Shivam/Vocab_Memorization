package com.example.vocabapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adjectives extends AppCompatActivity {
    List<Word> list;
    ListView adjListView;
    DatabaseReference wordsDB;
    Button addWord;
    Word word;
    long maxId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjectives);
        String str = getIntent().getStringExtra("Category");
        word = new Word();
        addWord = findViewById(R.id.addWord);
        adjListView  = (ListView)findViewById(R.id.adjListView);
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
                    if(word.getCategory().equals(str)){
                        list.add(word);
                    }
                }
                CustomAdapter adapter = new CustomAdapter(Adjectives.this, (ArrayList<Word>) list);
                adjListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Adjectives.this, AddWord.class);
                startActivity(intent);
            }
        });
        adjListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Adjectives.this);
                builder.setMessage("Add Word to User's List?");
                builder.setTitle("Alert!");
                builder.setIcon(R.drawable.ic_alert);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryStr = "user";
                        String wordStr = list.get(position).getWord();
                        String explanationStr = list.get(position).getExplanation();
                        word.setWord(wordStr);
                        word.setExplanation(explanationStr);
                        word.setCategory(categoryStr);
                        wordsDB.child(String.valueOf(maxId+1)).setValue(word);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

}