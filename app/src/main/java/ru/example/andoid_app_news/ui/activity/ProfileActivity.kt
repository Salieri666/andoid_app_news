package ru.example.andoid_app_news.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.api.ProfileApiService
import ru.example.andoid_app_news.databinding.ActivityProfileBinding

import ru.example.andoid_app_news.model.data.AuthDto
import ru.example.andoid_app_news.ui.viewmodel.ProfileViewModel
import ru.example.andoid_app_news.ui.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(ProfileApiService.instance((application as MainApplication).retrofit), application)
    }
    private var binding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding?.profileViewModel = profileViewModel
        binding?.context = this
        binding?.lifecycleOwner = this
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        binding?.arrowBackProfile?.setOnClickListener {
            onBackPressed()
        }
        
        return super.onCreateView(parent, name, context, attrs)
    }


    fun arrowBackClicked(view: View) {
        onBackPressed()
    }

    fun changeLoginRegister(view: View) {
        profileViewModel.isLogin.postValue(!profileViewModel.isLogin.value!!)
        profileViewModel.isRegister.postValue(!profileViewModel.isRegister.value!!)
    }

    fun toProfile(view : View) {
        if (!profileViewModel.isProfile.value!!) {
            val login = binding?.loginField?.editText?.text?.toString()
            val pass = binding?.passField?.editText?.text?.toString()

            if (profileViewModel.isLogin.value!!) {
                profileViewModel.authUser(AuthDto(login, pass), false).observe(this) {
                    it?.let {
                        if (!it.isSuccess) {
                            val toast = Toast.makeText(applicationContext, getString(R.string.wrongCredentials), Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.CENTER,200,200)
                                toast.show()
                        }
                    }
                    profileViewModel.authUser(AuthDto(login, pass), false).removeObservers(this)
                }
            } else if (profileViewModel.isRegister.value!!) {
                profileViewModel.authUser(AuthDto(login, pass), true).observe(this) {
                    it?.let {
                        if (!it.isSuccess) {
                            val toast = Toast.makeText(applicationContext, getString(R.string.loginExists), Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER,200,200)
                            toast.show()
                        }
                    }
                    profileViewModel.authUser(AuthDto(login, pass), false).removeObservers(this)
                }
            }
        }

        if (profileViewModel.isProfile.value!!) {
            profileViewModel.unLogin()
        }
    }

}