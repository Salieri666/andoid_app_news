package ru.example.andoid_app_news.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.example.andoid_app_news.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {


    private var newsBinding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(newsBinding?.root)
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
}