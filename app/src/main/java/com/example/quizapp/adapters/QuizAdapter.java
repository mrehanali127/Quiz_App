package com.example.quizapp.adapters;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.MainActivity;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utils.ColorPicker;
import com.example.quizapp.utils.IconPicker;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder>{
    private int template;
    private ArrayList<Quiz> quizzes;

    public QuizAdapter(int template, ArrayList<Quiz> quizzes) {
        this.template = template;
        this.quizzes = quizzes;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(template,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Quiz quiz=quizzes.get(position);
        holder.title.setText(quiz.title);
        holder.card.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()));
        holder.image.setImageResource(IconPicker.getIcon());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Rehan", quizzes.get(position).title);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        CardView card;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.quizTitle);
            image=itemView.findViewById(R.id.quizIcon);
            card=itemView.findViewById(R.id.Cardcontainer);
            view=itemView;

        }
    }

}
