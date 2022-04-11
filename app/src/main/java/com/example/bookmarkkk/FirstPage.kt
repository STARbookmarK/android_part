package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.FirstPageBinding

class FirstPage : Fragment(){
    private lateinit var binding: FirstPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FirstPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.main_to_login_action)
        }
        binding.joinBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.main_to_join_action)
        }
    }
}