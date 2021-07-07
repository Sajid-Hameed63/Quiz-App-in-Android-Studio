package com.example.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.models.Question;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    Context context;
    Question question;
    String[] options;

    public OptionAdapter(Context context, Question question) {
        this.context = context;
        this.question = question;
        options = new String[] {question.option1,question.option2,question.option3,question.option4};
    }

    class OptionViewHolder extends RecyclerView.ViewHolder{
        TextView optionView;
        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionView = itemView.findViewById(R.id.quiz_option);
        }

    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionAdapter.OptionViewHolder holder, int position) {
        holder.optionView.setText(options[position]);
        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(context, options[position], Toast.LENGTH_SHORT).show();
            question.userAnswer = options[position];
            notifyDataSetChanged();
        });

        if(question.userAnswer == options[position]){
//            Toast.makeText(context, "Show Red Border", Toast.LENGTH_SHORT).show();
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg);
        }
        else{
//            Toast.makeText(context, "Show Simple Border", Toast.LENGTH_SHORT).show();
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return options.length;
    }
}
