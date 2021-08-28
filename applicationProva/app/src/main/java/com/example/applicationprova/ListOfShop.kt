package com.example.applicationprova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfProductsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ListOfShop : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var searchshop: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_shop)

        val nameShop = mutableListOf<String>()
        val whobuy = mutableListOf<String>()
        val price = mutableListOf<String>()

        //data binding al posto del classico inflate
        val binding: ActivityListOfProductsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_list_of_products
        )
        //setContentView(R.layout.activity_list_of_groups)

        var auth = Firebase.auth
        val currentUser = auth.currentUser


        database =
            FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        searchshop = database.getReference("gruppi")


        val rv: RecyclerView = binding.listaProdotti
        rv.layoutManager = LinearLayoutManager(this)

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")

            searchshop.child(value.toString()).child("spese").get().addOnSuccessListener {
                for (postSnapshot in it.children) {
                    if (!postSnapshot.child("buy").getValue().toString().toBoolean()) {
                        nameShop.add(postSnapshot.child("nome spesa").getValue().toString())
                        whobuy.add(postSnapshot.child("nome utente").getValue().toString())
                        price.add(postSnapshot.child("totale").getValue().toString())
                    }
                }
                rv.adapter = ListOfShopAdapter(nameShop, whobuy,price, value.toString())


            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
                //  rv.adapter = ListofProductAdapter(list, list2)
            }


        }
    }
}