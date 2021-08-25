package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.applicationprova.databinding.ActivityInserisciProdottoBinding
import com.example.progetto.Prodotto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class InserisciProdotto: AppCompatActivity() {
    lateinit var nome : EditText
    lateinit var categoria: EditText
    lateinit var quantita: EditText
    lateinit var note: EditText

    var productid: Long=0

   lateinit var database: FirebaseDatabase
   lateinit var myRef: DatabaseReference

   private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityInserisciProdottoBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_inserisci_prodotto)
        //setContentView(R.layout.activity_inserisci_prodotto)

       nome=binding.Nomeprodotto
      categoria=binding.spinnercategory
       quantita=binding.spinnerquantity
       note=binding.note



     database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("gruppi")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                if (dataSnapshot.exists())
                    productid= dataSnapshot.childrenCount.toLong()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("prova", "Failed to read value.", error.toException())
            }
        })


        binding.btninsert.setOnClickListener{

           insertProduct()
        }
        binding.btnlogout.setOnClickListener(){
            auth = Firebase.auth
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
   }

    private fun insertProduct(){
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val prodotto = Prodotto(nome.text.toString(),categoria.text.toString(),quantita.text.toString().toInt(),note.text.toString(),currentUser?.uid.toString(),currentUser?.displayName.toString())
        myRef.child(value.toString()).child("prodotti").push().setValue(prodotto).addOnSuccessListener {
            val intent = Intent(this, ListOfProducts::class.java)
            startActivity(intent)

        }
        }


    }
}
