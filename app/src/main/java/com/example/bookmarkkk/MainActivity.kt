package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.bookmarkkk.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var userLoginType : Int = 0
    private val infoSaveModule : DataStoreModule by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBottomBar()

        Log.i(TAG, userLoginType.toString())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        mGoogleSignInClient = this.let { GoogleSignIn.getClient(it, gso) }

        binding.logoutBtn.setOnClickListener(this)
        //getToken()
    }

//    private fun getToken(){
//        NetworkClient.tokenService.getTokens()
//            .enqueue(object : Callback<TokenData> {
//                override fun onResponse(call: Call<TokenData>, response: Response<TokenData>) {
//                    if (response.isSuccessful.not()){
//                        Log.e(TAG, "조회 실패")
//                        Log.e(TAG, response.message())
//                    }else{
//                        response.body()?.let {
//                            Log.e(TAG, it.user_id)
//                        }
//                    }
//                }
//                override fun onFailure(call: Call<TokenData>, t: Throwable) {
//                    Log.e(TAG, "연결 실패")
//                    Log.e(TAG, t.toString())
//                }
//            })
//    }

    private fun runBottomBar(){
        binding.bottomBar.selectTabAt(0)
        changeFragment(MainPage())
        binding.bottomBar.onTabSelected={
            when(it.id){
                R.id.bookmarkShowBtn->{
                    changeFragment(MainPage())
                }
                R.id.tagShowBtn->{
                    changeFragment(TagPage())
                }
                R.id.myInfoBtn->{
                    changeFragment(MyInfoPage())
                }
            }
        }
    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.secondContainer,fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.logoutBtn -> {
               if (userLoginType==1)
                   Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
               if (userLoginType==2)
                   googleLogout()
           }
       }
    }

    //구글 로그아웃
    private fun googleLogout(){
        CoroutineScope(Dispatchers.Main).launch {
//            App.getInstance().getDataStore().setEmail("null")
//            App.getInstance().getDataStore().setLoginType(0)
            infoSaveModule.setEmail("null")
            infoSaveModule.setLoginType(0)
        }
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                MotionToast.createColorToast(
                    this,
                    "Google Logout",
                    "로그아웃 되었습니다",
                    MotionToastStyle.INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
                this.finish()
                Log.i(TAG, "google logout")
            }
    }

    //일반 로그아웃
    private fun logout(){
        //
    }

    override fun onBackPressed() { //뒤로가기 버튼 클릭 시 종료
        val count=supportFragmentManager.backStackEntryCount
        if (count==0){
            super.onBackPressed()
        }else{
            finishAffinity()
        }
    }

    override fun onStart() {
        super.onStart()
            lifecycleScope.launch {
                //val loginType = App.getInstance().getDataStore().loginType.first()
                val loginType = infoSaveModule.loginType.first()
                userLoginType = loginType
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }

}