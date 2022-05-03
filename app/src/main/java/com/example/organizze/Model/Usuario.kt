package com.example.organizze.Model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val database = Firebase.database
val myRef = database.getReference()


class Usuario {


    var nome:String = ""
    var email:String = ""
    var senha:String = ""
    var receitaTotal = 0.00
    var despesaTotal = 0.00

}

