package com.example.quiz.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class loginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnlogin.setOnClickListener {
            loginUser()
        }
        binding.btnsignup.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_signup)

        }

    }

    private fun loginUser() {
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!password.isEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Snackbar.make(binding.root,"login successful", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                    }.addOnFailureListener {
                        Snackbar.make(binding.root,"incorrect email and password", Snackbar.LENGTH_SHORT).show()
                    }
            }
            else{
                binding.etPassword.setError("empty fields are not allowed")
            }
        }else if(email.isEmpty()){
            binding.etEmailAddress.setError("empty fields are not allowed")
        }
        else
        {
            binding.etEmailAddress.setError("please write correct username")
        }
    }



}