package com.bessonov.musicappclient.ui.start

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.SessionManager
import com.bessonov.musicappclient.ui.login.LoginActivity
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.ui.register.RegisterActivity

class StartActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sessionManager = SessionManager(this)
        sessionManager.clearAuthToken()

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            startActivity(Intent(this@StartActivity, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this@StartActivity, RegisterActivity::class.java))
        }
    }
}