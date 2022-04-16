package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.TagPageBinding

class TagPage : Fragment() {
    private lateinit var binding: TagPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= TagPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.state,
                R.layout.state_spinner_style)
                .also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.stateSpinner.adapter=it
                }
        }
    }
}