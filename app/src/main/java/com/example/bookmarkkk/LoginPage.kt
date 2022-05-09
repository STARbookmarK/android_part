package com.example.bookmarkkk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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


class LoginPage : Fragment(), View.OnClickListener {
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
        binding.googleLoginBtn.setOnClickListener(this)
        binding.loginBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        //다음 액티비티 진입 시 확인해야 할 것들(ex 개인정보, 카테고리화 유무)
        //일반 로그인과 구글 로그인 구분할 것!!
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.googleLoginBtn -> {
                googleSignIn() //구글 로그인
            }
            R.id.loginBtn -> {
                login() //일반 로그인
            }
        }
    }

    private fun googleSignIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) { // 로그인 성공시
            val data: Intent? = it.data
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            coroutineScope.launch {
                //App.getInstance().getDataStore().setLoginType(2)
                infoSaveModule.setLoginType(2)
                //App.getInstance().getDataStore().setEmail(email) // 사용자 이메일 저장
                infoSaveModule.setEmail(email)
            }
        }catch (e: ApiException){
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun login(){
        user_id = binding.idEditText.text.toString()
        user_pw = binding.pwEditText.text.toString()
        autoLogin = binding.loginCheckBox.isChecked
        Log.e("autoLogin", autoLogin.toString())
        //쿠키 ??? 바디 ???

        //Log.e(TAG, user_id + user_pw)
        //서버 통신 부분은 나중에 repository에 분리
        NetworkClient.loginService.login(user_id, user_pw, autoLogin)
            .enqueue(object: Callback<LoginData> {
                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>){
                    Log.e(TAG, response.toString())
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                        return
                    }else{
                        response.body()?.let{
                            //응답받은 데이터가 null이 아니면 로그인 성공
                            Log.e(TAG, "로그인 성공")
                            coroutineScope.launch {
                                infoSaveModule.setLoginType(1)
                                infoSaveModule.setEmail(user_id)
                            }
                            Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action)
                        }
                    }
                }
                override fun onFailure(call: Call<LoginData>, t: Throwable){
                    Log.e(TAG, "연결 실패")
                    Log.e(TAG, t.toString())
                }
            })
    }

    companion object{
        const val TAG = "LoginPage"
    }
}