package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.LoginBinding
import com.example.bookmarkkk.databinding.MyinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MyInfoPage : Fragment(R.layout.myinfo) {

    private val binding by viewBinding(MyinfoBinding::bind)
    private lateinit var spinner: Spinner
    private val viewModel : ViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userData.observe(viewLifecycleOwner, Observer {
            binding.nickNameText.text = it.nickname
            binding.introText.text = it.info
        })

        context?.let {
            spinner = Spinner(it)
            binding.rankSpinner.adapter = spinner.rankSpinnerSet()
        }

        binding.modifyBtn.setOnClickListener {
            val activity = activity as MainActivity
            activity.changeFragment(ModifyInfoPage())
        }
    }

    override fun onStart() {
        super.onStart()
    }
}