package com.example.bookmarkkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.example.bookmarkkk.databinding.ModifyInfoBinding
import io.github.muddz.styleabletoast.StyleableToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class ModifyInfoPage : Fragment(), View.OnClickListener {
    private lateinit var binding: ModifyInfoBinding
    private lateinit var originPw : String
    private val infoSaveModule : DataStoreModule by inject()
    private val coroutineScope by lazy{ CoroutineScope(Dispatchers.IO) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ModifyInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //서버랑 통신해서 닉네임 + 상태메세지 + 보기값 저장 및 가져오기
        //먼저 자동로그인이나 로그인 되면 서버랑 통신해서 사용자 정보값 다 가져온 다음에
        //뷰모델이나 DataStore에 저장해서 쓰기??
        //보기 방식은 굳이 서버와 통신하지 않아도 될 것 같음
        //getUserInfo()
        binding.viewSettingOkBtn.setOnClickListener(this)
        binding.infoModifyOkBtn.setOnClickListener(this)
        binding.pwChangeOkBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.viewSettingOkBtn -> {
                coroutineScope.launch {
                    if (binding.listCheck.isChecked){
                        infoSaveModule.setBookmarkType("1") //리스트형
                    }else{
                        infoSaveModule.setBookmarkType("0") //바둑형
                    }
                }
            }
            R.id.infoModifyOkBtn -> {
                changeBio(binding.msgEditText.text.toString())
            }
            R.id.pwChangeOkBtn -> {
                // '원래 비밀번호' 잘못 입력하면 변경 안됨
                val oldPassword = binding.oldPwEdit.text.toString()
                val newPassword = binding.newPwEdit.text.toString()
                val newPasswordCheck = binding.newPwCheckEdit.text.toString()
                if (oldPassword==originPw){
                    if(newPassword != newPasswordCheck){
                        Toast.makeText(context, "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        changePassword(oldPassword, newPassword)
                    }
                }else{
                    Toast.makeText(context, "기존 비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun changeBio(info: String) {
        NetworkClient.userInfoService.changeBio(BioOfUserInfo(info))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e("ModifyInfoPage", response.toString())
                    }else{
                        Toast.makeText(context, "소개글 변경", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }

    private fun changePassword(pw: String, newPw: String){
        NetworkClient.userInfoService.changePassword(Password(pw, newPw))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e("ModifyInfoPage", response.toString())
                    }else{
                        Toast.makeText(context, "비밀번호가 변경되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
                        logout()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    //로그아웃
    private fun logout(){
        NetworkClient.authenticationService.logout()
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(MainActivity.TAG, response.toString())
                    }else{
                        val intent = Intent(context, FirstActivity::class.java) // 로그아웃 시 초기화면으로 이동
                        startActivity(intent)
                        activity?.finishAffinity() // 쌓였던 모든 프래그먼트 스택 삭제
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(MainActivity.TAG, t.toString())
                }
            })
    }

    override fun onStart() {
        super.onStart()
        NetworkClient.authenticationService.autoLogin()
            .enqueue(object: Callback<UserId> {
                override fun onResponse(call: Call<UserId>, response: Response<UserId>){
                    if (response.isSuccessful.not()){
                        Log.e("ModifyInfoPage", response.toString())
                        return
                    }else{
                        response.body()?.let {
                            binding.userId.text=it.id
                        }
                    }
                }
                override fun onFailure(call: Call<UserId>, t: Throwable){
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e("MyInfoPage", response.toString())
                        return
                    }else{
                        response.body()?.let {
                            binding.nickEditText.setText(it.nickname)
                            binding.msgEditText.setText(it.info)
                        }
                        coroutineScope.launch {
                            originPw = infoSaveModule.password.first()
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }

    companion object{
        const val TAG = "ModifyInfoPage"
    }

}