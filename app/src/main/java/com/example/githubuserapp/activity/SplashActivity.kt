package com.example.githubuserapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val THREE_SECOND = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            FLAG_FULLSCREEN,
            FLAG_FULLSCREEN
        )
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, THREE_SECOND)
    }
}