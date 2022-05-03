package com.example.organizze.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.example.organizze.Helper.DataCustom
import com.example.organizze.Model.Movimentacao
import com.example.organizze.Model.Usuario
import com.example.organizze.R
import com.example.organizze.databinding.ActivityReceitaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReceitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceitaBinding
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference()
    var receitaTotal:Double? = null
    var receitaDigitada:Double? = null
    var receitaAtualizada:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReceitaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        val dataCustom = DataCustom()
        val editValor = binding.editValor
        val editData = binding.editData
        val editCategoria = binding.editCategoria
        val editDescricao = binding.editDescricao

        editData.setText(dataCustom.dataAtual())
        recuperarReceitaTotal()

        binding.fabReceita.setOnClickListener {
            if (validarCampos()) {
                val movimentacao = Movimentacao()
                val valorRecuperado = editValor.text.toString().toDouble()

                movimentacao.valor = valorRecuperado
                movimentacao.data = editData.text.toString()
                movimentacao.categoria = editCategoria.text.toString()
                movimentacao.descricao = editDescricao.text.toString()
                movimentacao.tipo = "r"

                receitaDigitada = valorRecuperado
                receitaAtualizada = receitaTotal!! + receitaDigitada!!

                myRef.child("usuarios").child(auth.uid.toString())
                    .child("receitaTotal")
                    .setValue(receitaAtualizada)

                myRef.child("movimentacao")
                    .child(auth.uid.toString())
                    .child(dataCustom.diaMesAno(movimentacao.data))
                    .push()
                    .setValue(movimentacao)

                editValor.setText("")
                editData.setText(dataCustom.dataAtual())
                editCategoria.setText("")
                editDescricao.setText("")

                Toast.makeText(applicationContext, "Valor atribuido com sucesso!",
                    Toast.LENGTH_SHORT).show()
                finish()

            }else{
                Toast.makeText(applicationContext, "Erro ao passar valor!",
                    Toast.LENGTH_SHORT).show()
            }



        }
    }

    fun recuperarReceitaTotal(){
        val usuarioRef = myRef.child("usuarios").child(auth.uid.toString())

        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Usuario::class.java)
                receitaTotal = usuario?.receitaTotal
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun validarCampos(): Boolean {
        val editValor = binding.editValor.text.toString()
        val editData = binding.editData.text.toString()
        val editCategoria = binding.editCategoria.text.toString()
        val editDescricao = binding.editDescricao.text.toString()
        if (!editValor.isEmpty()) {
            if (!editData.isEmpty()){
                if (!editCategoria.isEmpty()){
                    if (!editDescricao.isEmpty()){
                        return true
                    }else{
                        Toast.makeText(applicationContext, "Preencha uma descrição!",
                            Toast.LENGTH_SHORT).show()
                        return false
                    }
                }else{
                    Toast.makeText(applicationContext, "Preencha uma categoria!",
                        Toast.LENGTH_SHORT).show()
                    return false
                }
            }else{
                Toast.makeText(applicationContext, "Preencha uma data!",
                    Toast.LENGTH_SHORT).show()
                return false
            }
        }else{
            Toast.makeText(applicationContext, "Preencha um valor!",
                Toast.LENGTH_SHORT).show()
            return false
        }

    }
}