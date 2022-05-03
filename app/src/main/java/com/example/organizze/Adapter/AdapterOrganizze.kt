package com.example.organizze.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizze.Model.Movimentacao
import com.example.organizze.R

class AdapterOrganizze(private val context: Context, private val movimentacoes:MutableList<Movimentacao>):RecyclerView.Adapter
<AdapterOrganizze.MyViewHolder>() {

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.findViewById<TextView>(R.id.txtTitulo)
        val categoria = itemView.findViewById<TextView>(R.id.txtCategoria)
        val valor = itemView.findViewById<TextView>(R.id.txtValor)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.lista_organizze, parent, false)
        val holder = MyViewHolder(itemLista)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movimentacao = movimentacoes.get(position)

        holder.titulo.text = movimentacao.descricao
        holder.categoria.text = movimentacao.categoria
        holder.valor.text = movimentacao.valor.toString()

        if (movimentacao.tipo.equals("d")){
            holder.valor.setTextColor(context.resources.getColor(R.color.textoDespesa))
            holder.valor.setText("-${movimentacao.valor}")
        }

    }

    override fun getItemCount() = movimentacoes.size
}