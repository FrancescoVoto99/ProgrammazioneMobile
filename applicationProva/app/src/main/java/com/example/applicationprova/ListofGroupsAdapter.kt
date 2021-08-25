package com.example.applicationprova

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar



class ListofGroupsAdapter (val data: List<String>,val data2: List<String>):

    RecyclerView.Adapter<ListofGroupsAdapter.MyViewHolder>() {


        class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
            val textView = row.findViewById<TextView>(R.id.item)
            val textView2 = row.findViewById<TextView>(R.id.idgroup)

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                MyViewHolder {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_groups, parent, false)
            val holder = MyViewHolder(layout)
            holder.row.setOnClickListener(){

                Snackbar.make(parent.rootView,"Click!"+ holder.textView.text, Snackbar.LENGTH_SHORT).show()
                val intent = Intent(holder.row.context, ListOfProducts::class.java)
                intent.putExtra("key", holder.textView2.text.toString() )
                holder.row.context.startActivity(intent)


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