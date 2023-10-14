package com.example.quiz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.OptionItemBinding
import com.example.quiz.models.questions
import com.example.quiz.R

class optionAdapter(val context: Context,val questions: questions) :RecyclerView.Adapter<optionAdapter.optionViewholder>(){
    private var options:List<String> = listOf(questions.option1,questions.option2,questions.option3,questions.option4)
    class optionViewholder (private val binding: OptionItemBinding):RecyclerView.ViewHolder(binding.root){
        var textview = binding.quizOption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): optionViewholder {
        return optionViewholder(OptionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: optionViewholder, position: Int) {
        holder.textview.text= options[position]
        holder.itemView.setOnClickListener{
            questions.userAnswer= options[position]
            notifyDataSetChanged()
        }
        if(questions.userAnswer==options[position]){
            holder.itemView.setBackgroundResource(R.drawable.item_selectedbg)
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}