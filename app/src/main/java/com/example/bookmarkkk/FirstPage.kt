package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class FirstPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_page, container, false)
        view.findViewById<Button>(R.id.fBtn).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.first_to_second_action)
        }
        return view
    }
}