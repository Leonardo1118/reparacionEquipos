package com.example.reparacionequipos.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.reparacionequipos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}