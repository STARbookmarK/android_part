package com.example.bookmarkkk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgument
import com.example.bookmarkkk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBottomBar()
        //주석 추가

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

    companion object{
        const val TAG = "MainActivity"
    }


}