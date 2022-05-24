package com.example.bookmarkkk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.bookmarkkk.databinding.LoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPage : Fragment(), View.OnClickListener { //로그인 페이지
    private lateinit var binding : LoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val coroutineScope by lazy{ CoroutineScope(Dispatchers.IO)}
    private lateinit var user_id : String
    private lateinit var user_pw : String
    private var autoLogin : Boolean = false
    private val infoSaveModule : DataStoreModule by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= LoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        //다음 액티비티 진입 시 확인해야 할 것들(ex 개인정보, 카테고리화 유무)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginBtn -> {
                login()
            }
        }
    }

    private fun login(){
        user_id = binding.idEditText.text.toString()
        user_pw = binding.pwEditText.text.toString()
        autoLogin = binding.loginCheckBox.isChecked

        //서버 통신 부분은 나중에 repository에 분리
        NetworkClient.loginService.login(LoginData(user_id = user_id, user_pw = user_pw, autoLogin = autoLogin))
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.message())
                    }else{
                        Log.i(TAG, response.headers().toString())
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action)
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }
    companion object{
        const val TAG = "LoginPage"
    }
}