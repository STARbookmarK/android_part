package com.example.bookmarkkk


import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.LoginBinding
import org.koin.android.ext.android.inject


class LoginPage : Fragment(R.layout.login), OnClickListener { //로그인 페이지

    private val binding by viewBinding(LoginBinding::bind)
    private lateinit var user_id : String
    private lateinit var user_pw : String
    private var autoLogin : Boolean = false
    private val viewModel : ViewModel by inject()

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
        user_id = binding.idEditText.text.toString()
        user_pw = binding.pwEditText.text.toString()
        autoLogin = binding.loginCheckBox.isChecked

        viewModel.login(LoginData(user_id, user_pw, autoLogin))

        viewModel.loginResultValue.observe(viewLifecycleOwner, Observer { value ->
            if (value==1){ // 로그인 성공
                Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action) // MainActivity 이동
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