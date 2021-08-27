package com.example.applicationprova

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfProductsBinding
import com.example.applicationprova.databinding.ActivitySettingsGroupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class SettingsGroup : AppCompatActivity() {

    var database: FirebaseDatabase= FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
    var searchUser: DatabaseReference = database.getReference("gruppi")
    val list = ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.UserGroup("-MhxU2gVL0ZZrvfrYOsN")

        val binding: ActivitySettingsGroupBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_settings_group
        )

        binding.listaUtenti.isClickable = true
        binding.listaUtenti.adapter = SettingGroupAdapter(this, list)

        binding.listaUtenti.setOnItemClickListener { parent, view, position, id ->

            val email = list[position]
            Snackbar.make(parent.rootView, "Click!" + email, Snackbar.LENGTH_SHORT).show()
            //val intent

        }

        val fab: View = binding.fabNewUser
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun fabOnClick() {
        val builder: AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setTitle("Inserisci un Nuovo Componente")

        val input = EditText(this)

        input.setHint("Email")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)


        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            var email = input.text.toString()

            searchUser.child("-MhxU2gVL0ZZrvfrYOsN").child("gruppo").push().setValue(email).addOnSuccessListener {


            }

        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
//
    }

      fun UserGroup(gruppo:String){

         val nomeGruppo= findViewById<TextView>(R.id.Nomegruppo)

         searchUser.child(gruppo).child("nomeGruppo").get().addOnSuccessListener {
             nomeGruppo.setText(it.value.toString())
         }.addOnFailureListener{
             Log.e("firebase", "Error getting data", it)
         }

         searchUser.child(gruppo).child("gruppo").get().addOnSuccessListener {
             for (postSnapshot in it.children) {

                 list.add(postSnapshot.getValue().toString())

             }
         }.addOnFailureListener{
             Log.e("firebase", "Error getting data", it)
         }

    }


}