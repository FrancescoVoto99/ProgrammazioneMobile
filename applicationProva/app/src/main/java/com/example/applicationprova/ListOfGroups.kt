package com.example.applicationprova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationprova.databinding.ActivityListOfGroupsBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ListOfGroups : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var searchgroups: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        val list = mutableListOf<String>()
        val list2 = mutableListOf<String>()
        super.onCreate(savedInstanceState)
        //data binding al posto del classico inflate
        val binding: ActivityListOfGroupsBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_list_of_groups)
        //setContentView(R.layout.activity_list_of_groups)

        var auth = Firebase.auth
        val currentUser = auth.currentUser


        database = FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        myRef = database.getReference("utentiGruppi")
        searchgroups= database.getReference("gruppi")


        val rv: RecyclerView = binding.listaGruppi
        rv.layoutManager = LinearLayoutManager(this)

        val divider = MaterialDividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL /*or LinearLayoutManager.HORIZONTAL*/)
        rv.addItemDecoration(divider)


        val child=currentUser?.email.toString().replace(".","")
        myRef.child(child).get().addOnSuccessListener {
            for (postSnapshot in it.children) {

                    list.add(postSnapshot.getValue().toString())
                    list2.add(postSnapshot.key.toString())

            }


                 rv.adapter = ListofGroupsAdapter(list,list2)
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

        rv.adapter = ListofGroupsAdapter(list,list2)
        //Action button
        val fab: View = binding.fab
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun fabOnClick() {
        val intent = Intent(this, Newgroup::class.java)
        startActivity(intent)
    }
}