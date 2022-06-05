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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.bookmarkkk.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.github.muddz.styleabletoast.StyleableToast
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
    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    private val infoSaveModule : DataStoreModule by inject()
    private var viewType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBottomBar()

        binding.logoutBtn.setOnClickListener(this)
    }

    private fun runBottomBar(){
        binding.bottomBar.selectTabAt(0)
        changeFragment(MainPage())
        binding.bottomBar.onTabSelected={
            when(it.id){
                R.id.bookmarkShowBtn->{
                    if (viewType == 1){
                        changeFragment(MainPage())
                    }else{
                        changeFragment(MainCategorizedPage())
                    }
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
        NetworkClient.authenticationService.logout()
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        //Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, FirstActivity::class.java) // 로그아웃 시 초기화면으로 이동
                        startActivity(intent)
                        finishAffinity() // 쌓였던 모든 프래그먼트 스택 삭제
                        this@MainActivity.let { StyleableToast.makeText(it, "logout", R.style.logoutToast).show() }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    override fun onStart() {
        super.onStart()
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        response.body()?.let {
                            if (it.hashtagCategory==1){ // 카테고리화 비활성화
                                viewType = 1
                                Log.e(TAG, viewType.toString())
                                changeFragment(MainPage())
                            }else{
                                viewType = 0
                                Log.e(TAG, viewType.toString())
                                changeFragment(MainCategorizedPage())
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
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

    companion object{
        const val TAG = "MainActivity"
    }
}