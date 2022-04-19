package ru.example.andoid_app_news.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.ActivityProfileBinding
import ru.example.andoid_app_news.model.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private var newsBinding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        newsBinding?.profileViewModel = profileViewModel
        newsBinding?.context = this
        newsBinding?.lifecycleOwner = this
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        newsBinding?.arrowBackProfile?.setOnClickListener {
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

        profileViewModel.isProfile.postValue(!profileViewModel.isProfile.value!!)
    }
}