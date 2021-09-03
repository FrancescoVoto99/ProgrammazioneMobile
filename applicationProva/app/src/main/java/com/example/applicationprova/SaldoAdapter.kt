package com.example.applicationprova

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SaldoAdapter(private val context : Activity, private  val map: Map<String,Float>,private  val arrayList: ArrayList<String>): ArrayAdapter<String>(context,R.layout.item_divisione_spese,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater :LayoutInflater= LayoutInflater.from(context )
        val view:View= inflater.inflate(R.layout.item_divisione_spese,null)

        val Utente : TextView = view.findViewById(R.id.utente)
        val saldo : TextView = view.findViewById(R.id.saldo)

        Utente.text=arrayList[position]

        val divisione: Float=map.values.sum()/map.size
        val dovuto: Float=map.values.toFloatArray()[position]-divisione

        if (dovuto>0){
            saldo.setBackgroundColor(Color.RED)
        }
        else{
            saldo.setBackgroundColor(Color.GREEN)
        }
        saldo.text=dovuto.toString()

        return view
    }


}