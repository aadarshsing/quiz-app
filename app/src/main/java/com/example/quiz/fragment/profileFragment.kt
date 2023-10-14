package com.example.quiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth  = FirebaseAuth.getInstance()
        binding.txtEmail.text = firebaseAuth.currentUser?.email
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}