package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.applicationprova.databinding.ActivityEditProductBinding
import com.example.applicationprova.databinding.ActivityInserisciProdottoBinding
import com.example.progetto.Prodotto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class EditProduct: AppCompatActivity() {
    lateinit var nome : EditText
    lateinit var categoria: EditText
    lateinit var quantita: EditText
    lateinit var note: EditText



    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEditProductBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_edit_product)
        //setContentView(R.layout.activity_inserisci_prodotto)

        nome=binding.Nomeprodotto
        categoria=binding.spinnercategory
        quantita=binding.spinnerquantity
        note=binding.note



        database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("gruppi")

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            val idProduct= extras.getString("idProduct")

            myRef.child(value.toString()).child("prodotti").child(idProduct.toString()).get().addOnSuccessListener {


                   binding.Nomeprodotto.setText(it.child("nome").getValue().toString())
                    binding.spinnercategory.setText(it.child("categoria").getValue().toString())
                    binding.spinnerquantity.setText(it.child("quantita").getValue().toString())
                    binding.note.setText(it.child("note").getValue().toString())





            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)

            }



        }





        binding.btnUpdate.setOnClickListener{

            updateProduct()
        }
        binding.btnlogout.setOnClickListener(){
            auth = Firebase.auth
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    private fun updateProduct(){
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            val idProduct= extras.getString("idProduct")
            auth = Firebase.auth
            val currentUser = auth.currentUser
            val prodotto = Prodotto(nome.text.toString(),categoria.text.toString(),quantita.text.toString().toInt(),note.text.toString(),currentUser?.uid.toString(),currentUser?.displayName.toString())
            myRef.child(value.toString()).child("prodotti").child(idProduct.toString()).setValue(prodotto).addOnSuccessListener {
                val intent = Intent(this, ListOfProducts::class.java)
                intent.putExtra("key", value.toString())
                startActivity(intent)

            }
        }


    }
}
