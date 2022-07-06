package com.example.bookmarkkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookmarkkk.databinding.ActivityMainBinding
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(R.layout.activity_main), OnClickListener {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private var categoryType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBottomBar()

        binding.logoutBtn.setOnClickListener(this)
    }

    private fun runBottomBar(){ // 하단바
        binding.bottomBar.selectTabAt(0)
        //changeFragment(MainPage())
        binding.bottomBar.onTabSelected={
            when(it.id){
                R.id.bookmarkShowBtn->{
                    if (categoryType == 1){ // 카테고리화 타입에 따라 화면 이동
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
                    if (response.isSuccessful){ // 로그아웃 성공(일단 토스트 먼저 띄우기)
                        StyleableToast.makeText(this@MainActivity, "로그아웃", R.style.logoutToast).show()
                        val intent = Intent(this@MainActivity, FirstActivity::class.java) // 로그아웃 시 초기화면으로 이동
                        startActivity(intent)
                        finishAffinity() // 쌓였던 모든 프래그먼트 스택 삭제
                    }else {
                        StyleableToast.makeText(this@MainActivity, "로그아웃 실패", R.style.errorToast).show()
                        Log.e(TAG, response.toString())
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    override fun onStart() {
        super.onStart()

        // livedata 사용하기에 적합하지 않음
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        response.body()?.let {
                            //카테고리화 활성화 유무
                            if (it.hashtagCategory==1){ // 카테고리화 비활성화
                                categoryType = 1
                                changeFragment(MainPage())
                            }else{ // 카테고리화 활성화
                                categoryType = 0
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