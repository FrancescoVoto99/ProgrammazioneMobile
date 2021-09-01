package com.example.applicationprova

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

class ListofProductAdapter (val data: List<String>,val data2: List<String>, val idGroup: String):
    RecyclerView.Adapter<ListofProductAdapter.MyViewHolder>() {
    var checkBoxStateArray = SparseBooleanArray()

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
        return holder
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = data.get(position).toString()
        holder.textView2.text = data2.get(position).toString()
        if(!checkBoxStateArray.get(position,false))
        {//checkbox unchecked.
            holder.checkBox.isChecked = false
        }
        else
        {//checkbox checked
            holder.checkBox.isChecked = true
        }
        //gets position from data object
        var data_position = data[position]

        Log.d("ListofGroupAdapter",data.get(position).toString())
    }

    override fun getItemCount(): Int = data.size

    inner class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView = row.findViewById<TextView>(R.id.item)
        val textView2 = row.findViewById<TextView>(R.id.idproduct)
        val checkBox = row.findViewById<CheckBox>(R.id.checkBox)

        init
        {//called after the constructor.

            checkBox.setOnClickListener {

                if(!checkBoxStateArray.get(adapterPosition,false))
                {//checkbox checked
                    checkBox.isChecked = true
                    SingletonIdProducts.addId(textView2.toString())
                    //stores checkbox states and position
                    checkBoxStateArray.put(adapterPosition,true)
                }
                else
                {//checkbox unchecked
                    checkBox.isChecked = false
                    SingletonIdProducts.removeId(textView2.toString())
                    //stores checkbox states and position.
                    checkBoxStateArray.put(adapterPosition,false)
                }

            }
        }
    }


}