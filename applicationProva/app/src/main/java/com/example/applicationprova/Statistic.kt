package com.example.applicationprova

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.FirebaseDatabase


class Statistic : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        val database =
            FirebaseDatabase.getInstance("https://prova-14ff5-default-rtdb.europe-west1.firebasedatabase.app/")
        val searchproducts = database.getReference("gruppi")
        val categorie = listOf<String>("Cibo", "Bagno", "Casa", "Salute", "Divertimento", "Altro")
        val quantita = mutableListOf<Int>(0, 0, 0, 0, 0, 0)


        var piechart: PieChart = findViewById(R.id.piechart)
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()

        val intent = Intent(this, ListOfProductCategory::class.java)

        val extras = intent.extras
        if (extras == null) {
            //val value = extras.getString("key")

            searchproducts.child("-MignrLG-d51pOLQA7Ob").child("prodotti").get().addOnSuccessListener {
                for (item in 0..(categorie.size-1)) {
                    for (postSnapshot in it.children) {
                        if ((postSnapshot.child("buy").getValue()
                                .toString()).equals("1") && (postSnapshot.child("categoria")
                                .getValue().toString()).equals(categorie[item])
                        ) {
                            quantita[item] =
                                    (quantita[item] + (postSnapshot.child("quantita").value
                                    .toString().toInt()))
                            print("proviamo " + quantita[item])

                        }
                    }}



                piechart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry, h: Highlight) {
                        intent.putExtra("key", "-MignrLG-d51pOLQA7Ob")
                        intent.putExtra("categoria", e.data.toString())
                        startActivity(intent)
                        val y = e.y
                        Log.d("prova",y.toString()+"  "+ e.data)
                    }

                    override fun onNothingSelected() {}
                })

                    for (item in 0..(categorie.size-1)) {
                        if(quantita[item]!=0){
                            val norma=quantita[item] / quantita.sum().toFloat()
                        entries.add(PieEntry(norma, categorie[item],categorie[item]))

                    }}


                insertcolors(colors)
                createPieChart(entries, piechart, colors)
                createLegend(piechart)




            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
                //  rv.adapter = ListofProductAdapter(list, list2)
            }













        }
    }

    fun insertcolors(colors: ArrayList<Int>){

        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)

        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)

        }

    }
    fun createPieChart(entries: ArrayList<PieEntry>, piechart: PieChart, colors: ArrayList<Int>){
        val dataSet: PieDataSet = PieDataSet(entries, "Prodotti Acquistati")
        dataSet.setColors(colors)

        val data: PieData = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(piechart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        piechart.data = data

        piechart.setUsePercentValues(true)
        piechart.setEntryLabelTextSize(12f)
        piechart.setEntryLabelColor(Color.BLACK)
        piechart.setCenterText("Spese")
        piechart.setCenterTextSize(24f)
        piechart.description.isEnabled = false


    }
    fun createLegend(piechart: PieChart){
        val l: Legend = piechart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true

    }
}