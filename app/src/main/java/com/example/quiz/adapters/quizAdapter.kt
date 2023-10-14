package com.example.quiz.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.databinding.QuizItemBinding
import com.example.quiz.models.quiz
import com.example.quiz.utils.ColorPicker
import com.example.quiz.utils.IconPicker
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.quiz.fragment.HomeFragment
import com.example.quiz.fragment.HomeFragmentDirections

class quizAdapter(val context: Context, val quizes:List<quiz>):
    RecyclerView.Adapter<quizAdapter.ViewHolder>() {
    class ViewHolder(private var binding: QuizItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(quiz: quiz){
            binding.quizTittle.text=quiz.title
            binding.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
            binding.quizIcon.setImageResource(IconPicker.getIcon())
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizz = quizes[position]
        holder.bind(quizz)
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToQuestionsFragment(title = quizz.title)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return quizes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuizItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

}

