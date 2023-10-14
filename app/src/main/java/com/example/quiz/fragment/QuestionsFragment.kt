package com.example.quiz.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.adapters.optionAdapter
import com.example.quiz.databinding.FragmentQuestionsBinding
import com.example.quiz.models.questions
import com.example.quiz.models.quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class QuestionsFragment : Fragment() {
    private lateinit var optionAdapter: optionAdapter
    var quizes :MutableList<quiz>?=null
    var questions : MutableMap<String,questions>?=null
    var index = 1
    private lateinit var binding: FragmentQuestionsBinding

    private val navigationArgs: QuestionsFragmentArgs by navArgs()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val date = navigationArgs.title
        Log.d("date",date)
        setUpButton()
        if(date!=null){
            firebaseFirestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if(it!=null && !it.isEmpty) {
                        quizes=it.toObjects(quiz::class.java)
                        questions=quizes!![0].questions
                        bindViews()
                        Log.d("DATA", it.toObjects(quiz::class.java).toString())
                    }
                }
        }




    }

    private fun setUpButton() {
        binding.previousBtn.setOnClickListener {
            index--
            bindViews()
        }
        binding.NextBtn.setOnClickListener {
            index++
            bindViews()
        }
        binding.submitbtn.setOnClickListener {
            Log.d("FinalQuiz",questions.toString())
            val json = Gson().toJson(quizes!![0])
            val action =  QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment(json)
            findNavController().navigate(action)
        }
    }

    private fun bindViews() {
        binding.NextBtn.visibility = View.GONE
        binding.previousBtn.visibility = View.GONE
        binding.submitbtn.visibility = View.GONE
        if (index == 1) {
            binding.NextBtn.visibility = View.VISIBLE
        }
         else if(index==questions!!.size){
        binding.previousBtn.visibility= View.VISIBLE
        binding.submitbtn.visibility = View.VISIBLE
    }
        else {
            binding.NextBtn.visibility = View.VISIBLE
            binding.previousBtn.visibility = View.VISIBLE
        }
        val question = questions!!["question$index"]
        question.let {
            binding.description.text = it!!.description
            optionAdapter = optionAdapter(requireContext(), it)
            binding.optionList.layoutManager = LinearLayoutManager(requireContext())
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }

    }


}