package com.example.reparacionequipos.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.reparacionequipos.admin.PanelAdmin


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
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()
            loginViewModel.login(emailStr, passwordStr)
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
                    is LoginState.Success -> {//aqui no se hace nada
                        Toast.makeText(this@MainActivity,"Correcto", Toast.LENGTH_SHORT).show()



                    }
                    is LoginState.Error -> {
                        Toast.makeText(this@MainActivity,state.message, Toast.LENGTH_SHORT).show()
                        email.text?.clear()
                        password.text?.clear()
                    }

                    is LoginState.LoggedInUser -> {
                        val rol = state.rol
                        // Aquí podés redirigir según el rol
                        if (rol == "administrador") {
                            val dir = Intent(this@MainActivity, PanelAdmin::class.java)
                            startActivity(dir)
                            finishAffinity()
                        }




                    }
                }
            }
        }
    }
}