package com.wldnasyrf.ds.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wldnasyrf.ds.R
import com.wldnasyrf.ds.data.remote.model.user.LoginRequest
import com.wldnasyrf.ds.databinding.ActivityLoginBinding
import com.wldnasyrf.ds.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.edLoginEmail.text?.trim().toString()
            val password = binding.edLoginPassword.text?.trim().toString()

            if (email.isEmpty() && password.isEmpty() || !validateInputs(email, password)) {
                AlertDialog.Builder(this).apply {
                    setTitle("Oops!")
                    setMessage(R.string.email_n_password_empty)
                    setPositiveButton("OK") { _, _ -> }
                    create()
                    show()
                }
            } else {
                binding.edLoginEmail.clearFocus()
                binding.edLoginPassword.clearFocus()
                hideKeyboard()
                login(email, password)
            }
        }

        binding.txtRegisterNow.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(LoginRequest(email, password)).observe(this) { response ->
            when(response) {
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    finish()
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        val isEmailValid = email.isNotEmpty() && binding.edLoginEmail.error == null
        val isPasswordValid = password.isNotEmpty() && binding.edLoginPassword.error == null

        return isEmailValid && isPasswordValid
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.overlayBg.visibility = View.VISIBLE
            // Bring the progress bar to front
            binding.progressBar.bringToFront()
        } else {
            binding.progressBar.visibility = View.GONE
            binding.overlayBg.visibility = View.GONE
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}