package com.bignerdranch.android.thirtythrows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text

class Result : AppCompatActivity() {
    private var partialSums = intArrayOf()
    private lateinit var totalResultView: TextView
    private lateinit var lowResultView: TextView
    private lateinit var congratulationsView: TextView
    private lateinit var fourResultView: TextView
    private lateinit var fiveResultView: TextView
    private lateinit var sixResultView: TextView
    private lateinit var sevenResultView: TextView
    private lateinit var eightResultView: TextView
    private lateinit var nineResultView: TextView
    private lateinit var tenResultView: TextView
    private lateinit var elevenResultView: TextView
    private lateinit var twelveResultView: TextView


    private var totalResult = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        totalResult = sumPartialResults()
        congratulationsView = findViewById(R.id.congratulatory_text)
        totalResultView = findViewById(R.id.result_text)
        lowResultView = findViewById(R.id.low_result_text)
        lowResultView.setText("Low = " + partialResults[0].toString())
        fourResultView = findViewById(R.id.four_result_text)
        fourResultView.setText("4 =" + partialResults[1].toString())
        fiveResultView = findViewById(R.id.five_result_text)
        fiveResultView.setText("5 =" + partialResults[2].toString())
        sixResultView = findViewById(R.id.six_result_text)
        sixResultView.setText("6 =" + partialResults[3].toString())
        sevenResultView = findViewById(R.id.seven_result_text)
        sevenResultView.setText("7 = " + partialResults[4].toString())
        eightResultView = findViewById(R.id.eight_result_text)
        eightResultView.setText("8 = " + partialResults[5].toString())
        nineResultView = findViewById(R.id.nine_result_text)
        nineResultView.setText( "9 =" + partialResults[6].toString())
        tenResultView = findViewById(R.id.ten_result_text)
        tenResultView.setText("10 ="+partialResults[7].toString())
        elevenResultView = findViewById(R.id.eleven_result_text)
        elevenResultView.setText("11 =" + partialResults[8].toString())
        twelveResultView = findViewById(R.id.twelve_result_text)
        twelveResultView.setText("12 ="+partialResults[9].toString())
        congratulationsView.setText("Well played here are your results:")
        totalResultView.setText("Your total result is were $totalResult")

    //totalResultView.setText(intent.getIntExtra(sumPartialResults(),1337).toString())
        // val temp = intent.getIntegerArrayListExtra()

    }

    private var partialResults = arrayListOf<Int>()
    private var finalResult = 0


    private fun sumPartialResults(): Int{
        var totalSum = intent.getIntExtra("Low",0)
        partialResults.add(totalSum)
        var temp = 0
        for (i in 4..12) {
            temp = intent.getIntExtra(i.toString(),0)
            partialResults.add(temp)
            totalSum += temp
        }

        return totalSum
    }

    private fun setPartialResultView(){
        lowResultView.setText(intent.getIntExtra("Low",0).toString())
        for (i in 4..12) {

        }
    }


}
