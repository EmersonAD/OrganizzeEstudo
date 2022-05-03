package com.example.organizze.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.organizze.R
import com.example.organizze.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
       binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val emailLogin = binding.editLoginEmail
        val senhaLogin = binding.editLoginSenha

        auth = Firebase.auth



        binding.btnLogar.setOnClickListener {

            val emailLog = emailLogin.text.toString()
            val senhaLog = senhaLogin.text.toString()


            auth.signInWithEmailAndPassword(emailLog, senhaLog).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, PrincipalActivity::class.java ))
                    Toast.makeText(applicationContext,
                        "Login efetuado com sucesso",
                        Toast.LENGTH_SHORT).show()
                }else{
                    var excecao:String = ""
                    try {
                       throw task.exception!!
                    }catch (e: FirebaseAuthInvalidUserException){
                        excecao = "Email não cadastrado"
                    }catch (e: FirebaseAuthInvalidCredentialsException){
                        excecao = "Senha inválida"
                    }catch (e:Exception){
                        excecao = "Erro ao efetuar login ${e.message}"
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext,
                        excecao,
                        Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}