package com.example.organizze.Activitys

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizze.Adapter.AdapterOrganizze
import com.example.organizze.Model.Movimentacao
import com.example.organizze.Model.Usuario
import com.example.organizze.R
import com.example.organizze.databinding.ActivityPrincipalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import java.text.DecimalFormat

class PrincipalActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPrincipalBinding
    val database = Firebase.database
    val myRef = database.getReference()
    private var despesaTotal = 0.0
    private var receitaTotal = 0.0
    private var resumoTotal = 0.0
    private lateinit var eventListenerUsuario:ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        fabMenu()

        //criando a lista para passar no adapter
        val movimentacoes:MutableList<Movimentacao> = mutableListOf()

        //Instanciar Adapter
        val adapter = AdapterOrganizze(this, movimentacoes)


        //Configurando Reycler
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter




        //caledário
        val calendarView = binding.calendarView
        configurationCalendarView()

    }

    fun recuperarSaldoTotal(){
        val usuarioRef = myRef.child("usuarios").child(auth.uid.toString())

        Log.i("Evento", "Evento iniciado")
        eventListenerUsuario = usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usuario = snapshot.getValue(Usuario::class.java)
                receitaTotal = usuario!!.receitaTotal
                despesaTotal = usuario.despesaTotal
                resumoTotal = receitaTotal-despesaTotal

                val decimalFormat = DecimalFormat("0.##")
                val resumoFormatado = decimalFormat.format(resumoTotal)

                binding.txtUsuario.text = "Olá, ${usuario.nome}"
                binding.txtSaldo.setText("R$ $resumoFormatado")

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onStart() {
        recuperarSaldoTotal()
        super.onStart()
    }

    override fun onStop() {
        val usuarioRef = myRef.child("usuarios").child(auth.uid.toString())
        Log.i("Evento", "Evento foi removido")
        usuarioRef.removeEventListener(eventListenerUsuario)
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnLogout -> {
                auth.signOut()
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun fabMenu(){
        val icon = ImageView(this)
        icon.setImageResource(R.drawable.ic_add2)

        val actionButton = FloatingActionButton.Builder(this)
            .setContentView(icon)
            .setBackgroundDrawable(R.drawable.shape)
            .build()

        val itemBuilder:SubActionButton.Builder = SubActionButton.Builder(this)
        val itemIcon = ImageView(this)
        itemIcon.setImageResource(R.drawable.ic_addfab)
        val itemIcon2 = ImageView(this)
        itemIcon2.setImageResource(R.drawable.ic_remove)
        val buttonReceita = itemBuilder.setContentView(itemIcon).build()
        val buttonDespesa = itemBuilder.setContentView(itemIcon2).build()

        val actionMenu = FloatingActionMenu.Builder(this)
            .addSubActionView(buttonReceita)
            .addSubActionView(buttonDespesa)
            .attachTo(actionButton)
            .build()

        buttonReceita.setOnClickListener {
            startActivity(Intent(applicationContext, ReceitaActivity::class.java))
        }
        buttonDespesa.setOnClickListener {
            startActivity(Intent(applicationContext, DespesaActivity::class.java))
        }
    }

    fun configurationCalendarView(){

        val calendarView = binding.calendarView
        val meses = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
            "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
        calendarView.setTitleMonths(meses)

        calendarView.setOnMonthChangedListener { widget, date ->

        }
    }

}