package com.example.quiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quiz.R
import com.example.quiz.databinding.FragmentLoginIntroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class loginIntro : Fragment() {
    private lateinit var binding: FragmentLoginIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth:FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            Snackbar.make(binding.root,"user already logged in", Snackbar.LENGTH_SHORT).show()
            redirect("MAIN")
        }
        binding.button.setOnClickListener{
            redirect("LOGIN")

        }
    }

    private fun redirect(name: String) {
        when(name){
            "LOGIN" -> findNavController().navigate(R.id.action_loginIntro_to_loginFragment)
            "MAIN" -> findNavController().navigate(R.id.action_loginIntro_to_homeFragment)
            else-> throw java.lang.Exception("no path exists")
        }
    }

}