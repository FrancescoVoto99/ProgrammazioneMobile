package com.example.applicationprova

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfProductsBinding
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

        //setContentView(R.layout.activity_list_of_groups)

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
                    if(!postSnapshot.child("buy").getValue().toString().toBoolean()) {
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
            val input = EditText(this)
            input.setHint("Prezzo")
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setView(input)
            //set positive button
            builder.setPositiveButton(
                "Conferma") { dialog, id ->
                // User clicked Update Now button
                var prezzo = input.text.toString().toFloat()
                confermaSpesa(prezzo)

            }

            //set negative button
            builder.setNegativeButton(
                "Annulla") { dialog, id ->
                // User cancelled the dialog
                Toast.makeText(this, "Annullato", Toast.LENGTH_SHORT).show()

            }
            builder.show()
            //val pop = Popup()
            //pop.show(supportFragmentManager, "Popup")
        }


    }

    private fun fabOnClick() {
        val intent = Intent(this, InserisciProdotto::class.java)
        val extras = this.intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            intent.putExtra("key",value.toString()  )
        }
        startActivity(intent)
    }
    private fun confermaSpesa(costo: Float) {

        //Come faccio a modificare il valore buy dei prodotti acquistati?
        //Creare spesa su firebase e popolarla
        //lista con gli id dei prodotti acquistati
        val idList = SingletonIdProducts.getId()

        val intent = Intent(this, ListOfShop::class.java)
        val extras = this.intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            intent.putExtra("key", value.toString())
        }
        startActivity(intent)
    }
}