package com.example.organizze.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.organizze.R
import com.example.organizze.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class MainActivity : IntroActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)

        auth = Firebase.auth

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide( SimpleSlide.Builder()
            .title("Rápido e fácil")
            .description("Organize suas contas de onde estiver.")
            .image(R.drawable.um)
            .background(R.color.white)
            .build()
        )
        addSlide( SimpleSlide.Builder()
            .title("Navegação simples")
            .description("Saiba para onde está indo seu dinheiro.")
            .image(R.drawable.dois)
            .background(R.color.white)
            .build()
        )
        addSlide( SimpleSlide.Builder()
            .title("Pontualidade")
            .description("Nunca mais esqueça de pagar uma conta.")
            .image(R.drawable.tres)
            .background(R.color.white)
            .build()
        )
        addSlide( SimpleSlide.Builder()
            .title("Organizze-se ;)")
            .description("Tudo organizado, no celular ou no computador.")
            .image(R.drawable.quatro)
            .background(R.color.white)
            .build()
        )
        addSlide( FragmentSlide.Builder()
            .canGoForward(false)
            .fragment(R.layout.fragment_slide)
            .background(R.color.white)
            .build()
        )

    }

    fun btnCadastro(view: View){
        startActivity(Intent(this, CadastroActivity::class.java))
    }
    fun btnLogin(view: View){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun validarUsuario(){
        if(auth.currentUser != null){
            startActivity(Intent(this, PrincipalActivity::class.java))
        }
    }

    override fun onStart() {
        validarUsuario()
        super.onStart()
    }

}