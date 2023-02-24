package com.android.aman.newsapp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    private val splashtimeout: Long = 5000

    var backgroundImage: ImageView? = null
    var sideAnim: Animation? = null
    var bottomAnim: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        backgroundImage = findViewById(R.id.imageView);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageView.setAnimation(sideAnim)
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, splashtimeout)
    }
}

