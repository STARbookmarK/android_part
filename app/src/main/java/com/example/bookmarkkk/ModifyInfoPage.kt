package com.example.bookmarkkk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.app.ActivityCompat.requireViewById
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
import java.math.BigInteger
import kotlin.math.log

class ModifyInfoPage : Fragment(), View.OnClickListener {
    private lateinit var binding: ModifyInfoBinding
    private lateinit var originPw : String
    private val infoSaveModule : DataStoreModule by inject()
    private val coroutineScope by lazy{ CoroutineScope(Dispatchers.IO) }
    private var bookmarkShow = 0
    private var hashtagShow = 0
    private var hashtagCategory = 0

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
        binding.viewSettingOkBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.viewSettingOkBtn -> { // 보기방식 변경
                bookmarkShow = if (binding.listCheck.isChecked) 1 else 0
                hashtagShow = if (binding.invisibleCheck.isChecked) 1 else 0
                hashtagCategory = if (binding.inActiveCheck.isChecked) 1 else 0
                changeViewType(bookmarkShow, hashtagShow, hashtagCategory)
            }
            R.id.infoModifyOkBtn -> { // 사용자 정보 수정
                changeBio(binding.msgEditText.text.toString())
            }
            R.id.pwChangeOkBtn -> { // 비밀번호 변경
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

    private fun changeBio(info: String) { // 소개글 변경
        NetworkClient.userInfoService.changeBio(BioOfUserInfo(info))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        context?.let { StyleableToast.makeText(it, "소개글 변경", R.style.bioToast).show() }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(LoginPage.TAG, t.toString())
                }
            })
    }

    private fun changePassword(pw: String, newPw: String){ // 비밀번호 변경
        NetworkClient.userInfoService.changePassword(Password(pw, newPw))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        logout()
                        context?.let { StyleableToast.makeText(it, "비밀번호 변경, 다시 로그인해주세요", R.style.passwordToast).show() }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    private fun changeViewType(bookmark: Int, hashtag: Int, category: Int){
        NetworkClient.userInfoService.changeViewType(BookmarkViewInfo(bookmark, hashtag, category))
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        context?.let { StyleableToast.makeText(it, "보기방식 변경", R.style.bioToast).show() }
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
        // 아이디, 닉네임, 소개글, 보기방식
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        response.body()?.let {
                            binding.userId.text = it.id
                            binding.nickName.text = it.nickname
                            binding.msgEditText.setText(it.info)
                            if (it.bookmarkShow==1){
                                binding.radioGroup.check(R.id.listCheck)
                            }else{
                                binding.radioGroup.check(R.id.gridCheck)
                            }
                            if (it.hashtagShow==1){
                                binding.radioGroup2.check(R.id.invisibleCheck)
                            }else{
                                binding.radioGroup2.check(R.id.visibleCheck)
                            }
                            if (it.hashtagCategory==1){
                                binding.radioGroup3.check(R.id.inActiveCheck)
                            }else{
                                binding.radioGroup3.check(R.id.activeCheck)
                            }
                        }
                        coroutineScope.launch {
                            originPw = infoSaveModule.password.first() // 비밀번호 변경에 사용됨
                        }
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable){
                    Log.e(TAG, t.toString())
                }
            })
    }
    companion object{
        const val TAG = "ModifyInfoPage"
    }

}