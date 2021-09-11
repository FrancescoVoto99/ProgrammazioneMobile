package com.example.applicationprova.model


data class Spesa(
    val idutente: String?=null, val nomeutente: String?=null, val nomespesa: String? =null,
    val totale: Float?=null, val prodotti: List<String>?=null)
