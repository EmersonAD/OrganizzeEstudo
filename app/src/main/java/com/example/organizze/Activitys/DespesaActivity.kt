package com.example.organizze.Activitys

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.organizze.Helper.DataCustom
import com.example.organizze.Model.Movimentacao
import com.example.organizze.Model.Usuario
import com.example.organizze.databinding.ActivityDespesaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class DespesaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDespesaBinding

    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference()
    private var despesaTotal:Double? = null
    private var despesaDigitada: Double? = null
    private var despesaAtualizada:Double? = null
    private val usuarioRef = myRef.child("usuarios")


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDespesaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        val dataCustom = DataCustom()
        val editValor = binding.editValor
        val editData = binding.editData
        val editCaterigoria = binding.editCategoria
        val editDescricao = binding.editDescricao

        editData.setText(dataCustom.dataAtual())
        recuperarDespesaTotal()

    binding.fabDespesa.setOnClickListener {

        if (validarCampos()) {
            val movimentacao = Movimentacao()
            val valorRecuperado = editValor.text.toString().toDouble()
            movimentacao.valor = valorRecuperado
            movimentacao.categoria = editCaterigoria.text.toString()
            movimentacao.data = editData.text.toString()
            movimentacao.descricao = editDescricao.text.toString()
            movimentacao.tipo = "d"

            despesaDigitada = valorRecuperado
            despesaAtualizada = despesaTotal!! + despesaDigitada!!

            myRef.child("usuarios").child(auth.uid.toString()).child("despesaTotal")
                .setValue(despesaAtualizada)

            myRef.child("movimentacao")
                .child(auth.uid.toString())
                .child(dataCustom.diaMesAno(movimentacao.data))
                .push()
                .setValue(movimentacao)

            Toast.makeText(applicationContext,
                "Despesa adicionada com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
            }
        }
    }



    fun recuperarDespesaTotal(){
       usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Usuario::class.java)
                despesaTotal = usuario?.despesaTotal

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun validarCampos(): Boolean {
        val editValor = binding.editValor.text.toString()
        val editData = binding.editData.text.toString()
        val editCaterigoria = binding.editCategoria.text.toString()
        val editDescricao = binding.editDescricao.text.toString()

        if (!editValor.isEmpty()){
            if (!editData.isEmpty()){
                if (!editCaterigoria.isEmpty()){
                    if (!editDescricao.isEmpty()){
                        return true
                    }else{
                        Toast.makeText(applicationContext,
                        "Digite uma descrição", Toast.LENGTH_SHORT).show()
                        return false
                    }
                }else{
                    Toast.makeText(applicationContext,
                        "Digite uma categoria", Toast.LENGTH_SHORT).show()
                    return false
                }
            }else{
                Toast.makeText(applicationContext,
                    "Digite uma data", Toast.LENGTH_SHORT).show()
                return false
            }
        }else{
            Toast.makeText(applicationContext,
                "Digite um valor", Toast.LENGTH_SHORT).show()
            return false
        }

    }

}