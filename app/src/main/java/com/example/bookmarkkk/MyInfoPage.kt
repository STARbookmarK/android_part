package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.MyinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyInfoPage : Fragment() {
    private lateinit var binding : MyinfoBinding
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MyinfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val email = App.getInstance().getDataStore().email.first()
            binding.idText.text=email
        }

        context?.let {
            spinner= Spinner(it)
            binding.rankSpinner.adapter=spinner.rankSpinnerSet()
        }

        binding.modifyBtn.setOnClickListener{
            val activity = activity as MainActivity
            activity.changeFragment(ModifyInfoPage())
        }

    }

}