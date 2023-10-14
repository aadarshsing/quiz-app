package com.example.quiz.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.quiz.R
import com.example.quiz.databinding.FragmentResultBinding
import com.example.quiz.models.quiz
import com.google.gson.Gson

class ResultFragment : Fragment() {
    private val navigationArgs: ResultFragmentArgs by navArgs()
    private lateinit var binding: FragmentResultBinding
    lateinit var quizData: quiz

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = navigationArgs.result
        quizData =Gson().fromJson<quiz>(result,quiz::class.java)
        calculateScore()
        setAnswer()


    }

    private fun setAnswer() {
        val builder = StringBuilder("")
        for (entry in quizData.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            binding.txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score = 0
        for(entry in quizData.questions.entries){
            val questions = entry.value
            if(questions.userAnswer==questions.answer){
                score += 10
            }
        }
        binding.txtScore.text="your score is ${score}"
    }
}