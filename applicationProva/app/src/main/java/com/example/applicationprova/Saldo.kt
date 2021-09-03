package com.example.applicationprova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.applicationprova.databinding.ActivitySaldoBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class Saldo : AppCompatActivity() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
    var searchUser: DatabaseReference = database.getReference("gruppi")
    val listUtenti = ArrayList<String>()
    val listNomiUtenti = ArrayList<String>()
    val listspese = mutableListOf<Float>()

    val listspese2 = mutableMapOf<String,Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivitySaldoBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_saldo
        )


        searchUser.child("-MignrLG-d51pOLQA7Ob").child("nomeGruppo").get().addOnSuccessListener {
            binding.Nomegruppo.setText(it.value.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        searchUser.child("-MignrLG-d51pOLQA7Ob").child("gruppo").get().addOnSuccessListener {

                for (postSnapshot in it.children) {

                    listUtenti.add(postSnapshot.value.toString())

                    listspese2.put(postSnapshot.key.toString(),0.00f)

                    //listNomiUtenti.add(postSnapshot.value.toString())
                    //listspese.add(0.00f)

                }


        searchUser.child("-MignrLG-d51pOLQA7Ob").child("spese").get().addOnSuccessListener {

        for (postSnapshot in it.children) {
                val spesa : Float? =listspese2.get(postSnapshot.child("idutente").value.toString())
                if (spesa != null) {
                    listspese2[postSnapshot.child("idutente").value.toString()]=spesa + postSnapshot.child("totale").value.toString().toFloat()
                }
        }
            binding.listaSaldi.isClickable = true
            binding.listaSaldi.adapter = SaldoAdapter(this, listspese2,listUtenti)
            }



    }
}
}