package com.example.applicationprova

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class Statistic : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        var piechart: PieChart = findViewById(R.id.piechart)
        val entries : ArrayList<PieEntry> = ArrayList()

        entries.add(PieEntry(0.2f,"prova1"))
        entries.add(PieEntry(0.2f,"prova2"))
        entries.add(PieEntry(0.2f,"prova3"))
        entries.add(PieEntry(0.2f,"prova4"))
        entries.add(PieEntry(0.2f,"prova5"))

        val colors :ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS){
            colors.add(color)

        }
        for (color in ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color)

        }

        val dataSet : PieDataSet = PieDataSet(entries,"Prodotti Acquistati")
        dataSet.setColors(colors)

        val data : PieData = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(piechart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        piechart.data= data

        piechart.setUsePercentValues(true)
        piechart.setEntryLabelTextSize(12f)
        piechart.setEntryLabelColor(Color.BLACK)
        piechart.setCenterText("Spese")
        piechart.setCenterTextSize(24f)
        piechart.description.isEnabled= false

        val l : Legend=piechart.legend
        l.verticalAlignment=Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment= Legend.LegendHorizontalAlignment.RIGHT
        l.orientation= Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled= true



    }
}