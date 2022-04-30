package com.example.bookmarkkk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.MyinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyInfoPage : Fragment() {
    private lateinit var binding: MyinfoBinding
    private lateinit var spinner: Spinner
    private var loginType = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyinfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val email = App.getInstance().getDataStore().email.first()
            loginType = App.getInstance().getDataStore().loginType.first()
            binding.idText.text = email
        }

        context?.let {
            spinner = Spinner(it)
            binding.rankSpinner.adapter = spinner.rankSpinnerSet()
        }

        binding.modifyBtn.setOnClickListener {
            val activity = activity as MainActivity
            if (loginType == 1 || loginType == 0) { // 일반 로그인
                activity.changeFragment(ModifyInfoPage())
            } else { //구글 로그인
                activity.changeFragment(ModifyGoogleInfoPage())
            }
        }
    }
}