package com.example.organizze.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Movimentacao {

    var data = ""
    var categoria = ""
    var descricao = ""
    var tipo = ""
    var valor:Double = 0.0

}