package com.example.bookmarkkk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
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

class ModifyInfoPage : Fragment(R.layout.modify_info), OnClickListener {

    private val binding by viewBinding(ModifyInfoBinding::bind)
    private lateinit var originPw : String
    private val infoSaveModule : DataStoreModule by inject()
    private val viewModel : ViewModel by inject()
    private val coroutineScope by lazy{ CoroutineScope(Dispatchers.IO) }
    private var bookmarkShow = 0
    private var hashtagShow = 0
    private var hashtagCategory = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.userId.text = user.id
                binding.nickName.text = user.nickname
                binding.msgEditText.setText(user.info)
            }
        })

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
                val data = BookmarkViewInfo(bookmarkShow, hashtagShow, hashtagCategory)
                changeViewType(data)
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
                        val data = Password(oldPassword, newPassword)
                        changePassword(data)
                    }
                }else{
                    Toast.makeText(context, "기존 비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun changeBio(info: String) { // 소개글 변경
        viewModel.changeBio(info)
    }

    private fun changePassword(data: Password){ // 비밀번호 변경
        NetworkClient.userInfoService.changePassword(data)
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        logout()
                        StyleableToast.makeText(requireContext(), "비밀번호 변경, 다시 로그인해주세요", R.style.passwordToast).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
    }

    private fun changeViewType(data: BookmarkViewInfo){
        NetworkClient.userInfoService.changeViewType(data)
            .enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        StyleableToast.makeText(requireContext(), "보기방식 변경", R.style.bioToast).show()
                        val intent = Intent(context, MainActivity::class.java) // 메인화면으로 이동
                        startActivity(intent)
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
                    if (response.isSuccessful){ // 로그아웃 성공
                        StyleableToast.makeText(requireContext(), "로그아웃", R.style.logoutToast).show()
                        val intent = Intent(requireContext(), FirstActivity::class.java) // 로그아웃 시 초기화면으로 이동
                        startActivity(intent)
                    }else {
                        StyleableToast.makeText(requireContext(), "로그아웃 실패", R.style.errorToast).show()
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
        // 아이디, 닉네임, 소개글, 보기방식
        NetworkClient.userInfoService.getUserInfo()
            .enqueue(object: Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>){
                    if (response.isSuccessful.not()){
                        Log.e(TAG, response.toString())
                    }else{
                        response.body()?.let {
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