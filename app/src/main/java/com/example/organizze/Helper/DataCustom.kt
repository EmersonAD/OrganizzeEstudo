package com.example.organizze.Helper

import java.text.SimpleDateFormat

class DataCustom {
        fun dataAtual(): String {
            val data:Long = System.currentTimeMillis()
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dataString = simpleDateFormat.format(data)
            return dataString
        }

    fun diaMesAno(data:String): String {
       val retornoData:Array<String> = data.split("/").toTypedArray()
        var dia = retornoData[0]
        var mes = retornoData[1]
        var ano = retornoData[2]

        val mesAno = mes + ano
        return mesAno

    }
}