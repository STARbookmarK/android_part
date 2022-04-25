package com.example.bookmarkkk

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
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
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch


class LoginPage : Fragment(), View.OnClickListener {
    private lateinit var binding : LoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

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
    }

    override fun onStart() {
        super.onStart()
        //다음 액티비티 진입 시 확인해야 할 것들(ex 개인정보, 카테고리화 유무)
        val account = context?.let { GoogleSignIn.getLastSignedInAccount(it) }
        val email = account?.email

        if (account!=null){
            CoroutineScope(Dispatchers.Main).launch {
                if (email != null) {
                    App.getInstance().getDataStore().setEmail(email)
                }
            }
            Toast.makeText(context,"자동 로그인 중...",Toast.LENGTH_SHORT).show()
            Navigation.findNavController(binding.root).navigate(R.id.login_to_main_action)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.googleLoginBtn -> {
                signIn()
            }
        }
    }

    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == 1) {
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()

            Log.d("account", email)
            Log.d("familyName", familyName)
        }catch (e: ApiException){
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }

    }
}