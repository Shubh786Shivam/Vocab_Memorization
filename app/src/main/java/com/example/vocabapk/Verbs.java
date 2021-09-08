package com.example.vocabapk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Verbs extends AppCompatActivity {
    List<Word> list;
    ListView adjListView;
    DatabaseReference wordsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjectives);
        adjListView  = (ListView)findViewById(R.id.adjListView);
        list = new ArrayList<>();
        //generateWordsList();

        wordsDB = FirebaseDatabase.getInstance().getReference("Word");
        wordsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot wordSnap: snapshot.getChildren()){
                    Word word = wordSnap.getValue(Word.class);
                    if(word.getCategory().equals("verb")){
                        list.add(word);
                    }
                }
                //CustomAdapter adapter = new CustomAdapter(Verbs.this, (ArrayList<Word>) list);
                //adjListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}