package com.example.bookmarkkk

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.MyinfoBinding
import org.koin.android.ext.android.inject

class MyInfoPage : Fragment(R.layout.myinfo) {

    private val binding by viewBinding(MyinfoBinding::bind)
    private lateinit var spinner: Spinner
    private val viewModel : ViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.nickNameText.text = user.nickname
                binding.introText.text = user.info
            }
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