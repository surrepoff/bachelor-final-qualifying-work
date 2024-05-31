package com.bessonov.musicappclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.SessionManager
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.ResponseDTO
import com.bessonov.musicappclient.dto.UserLoginDTO
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.ui.start.StartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var loginButton: Button
    private lateinit var backButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sessionManager = SessionManager(this)

        loginButton = findViewById(R.id.loginButton)
        backButton = findViewById(R.id.backButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Username and password must not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, StartActivity::class.java))
        }
    }

    private fun loginUser(username: String, password: String) {
        val retrofitClient = RetrofitClient()
        val userAPI = retrofitClient.getRetrofit(this).create(UserAPI::class.java)

        userAPI.login(UserLoginDTO(username, password)).enqueue(object : Callback<ResponseDTO> {
            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                Log.e("Login", "Failed to login (onFailure)", t)
                Toast.makeText(this@LoginActivity, "Failed to login (onFailure)", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                val loginResponse = response.body()
                val errorResponse = response.errorBody()?.string()

                if (response.isSuccessful) {
                    if (loginResponse != null && loginResponse.status_code == 200) {
                        Log.d("Login", "Response $loginResponse")
                        sessionManager.saveAuthToken(loginResponse.message)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    else {
                        Log.e("Login", "Failed to login : ${loginResponse?.message}")
                        Toast.makeText(this@LoginActivity, "Failed to login : ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("Login", "Failed to login (onResponse) : $errorResponse")
                    Toast.makeText(this@LoginActivity, "Failed to login (onResponse): $errorResponse", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}