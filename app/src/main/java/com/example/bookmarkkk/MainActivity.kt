package com.example.bookmarkkk

import android.content.Intent
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
    private var userLoginType : Int = 0
    //private val infoSaveModule : DataStoreModule by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBottomBar()

        Log.i(TAG, userLoginType.toString())

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
               logout()
           }
       }
    }

    //로그아웃
    private fun logout(){
        NetworkClient.logoutService.logout()
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, FirstActivity::class.java) // 로그아웃 시 초기화면으로 이동
                        startActivity(intent)
                        finishAffinity() // 쌓였던 모든 프래그먼트 스택 삭제
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
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
//            lifecycleScope.launch {
//                val loginType = App.getInstance().getDataStore().loginType.first()
//                val loginType = infoSaveModule.loginType.first()
//                userLoginType = loginType
//        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}