package com.bessonov.musicappclient.ui.register

import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.SessionManager
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.ResponseDTO
import com.bessonov.musicappclient.dto.UserLoginDTO
import com.bessonov.musicappclient.dto.UserRegisterDTO
import com.bessonov.musicappclient.ui.login.LoginActivity
import com.bessonov.musicappclient.ui.main.MainActivity
import com.bessonov.musicappclient.ui.start.StartActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var registerButton: Button
    private lateinit var backButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerButton = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val repeatPassword = repeatPasswordEditText.text.toString()

            if (username.isNotBlank() && email.isNotBlank() &&
                password.isNotBlank() && repeatPassword.isNotBlank()
            ) {
                if (password == repeatPassword) {
                    registerUser(username, email, password)
                } else {
                    Toast.makeText(this, "Passwords not equal", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields must not be empty", Toast.LENGTH_SHORT).show()
            }

        }

        backButton.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, StartActivity::class.java))
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        val retrofitClient = RetrofitClient()
        val userAPI = retrofitClient.getRetrofit(this).create(UserAPI::class.java)

        userAPI.register(UserRegisterDTO(username, email, password)).enqueue(object : Callback<ResponseDTO> {
            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                Log.e("Register", "Failed to register (onFailure)", t)
                Toast.makeText(this@RegisterActivity, "Failed to register (onFailure)", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                val registerResponse = response.body()
                val errorResponse = response.errorBody()?.string()

                if (response.isSuccessful) {
                    if (registerResponse != null && registerResponse.status_code == 200) {
                        Log.d("Register", "Response $registerResponse")
                        sessionManager.saveAuthToken(registerResponse.toString())
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                    else {
                        Log.e("Register", "Failed to register : ${registerResponse?.message}")
                        Toast.makeText(this@RegisterActivity, "Failed to register : ${registerResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("Register", "Failed to register (onResponse) : $errorResponse")
                    Toast.makeText(this@RegisterActivity, "Failed to register (onResponse): $errorResponse", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}