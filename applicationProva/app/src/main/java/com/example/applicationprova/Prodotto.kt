package com.example.progetto

data class Prodotto(
    val Nome: String?=null, val Categoria: String?=null, val Quantita: String? =null,
    val note: String?=null,
    val iduser: String?=null, val NomeUtente:String?=null, val buy:String="0",
    val carrello: String="0")
