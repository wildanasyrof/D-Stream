package com.wldnasyrf.ds.ui.login

import android.content.Context
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

            if (email.isEmpty() && password.isEmpty()) {
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

    private fun getUserPerferences() {
        viewModel.userPreferences.observe(this) {
            Toast.makeText(this, it.token, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.overlayBg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}