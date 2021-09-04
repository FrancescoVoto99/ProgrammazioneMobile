package com.example.applicationprova

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfProductsBinding
import com.example.progetto.Prodotto
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

 class ListOfProducts : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var searchproducts: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        val list = mutableListOf<String>()
        val list2 = mutableListOf<String>()
        super.onCreate(savedInstanceState)
        //data binding al posto del classico inflate
        val binding: ActivityListOfProductsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_list_of_products
        )




        var auth = Firebase.auth
        val currentUser = auth.currentUser


        database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        searchproducts= database.getReference("gruppi")


        val rv: RecyclerView = binding.listaProdotti
        rv.layoutManager = LinearLayoutManager(this)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")

            searchproducts.child(value.toString()).child("prodotti").get().addOnSuccessListener {
                for (postSnapshot in it.children) {
                    if((postSnapshot.child("buy").getValue().toString()).equals("0")) {
                        list.add(postSnapshot.child("nome").getValue().toString())
                        list2.add(postSnapshot.key.toString())
                    }
                }
                rv.adapter = ListofProductAdapter(list, list2,value.toString())

            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
              //  rv.adapter = ListofProductAdapter(list, list2)
            }

        }

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.listaprodotti -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.listaSpese -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.saldi -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.statistiche -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }


        //Action button
        val fab: View = binding.fabProduct
        fab.setOnClickListener {
            fabOnClick()
        }
        //button

        binding.buyBut.setOnClickListener{

            //Instantiate builder variable
            val builder = AlertDialog.Builder(findViewById<View>(android.R.id.content).rootView.context)

            // set title
            builder.setIcon(R.drawable.ic_baseline_shopping_cart_24)
            builder.setTitle("Conferma spesa")
            //set content area
            builder.setMessage("Inserisci il prezzo della spesa")
            //textInput
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            val input = EditText(this)
            input.setHint("Prezzo")
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            layout.addView(input)
            val nome = EditText(this)
            nome.setHint("Nome Spesa")
            nome.inputType = InputType.TYPE_CLASS_TEXT
            layout.addView(nome)
            builder.setView(layout)
            //set positive button
            builder.setPositiveButton(
                "Conferma") { dialog, id ->
                // User clicked Update Now button
                var prezzo = input.text.toString().toFloat()
                var nomeSpesa = nome.text.toString()
                insertSpesa(prezzo, nomeSpesa)

            }

            //set negative button
            builder.setNegativeButton(
                "Annulla") { dialog, id ->
                // User cancelled the dialog
                Toast.makeText(this, "Annullato", Toast.LENGTH_SHORT).show()

            }
            builder.show()

        }


    }

    public fun fabOnClick() {
        val intent = Intent(this, InserisciProdotto::class.java)
        val extras = this.intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            intent.putExtra("key",value.toString()  )
        }
        startActivity(intent)
    }


    private fun insertSpesa(costo: Float, nomeSpesa: String){
        val idList = SingletonIdProducts.getId()
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            val auth = Firebase.auth
            val currentUser = auth.currentUser
            val spesa = Spesa(currentUser?.email.toString().replace(".","'") ,currentUser?.displayName.toString(),nomeSpesa,costo,idList)
            myRef=database.getReference("gruppi")
            myRef.child(value.toString()).child("spese").push().setValue(spesa).addOnSuccessListener {
                changeBuyBit()
                SingletonIdProducts.clear()
                val intent = Intent(this, ListOfShop::class.java)
                intent.putExtra("key", value.toString())
                startActivity(intent)

            }
        }
    }
    private fun changeBuyBit(){
        val idList = SingletonIdProducts.getId()
        myRef=database.getReference("gruppi")
        val extras = intent.extras
        val value = extras?.getString("key")
        for(item in idList){
            myRef.child(value.toString()).child("prodotti").child(item).child("buy").setValue("1") .addOnSuccessListener {
                Log.d("Firebase", "Modified buy bit")
            }
        }
    }
    public fun delete(item: String){
        myRef=database.getReference("gruppi")
        val extras = intent.extras
        val value = extras?.getString("key")
            myRef.child(value.toString()).child("prodotti").child(item).removeValue().addOnSuccessListener {
                Log.d("Firebase", "Deleted product")
            }

    }
}