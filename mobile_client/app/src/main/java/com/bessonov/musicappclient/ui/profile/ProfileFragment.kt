package com.bessonov.musicappclient.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bessonov.musicappclient.R
import com.bessonov.musicappclient.api.RetrofitClient
import com.bessonov.musicappclient.api.SessionManager
import com.bessonov.musicappclient.api.UserAPI
import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.dto.UserEditRequestDTO
import com.bessonov.musicappclient.dto.UserEditResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var closeButton: ImageButton

    private lateinit var nicknameText: EditText
    private lateinit var nicknameEdit: ImageButton

    private lateinit var registrationText: TextView
    private lateinit var lastUpdateText: TextView

    private lateinit var loginText: EditText
    private lateinit var loginEdit: ImageButton

    private lateinit var emailText: EditText
    private lateinit var emailEdit: ImageButton

    private lateinit var newPasswordText: EditText
    private lateinit var repeatNewPasswordText: EditText
    private lateinit var newPasswordEdit: ImageButton

    private lateinit var passwordLabel: TextView
    private lateinit var passwordText: EditText

    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private lateinit var userDataDTO: UserDataDTO

    private var isUserInfoLoaded: Boolean = false

    private var isEdited: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        swipeRefreshLayout = view.findViewById(R.id.fragmentProfile_swipeRefreshLayout)

        closeButton = view.findViewById(R.id.fragmentProfile_closeButton)

        nicknameText = view.findViewById(R.id.fragmentProfile_nicknameText)
        nicknameText.isEnabled = false
        nicknameEdit = view.findViewById(R.id.fragmentProfile_nicknameEdit)

        registrationText = view.findViewById(R.id.fragmentProfile_registrationText)
        lastUpdateText = view.findViewById(R.id.fragmentProfile_lastUpdateText)

        loginText = view.findViewById(R.id.fragmentProfile_loginText)
        loginText.isEnabled = false
        loginEdit = view.findViewById(R.id.fragmentProfile_loginEdit)

        emailText = view.findViewById(R.id.fragmentProfile_emailText)
        emailText.isEnabled = false
        emailEdit = view.findViewById(R.id.fragmentProfile_emailEdit)

        newPasswordText = view.findViewById(R.id.fragmentProfile_newPasswordText)
        newPasswordText.isEnabled = false
        repeatNewPasswordText = view.findViewById(R.id.fragmentProfile_repeatNewPasswordText)
        repeatNewPasswordText.isEnabled = false
        newPasswordEdit = view.findViewById(R.id.fragmentProfile_newPasswordEdit)

        passwordLabel = view.findViewById(R.id.fragmentProfile_passwordLabel)
        passwordText = view.findViewById(R.id.fragmentProfile_passwordText)
        saveButton = view.findViewById(R.id.fragmentProfile_saveButton)
        cancelButton = view.findViewById(R.id.fragmentProfile_cancelButton)

        userDataDTO = UserDataDTO()

        isUserInfoLoaded = false

        checkIsEdited()

        loadData()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkIsEdited()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        closeButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        nicknameEdit.setOnClickListener {
            nicknameText.isEnabled = !nicknameText.isEnabled
        }

        nicknameText.addTextChangedListener(textWatcher)

        loginEdit.setOnClickListener {
            loginText.isEnabled = !loginText.isEnabled
        }

        loginText.addTextChangedListener(textWatcher)

        emailEdit.setOnClickListener {
            emailText.isEnabled = !emailText.isEnabled
        }

        emailText.addTextChangedListener(textWatcher)

        newPasswordEdit.setOnClickListener {
            newPasswordText.isEnabled = !newPasswordText.isEnabled
            repeatNewPasswordText.isEnabled = !repeatNewPasswordText.isEnabled
        }

        newPasswordText.addTextChangedListener(textWatcher)
        repeatNewPasswordText.addTextChangedListener(textWatcher)

        saveButton.setOnClickListener {
            saveDate();
        }

        cancelButton.setOnClickListener {
            populateData()
            newPasswordText.setText("")
            repeatNewPasswordText.setText("")
            checkIsEdited()
            nicknameText.isEnabled = false
            loginText.isEnabled = false
            emailText.isEnabled = false
            newPasswordText.isEnabled = false
            repeatNewPasswordText.isEnabled = false
        }

        return view
    }

    private fun checkIsEdited() {
        if (nicknameText.text.toString() != userDataDTO.nickname) {
            isEdited = true
            updateUI()
            return
        }

        if (loginText.text.toString() != userDataDTO.username) {
            isEdited = true
            updateUI()
            return
        }

        if (emailText.text.toString() != userDataDTO.email) {
            isEdited = true
            updateUI()
            return
        }

        if (newPasswordText.text.toString().isNotBlank()) {
            isEdited = true
            updateUI()
            return
        }

        if (repeatNewPasswordText.text.toString().isNotBlank()) {
            isEdited = true
            updateUI()
            return
        }

        isEdited = false
        updateUI()
        return
    }

    private fun updateUI() {
        if (isEdited) {
            passwordLabel.visibility = View.VISIBLE
            passwordText.visibility = View.VISIBLE
            passwordText.isEnabled = true
            saveButton.visibility = View.VISIBLE
            saveButton.isEnabled = true
            cancelButton.visibility = View.VISIBLE
            cancelButton.isEnabled = true
        } else {
            passwordLabel.visibility = View.INVISIBLE
            passwordText.visibility = View.INVISIBLE
            passwordText.isEnabled = false
            saveButton.visibility = View.INVISIBLE
            saveButton.isEnabled = false
            cancelButton.visibility = View.INVISIBLE
            cancelButton.isEnabled = false
        }
    }

    private fun checkIsInfoLoaded() {
        if (isUserInfoLoaded) {
            populateData()
            isEdited = false
            updateUI()
            isUserInfoLoaded = false
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun populateData() {
        nicknameText.setText(userDataDTO.nickname)
        registrationText.text = userDataDTO.registrationDate.toString()
        lastUpdateText.text = userDataDTO.lastUpdateDate.toString()
        loginText.setText(userDataDTO.username)
        emailText.setText(userDataDTO.email)
    }

    private fun loadData() {
        loadUser()
    }

    private fun loadUser() {
        val retrofitClient = RetrofitClient()
        val userAPI = retrofitClient.getRetrofit(requireContext()).create(UserAPI::class.java)

        userAPI.info().enqueue(object : Callback<UserDataDTO> {
            override fun onResponse(call: Call<UserDataDTO>, response: Response<UserDataDTO>) {
                if (response.isSuccessful && response.body() != null) {
                    userDataDTO = response.body()!!
                    isUserInfoLoaded = true
                    checkIsInfoLoaded()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load user (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserDataDTO>, t: Throwable) {
                Log.e("LoadUser", "Failed to load user", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to load user (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun saveDate() {
        if (!isEdited)
            return

        if (passwordText.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Введите текущий пароль для изменения данных",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (newPasswordText.text.toString() != repeatNewPasswordText.text.toString()) {
            Toast.makeText(
                requireContext(),
                "Поля 'Новый пароль' и 'Повторите новый пароль' не совпадают",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val userEditRequestDTO: UserEditRequestDTO = UserEditRequestDTO()
        userEditRequestDTO.password = passwordText.text.toString()

        val editMap: HashMap<String, String> = HashMap<String, String>()

        if (nicknameText.text.toString() != userDataDTO.nickname) {
            editMap["nickname"] = nicknameText.text.toString()
        }

        if (loginText.text.toString() != userDataDTO.username) {
            editMap["username"] = loginText.text.toString()
        }

        if (emailText.text.toString() != userDataDTO.email) {
            editMap["email"] = emailText.text.toString()
        }

        if (newPasswordText.text.toString().isNotBlank() && repeatNewPasswordText.text.toString()
                .isNotBlank()
        ) {
            editMap["password"] = newPasswordText.text.toString()
        }

        userEditRequestDTO.editMap = editMap

        val retrofitClient = RetrofitClient()
        val userAPI = retrofitClient.getRetrofit(requireContext()).create(UserAPI::class.java)

        userAPI.edit(userEditRequestDTO).enqueue(object : Callback<UserEditResponseDTO> {
            override fun onResponse(
                call: Call<UserEditResponseDTO>,
                response: Response<UserEditResponseDTO>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    checkEditResponse(response.body()!!)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to edit user (onResponse)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserEditResponseDTO>, t: Throwable) {
                Log.e("LoadUser", "Failed to edit user", t)
                Toast.makeText(
                    requireContext(),
                    "Failed to edit user (onFailure)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun checkEditResponse(userEditResponseDTO: UserEditResponseDTO) {
        for ((key, value) in userEditResponseDTO.editMap.entries) {
            if (key == "username" && !value.startsWith("Error")) {
                val sessionManager = SessionManager(requireContext())
                sessionManager.saveAuthToken(value)

                Toast.makeText(
                    requireContext(),
                    "username: Successfully changed",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "$key: $value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        loadData()
    }
}