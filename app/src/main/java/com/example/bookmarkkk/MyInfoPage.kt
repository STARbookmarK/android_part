package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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

class MyInfoPage : Fragment() {
    private lateinit var binding: MyinfoBinding
    private lateinit var spinner: Spinner
    private var loginType = 0
    private val infoSaveModule : DataStoreModule by inject()

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
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e("MyInfoPage", response.toString())
                        return
                    }else{
                        response.body()?.let {
                            binding.nickNameText.text = it.nickname
                            binding.introText.text = it.info
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }
}