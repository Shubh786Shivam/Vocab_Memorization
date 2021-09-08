package com.example.vocabapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Word> wordsList;

    public CustomAdapter(Adjectives context, ArrayList<Word> wordsList) {
        this.context = context;
        this.wordsList = wordsList;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Word currentWord = (Word) getItem(position);
        viewHolder.word.setText(currentWord.getWord());
        viewHolder.explanation.setText(currentWord.getExplanation());

        return convertView;

    }
    private class ViewHolder {
        TextView word;
        TextView explanation;

        public ViewHolder(View view) {
            word = (TextView)view.findViewById(R.id.word);
            explanation = (TextView) view.findViewById(R.id.explanation);
        }
    }
}
