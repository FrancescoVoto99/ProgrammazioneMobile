package com.example.applicationprova

import com.example.progetto.Prodotto

import android.content.Intent
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class ListOfProductBoughtAdapter (val prodotti: List<Prodotto>):
    RecyclerView.Adapter<ListOfProductBoughtAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products_bought, parent, false)
        val holder = MyViewHolder(layout)
        holder.row.setOnClickListener(){
            Snackbar.make(parent.rootView,"Click!", Snackbar.LENGTH_SHORT).show()

        }
        return holder
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = prodotti.get(position).nome.toString()
        holder.textView2.text = prodotti.get(position).iduser.toString()

        //gets position from data object
        var data_position = prodotti[position]

        Log.d("ListofGroupAdapter",prodotti.get(position).toString())
    }

    override fun getItemCount(): Int = prodotti.size

    inner class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView = row.findViewById<TextView>(R.id.item)
        val textView2 = row.findViewById<TextView>(R.id.idproduct)


    }


}