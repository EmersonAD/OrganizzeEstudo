package com.example.organizze.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.example.organizze.Helper.Base64Custom
import com.example.organizze.Model.Usuario
import com.example.organizze.R
import com.example.organizze.databinding.ActivityCadastroBinding
import com.example.organizze.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference()
    var base64Custom = Base64Custom()



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        val nomeCadastro = binding.editNome
        val emailCadastro = binding.editEmail
        val senhaCadastro = binding.editSenha



        binding.btnCadastro.setOnClickListener {

            val usuario = Usuario()
            usuario.email = emailCadastro.text.toString()
            usuario.senha = senhaCadastro.text.toString()
            usuario.nome = nomeCadastro.text.toString()

            if (!usuario.nome.isEmpty()){
                if (!usuario.email.isEmpty()){
                    if (!usuario.senha.isEmpty()){

                        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener {
                                task ->
                            if (task.isSuccessful){
                                try {
                                    myRef.child("usuarios").child(auth.uid.toString()).setValue(usuario)
                                    finish()
                                }catch (e:Exception){
                                    Toast.makeText(applicationContext,
                                    "Erro: ${e.message}",
                                    Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                var excecao:String = ""
                                try {
                                    throw task.exception!!
                                }catch (e: FirebaseAuthWeakPasswordException){
                                    excecao = "Escolha uma senha mais forte!"
                                }catch (e: FirebaseAuthInvalidCredentialsException){
                                    excecao = "Insira um email válido!"
                                }catch (e: FirebaseAuthUserCollisionException){
                                    excecao = "Email já cadastrado!"
                                }catch (e: Exception){
                                    excecao = "Erro ao cadastrar usuário ${e.message}"
                                    e.printStackTrace()
                                }
                                Toast.makeText(applicationContext,
                                    excecao,
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                    }else{
                        Toast.makeText(applicationContext,
                            "Preencha sua senha!",
                            Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext,
                        "Preencha seu email!",
                        Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext,
                "Preencha seu nome!",
                Toast.LENGTH_SHORT).show()
            }
        }

    }


}