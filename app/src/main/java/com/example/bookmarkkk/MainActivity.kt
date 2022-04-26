package com.example.bookmarkkk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var userLoginType : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBottomBar()

        //일반 로그인과 구글 로그인을 구별하기 위해 DataStore에 변수 생성
        CoroutineScope(Dispatchers.Main).launch {
            val loginType = App.getInstance().getDataStore().loginType.first()
            userLoginType = loginType
            Log.i(TAG, userLoginType.toString())
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        mGoogleSignInClient = this.let { GoogleSignIn.getClient(it, gso) }

        binding.logoutBtn.setOnClickListener(this)
    }

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
               if (userLoginType==2)
                   googleLogout()
           }
       }
    }

    //구글 로그아웃
    private fun googleLogout(){
        CoroutineScope(Dispatchers.Main).launch {
            App.getInstance().getDataStore().setEmail("null")
            App.getInstance().getDataStore().setLoginType(0)
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

    companion object{
        const val TAG = "MainActivity"
    }
}