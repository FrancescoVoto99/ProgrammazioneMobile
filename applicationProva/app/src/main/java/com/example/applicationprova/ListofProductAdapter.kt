package com.example.applicationprova

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.Prodotto
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ListofProductAdapter (val data: List<String>,val data2: List<String>, val idGroup: String):
    RecyclerView.Adapter<ListofProductAdapter.MyViewHolder>() {
    val database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
    val myRef = database.getReference("gruppi")
    val auth = Firebase.auth
    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView = row.findViewById<TextView>(R.id.item)
        val textView2 = row.findViewById<TextView>(R.id.idproduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products, parent, false)
        val holder = MyViewHolder(layout)
        holder.row.setOnClickListener(){
            Snackbar.make(parent.rootView,"Click!", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(holder.row.context, EditProduct::class.java)
            intent.putExtra("key", idGroup)
            intent.putExtra("idProduct", holder.textView2.text.toString())
            holder.row.context.startActivity(intent)

        }
        holder.row.setOnLongClickListener (){
            val idProduct =  holder.textView2.text.toString()
            var carrello = true; //come prendo il valore del carrello nel database?
            if(!(carrello)) {
                myRef.child(idGroup).child("prodotti").child(idProduct).child("carrello")
                    .setValue("1").addOnSuccessListener {
                    //holder.row.setBackgroundColor(parseColor("#00ff00"))
                }
                Snackbar.make(parent.rootView,"Aggiunto al carrello", Snackbar.LENGTH_SHORT).show()
            }
            else {
                myRef.child(idGroup).child("prodotti").child(idProduct).child("carrello")
                    .setValue("0").addOnSuccessListener {
                    //holder.row.setBackgroundColor(parseColor("#ff0000"))
                }
                Snackbar.make(parent.rootView,"Tolto dal carrello!", Snackbar.LENGTH_SHORT).show()
            }
            true
        }

        return holder
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = data.get(position).toString()
        holder.textView2.text = data2.get(position).toString()
        Log.d("ListofGroupAdapter",data.get(position).toString())

    }

    override fun getItemCount(): Int = data.size

}