package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.LoginBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPage : Fragment(R.layout.login), OnClickListener { //로그인 페이지

    private val binding by viewBinding(LoginBinding::bind)
    private val infoSaveModule : DataStoreModule by inject()
    private lateinit var id : String
    private lateinit var pw : String
    private var autoLogin : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> {
                login()
            }
        }
    }

    private fun login() {
        id = binding.idEditText.text.toString()
        pw = binding.pwEditText.text.toString()
        autoLogin = binding.loginCheckBox.isChecked
        val data = LoginData(id, pw, autoLogin)

        NetworkClient.authenticationService.login(data)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>){
                    if (response.isSuccessful){ // 로그인 성공
                        StyleableToast.makeText(requireContext(), "login", R.style.loginToast).show()
                        CoroutineScope(Dispatchers.IO).launch {
                            infoSaveModule.setPassword(data.user_pw)
                        }
                        Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action) // MainActivity 이동
                    }else{
                        StyleableToast.makeText(requireContext(), "아이디 또는 비밀번호가 잘못되었습니다", R.style.errorToast).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }

    override fun onStart() {
        super.onStart()
        //다음 액티비티 진입 시 확인해야 할 것들(ex 개인정보, 리스트 or 그리드, 카테고리화 유무)
    }

    override fun onStop() {
        super.onStop()
        // datastore 작업은 이미 완료
//        job?.cancel()
//        if(job?.isCompleted == true){
//            Log.e(TAG, "job is completed") -> ok
//        }else {
//            Log.e(TAG, "job is not completed")
//        }
    }

    companion object{
        const val TAG = "LoginPage"
    }
}