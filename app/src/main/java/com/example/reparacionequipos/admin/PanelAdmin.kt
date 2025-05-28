package com.example.reparacionequipos.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.example.reparacionequipos.admin.ui.tecnico.TecnicoFragment
import com.example.reparacionequipos.databinding.ActivityPanelAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.example.reparacionequipos.R
import com.example.reparacionequipos.admin.ui.cuenta.cuentaAdmin

class PanelAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityPanelAdminBinding
    lateinit var MENU : BottomNavigationView
    lateinit var TITULO : TextView



    private lateinit var auth: FirebaseAuth
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanelAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        TITULO = binding.TituloAdmin

        MENU = binding.menuAdmin


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(this@PanelAdmin)
                builder.setMessage("¿Estás seguro de que quieres salir?")
                    .setCancelable(false)
                    .setPositiveButton("Sí") { dialog, id ->
                        // Si el usuario confirma, llama a onBackPressed para salir
                        finishAffinity()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Si el usuario cancela, cierra el diálogo sin hacer nada
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } //cierre del handle
        })


        MENU.setOnItemSelectedListener { item->
            when(item.itemId){

                R.id.Menu_programa->{


                    if (currentFragmentTag != "Fragments Tecnico") {

                        verFragmentoTecnico()
                    }
                    true
                }
                R.id.Menu_cuenta ->{
                    if (currentFragmentTag != "Fragments cuenta") {
                       verFragmentoCuenta()
                    }
                    true
                }
                R.id.Menu_categoria->{
                    if (currentFragmentTag != "Fragments categoria") {
                       // verFragmentoCategoria()
                    }
                    true
                }
                else->{
                    false
                }
            }
        }

    }//CIERRE DEL ONCREATE

    private fun verFragmentoTecnico(){
        val nombre_titulo= "TECNICOS"
        TITULO.text = nombre_titulo
        val fragment =TecnicoFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.FragmentsAdmin.id, fragment, "Fragments Tecnico")
        transaction.commit()
        currentFragmentTag = "Fragments Tecnico"

    }

    private fun verFragmentoCuenta(){
        val nombre_titulo= "MI CUENTA"
        TITULO.text = nombre_titulo
        val fragment = cuentaAdmin()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.FragmentsAdmin.id, fragment, "Fragments cuenta")
        transaction.commit()
        currentFragmentTag = "Fragments cuenta"

    }

    /*

    private fun verFragmentoPrograma(){
        val nombre_titulo= "PROGRAMA"
        TITULO.text = nombre_titulo
        val fragment =ProgramaLocutor()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.FragmentsAdmin.id, fragment, "Fragments programa")
        transaction.commit()
        currentFragmentTag = "Fragments programa"

    }*/



}
