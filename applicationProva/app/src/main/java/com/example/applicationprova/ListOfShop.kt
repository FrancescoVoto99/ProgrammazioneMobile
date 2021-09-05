package com.example.applicationprova

import android.content.Intent
import android.icu.number.Precision
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfProductsBinding
import com.example.applicationprova.databinding.ActivityListOfShopBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.round


class ListOfShop : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var searchshop: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_shop)

        val nameShop = mutableListOf<String>()
        val whobuy = mutableListOf<String>()
        val price = mutableListOf<Float>()

        //data binding al posto del classico inflate
        val binding: ActivityListOfShopBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_list_of_shop
        )
        //setContentView(R.layout.activity_list_of_groups)

        var auth = Firebase.auth
        val currentUser = auth.currentUser


        database =
            FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        searchshop = database.getReference("gruppi")


        val rv: RecyclerView = binding.listaSpese




        rv.layoutManager = LinearLayoutManager(this)



        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")

            searchshop.child(value.toString()).child("spese").get().addOnSuccessListener {
                for (postSnapshot in it.children) {
                    if (!postSnapshot.child("buy").getValue().toString().toBoolean()) {
                        nameShop.add(postSnapshot.child("nomespesa").getValue().toString())
                        whobuy.add(postSnapshot.child("nomeutente").getValue().toString())
                        price.add(postSnapshot.child("totale").getValue().toString().toFloat())
                    }
                }

                rv.adapter = ListOfShopAdapter(nameShop, whobuy, price, value.toString())
                binding.spesaTotale.text=String.format("%.2f", price.sum())+"€"

                searchshop.child(value.toString()).child("gruppo").get().addOnSuccessListener {
                    binding.miaSpesa.text =String.format("%.2f", price.sum()/it.children.count())+"€"
                }



            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
                //  rv.adapter = ListofProductAdapter(list, list2)
            }


        }
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.listaspese
        bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.listaprodotti -> {
                    val intent = Intent(this, ListOfProducts::class.java)
                    intent.putExtra("key", extras?.getString("key"))
                    startActivity(intent)
                    true
                }
                R.id.listaspese -> {
                    /*val intent = Intent(this, ListOfShop::class.java)
                    intent.putExtra("key", extras?.getString("key"))
                    startActivity(intent)
                    // Respond to navigation item 2 click

                     */
                    true
                }
                R.id.saldi -> {
                    val intent = Intent(this, Saldo::class.java)
                    intent.putExtra("key", extras?.getString("key"))
                    startActivity(intent)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.statistiche -> {
                    val intent = Intent(this, Statistic::class.java)
                    intent.putExtra("key", extras?.getString("key"))
                    startActivity(intent)
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

    }
}