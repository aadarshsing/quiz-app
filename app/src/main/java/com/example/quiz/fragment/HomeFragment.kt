package com.example.quiz.fragment


import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quiz.R
import com.example.quiz.adapters.quizAdapter
import com.example.quiz.databinding.FragmentHomeBinding
import com.example.quiz.models.quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: quizAdapter
    lateinit var firestore: FirebaseFirestore
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var quizList= mutableListOf<quiz>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        binding.topAppBar.setNavigationOnClickListener {
           binding.mainDrawer.open()
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            when(menuItem.itemId){
                R.id.btnProfile->{
                    menuItem.isChecked = true
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    binding.mainDrawer.close()
                }

            }
            binding.mainDrawer.close()
            true
        }

        getData()
        setUpRecyclerView()
        binding.floatingActionButton.setOnClickListener {
            val datepicker: MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker().build()
            datepicker.show(childFragmentManager,"datePicker")
            datepicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datepicker.headerText)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val date:String = dateFormat.format(Date(it))
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToQuestionsFragment(date))

            }
            datepicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER",datepicker.headerText)
            }
            datepicker.addOnCancelListener{
                Log.d("DATEPICKER","datepicker cancelled")
            }

        }
    }


    private fun getData(){
        val collectionReference:CollectionReference =firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if(value==null || error!=null){
                Toast.makeText(context,"Error in fetching data ",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            quizList.clear()
            quizList.addAll(value.toObjects(quiz::class.java))
            adapter.notifyDataSetChanged()

        }
    }

    private fun setUpRecyclerView() {
        adapter  = quizAdapter(requireContext(),quizList)
        binding.quizRecyler.layoutManager = GridLayoutManager(requireContext(),2)
        binding.quizRecyler.adapter = adapter
    }


}