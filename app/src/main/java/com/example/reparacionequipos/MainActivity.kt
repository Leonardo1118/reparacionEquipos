package com.example.reparacionequipos.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope


import com.example.reparacionequipos.databinding.ActivityMainBinding
import com.example.reparacionequipos.login.model.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var email : TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var btn_login : Button
    private lateinit var btn_exit : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.inputEmail
        password = binding.inputPassoword
        btn_login = binding.btnIngresar
        btn_exit = binding.btnSalir

        btn_login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            loginViewModel.login(email, password)
        }

        observarLogin()
    }

    private fun observarLogin() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Idle -> {}
                    is LoginState.Loading -> {
                        // Mostrar loader si querés
                    }
                    is LoginState.Success -> {
                        // Redirigir al home
                        Toast.makeText(this@MainActivity, "correcto", Toast.LENGTH_SHORT).show()
                        //startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        //finish()
                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}