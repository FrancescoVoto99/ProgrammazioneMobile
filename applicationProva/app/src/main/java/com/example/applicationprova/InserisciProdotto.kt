package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.progetto.Prodotto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InserisciProdotto: AppCompatActivity() {
    lateinit var nome : EditText
    lateinit var categoria: EditText
    lateinit var quantita: EditText
    lateinit var note: EditText
    lateinit var btnInsert: Button
    lateinit var btnLogout: Button

    var productid: Long=0

   lateinit var database: FirebaseDatabase
   lateinit var myRef: DatabaseReference

   private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserisci_prodotto)

       nome=findViewById<EditText>(R.id.Nomeprodotto)
      categoria=findViewById<EditText>(R.id.spinnercategory)
       quantita=findViewById<EditText>(R.id.spinnerquantity)
       note=findViewById<EditText>(R.id.note)
      btnInsert=findViewById<Button>(R.id.btninsert)
        btnLogout=findViewById<Button>(R.id.btnlogout)



     database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("prodotti")

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


        btnInsert.setOnClickListener{

           insertProduct()
        }
        btnLogout.setOnClickListener(){
            auth = Firebase.auth
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
   }

    private fun insertProduct(){
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val prodotto = Prodotto(nome.text.toString(),categoria.text.toString(),quantita.text.toString().toInt(),note.text.toString(),currentUser?.uid.toString(),currentUser?.displayName.toString())
        myRef.child((productid+1).toString()).setValue(prodotto).addOnSuccessListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}
