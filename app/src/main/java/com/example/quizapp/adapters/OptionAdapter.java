package com.example.quizapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.QuestionActivity;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;

import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder> {
    private int template;
    private Question question;
    List<String> options=new ArrayList<String>();



    public OptionAdapter(int template, Question question) {
        this.template = template;
        this.question= question;
        options.add(question.option1);
        options.add(question.option2);
        options.add(question.option3);
        options.add(question.option4);

    }

    @Override
    public OptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(template,parent,false);
        OptionHolder holder=new OptionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OptionHolder holder, int position) {

        holder.option.setText(options.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Rehan", options.get(position));
                question.useranswer=options.get(position);
                notifyDataSetChanged();
            }
        });
        if(question.useranswer==options.get(position)){
           holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg);
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        //Log.i("Rehan", options.size()+"");
        return options.size();

    }


    // Class for  View Holder
    public static class OptionHolder extends RecyclerView.ViewHolder{
        TextView option;

        public OptionHolder(View itemView) {
            super(itemView);
            option=itemView.findViewById(R.id.quizOption);
        }
    }
}
