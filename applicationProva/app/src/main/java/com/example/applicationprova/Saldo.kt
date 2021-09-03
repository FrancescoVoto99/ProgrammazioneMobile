package com.example.applicationprova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.applicationprova.databinding.ActivitySaldoBinding
import com.example.applicationprova.databinding.ActivitySettingsGroupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class Saldo : AppCompatActivity() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
    var searchUser: DatabaseReference = database.getReference("gruppi")
    var myRefutenti: DatabaseReference = database.getReference("utentiGruppi")
    val listUtenti = ArrayList<String>()
    val listNomiUtenti = ArrayList<String>()
    val listspese = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivitySaldoBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_saldo
        )


        searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("nomeGruppo").get().addOnSuccessListener {
            binding.Nomegruppo.setText(it.value.toString())
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("gruppo").get().addOnSuccessListener {

                for (postSnapshot in it.children) {

                    listUtenti.add(postSnapshot.key.toString())
                    listNomiUtenti.add(postSnapshot.value.toString())
                    listspese.add(0.00f)

                }


        searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("spese").get().addOnSuccessListener {

        for(item in 0..(listUtenti.size-1)){
        for (postSnapshot in it.children) {
            if (postSnapshot.child("idutente").value.toString().equals(listUtenti[item])) {
                listspese[item] = listspese[item] + postSnapshot.child("totale").value.toString().toFloat()
            }
        }



            }


            binding.listaUtenti.isClickable = true
            binding.listaUtenti.adapter = SettingGroupAdapter(this, list)
    }
}