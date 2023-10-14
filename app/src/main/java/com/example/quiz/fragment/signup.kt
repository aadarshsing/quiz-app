package com.example.quiz.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.databinding.FragmentSignupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class signup : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(layoutInflater)
        var fragmentManager: FragmentManager? = getFragmentManager()
        var fragmentTransaction :FragmentTransaction= fragmentManager!!.beginTransaction()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnsignup.setOnClickListener {
            createUser()
        }
        binding.btnlogin.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_loginFragment)
            val fragmentManager: FragmentManager? = getFragmentManager()
           val fragmentTransaction :FragmentTransaction= fragmentManager!!.beginTransaction()
            fragmentTransaction.addToBackStack(null).commit()


        }

    }

    private fun createUser() {
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPass = binding.etConPassword.text.toString()
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty() ){
                if(password == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener {
                        Snackbar.make(binding.root,"user registered", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signup_to_loginFragment)



                    }.addOnFailureListener{
                        Snackbar.make(binding.root,"registration failed",Snackbar.LENGTH_SHORT).show()
                    }
                }
                else{
                    binding.etConPassword.setError("write the matching password")
                }
            }
            else{
                binding.etPassword.setError("password cannot be empty")
            }
        }
        else{
            binding.etEmailAddress.setError("please write correct email")
        }
    }



}