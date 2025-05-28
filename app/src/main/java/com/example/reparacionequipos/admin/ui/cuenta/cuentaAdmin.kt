package com.example.reparacionequipos.admin.ui.cuenta

import android.content.Context
import android.content.Intent

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.cardview.widget.CardView

import com.example.reparacionequipos.databinding.FragmentCuentaAdminBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.example.reparacionequipos.login.MainActivity


class cuentaAdmin : Fragment() {
    private lateinit var binding: FragmentCuentaAdminBinding
    private val cuentaAdminViewModel: CuentaAdminViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private  lateinit var micontext : Context


    private  lateinit var text_cerrar : TextView

    private lateinit var card : CardView

    private  lateinit var NOMBRE : TextView
    private  lateinit var EMAIL : TextView
    private  lateinit var ROL : TextView
    private  lateinit var FECHA : TextView

    override fun onAttach(context: Context) {
        micontext =context
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuentaAdminBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NOMBRE = binding.userInfo
        EMAIL  =binding.emailInfo
        ROL = binding.rolInfo
        FECHA = binding.registroInfo


        text_cerrar = binding.cerrarSesionoyente
        card = binding.cardCerrar
        auth = FirebaseAuth.getInstance()

        cuentaAdminViewModel.cargarDatosUsuario()

        mostar_info_cuenta()

        text_cerrar.setOnClickListener{
            text_cerrar.setTextColor("#003C43".toColorInt())
            card.setCardBackgroundColor("#EAAE56".toColorInt())


            cerrarSesion()
            Toast.makeText(context, "SESION CERRADA", Toast.LENGTH_SHORT).show()

        }


    }

    private fun cerrarSesion (){
        auth.signOut()
        val dir = Intent(context, MainActivity::class.java)
        startActivity(dir)
        activity?.finishAffinity()
    }


    private fun mostar_info_cuenta() {

        lifecycleScope.launchWhenStarted {
            cuentaAdminViewModel.userInfoState.collect(){ result ->
                result?.onSuccess { userInfo ->
                    NOMBRE.text = userInfo.nombre
                    EMAIL.text = userInfo.email
                    ROL .text = userInfo.rol

                }?.onFailure {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}