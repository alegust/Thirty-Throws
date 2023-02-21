package com.bignerdranch.android.thirtythrows

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

import kotlin.random.Random

private const val TAG = "MainActivity"
private const val RESULT_ARRAY = "com.bignerdranch.android.thirtythrows.RESULT_ARRAY"
class MainActivity : AppCompatActivity() {

    private lateinit var dice1: ImageButton
    private lateinit var dice2: ImageButton
    private lateinit var dice3: ImageButton
    private lateinit var dice4: ImageButton
    private lateinit var dice5: ImageButton
    private lateinit var dice6: ImageButton
    private lateinit var throwButton: Button
    private lateinit var okButton: Button
    private lateinit var submitButton: Button

    private var submitButtonChoiceIndex = 0
    private var diceClicked = IntArray(7) // If dice is selected on screen then the corresponding index has value 1. value 2 of index i indicates that the dice is used up for a score pairing
    private var diceValues = IntArray(7) // Actual value of every dice. First dice corresponds to index 1 etc...
    private var whiteDiceIds = IntArray(7) // All of these are of size 7 in order to make the mapping of dices easy
    private var redDiceIds = IntArray(7)
    private var greyDiceIds = IntArray(7)
    private var numberOfReThrows = 0
    private lateinit var spinner: Spinner
    private var choices = arrayListOf<String>("Low", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    private lateinit var currentChoice: String
    private  var currentChoiceIndex = 0
    private  var result = mutableListOf<Score>()

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         *
         * Han inte fixa med rotation och savestate då jag har suttit med en bug nu i flera timmar innan inlämning.
         *  Override:ade alla livscykelmetoder och kunde rotera emulatorn utan att det krashade.
         *  Sedan efter ett antal undo operationer så kan jag inte längre rotera utan att app:en krashar.
         *  Stacktrace:en är inte till hjälp då den säger att
         *  det är ett nullpointerException på "Uknown Source".
         *
         *  Märker även att jag kan rotera Result vyn utan problem när jag väl kommer dit efter ha submit:at allt.
         *  Detta borde väl inte gå då jag inte har skapat ett xml för landskapsvy för den aktiviteten?
         *  Vad fan är det som händer?????????????????????????????????
         *
         *  Hjälp skulle uppskattas då jag inte kan dela min kod med någon annan från kursen.
         *
         */




        dice1 = findViewById(R.id.dice1)
        dice2 = findViewById(R.id.dice2)
        dice3 = findViewById(R.id.dice3)
        dice4 = findViewById(R.id.dice4)
        dice5 = findViewById(R.id.dice5)
        dice6 = findViewById(R.id.dice6)
        throwButton = findViewById(R.id.throw_button)
        okButton = findViewById(R.id.ok_button)
        submitButton = findViewById(R.id.submit_button)
        spinner = findViewById(R.id.spinner)


        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, choices)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    currentChoice = choices[position]
                    currentChoiceIndex = position
                    submitButtonChoiceIndex = currentChoiceIndex // Fix later
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        setUpResult()
        whiteDiceIds[1] = R.drawable.white1
        whiteDiceIds[2] = R.drawable.white2
        whiteDiceIds[3] = R.drawable.white3
        whiteDiceIds[4] = R.drawable.white4
        whiteDiceIds[5] = R.drawable.white5
        whiteDiceIds[6] = R.drawable.white6

        redDiceIds[1] = R.drawable.red1
        redDiceIds[2] = R.drawable.red2
        redDiceIds[3] = R.drawable.red3
        redDiceIds[4] = R.drawable.red4
        redDiceIds[5] = R.drawable.red5
        redDiceIds[6] = R.drawable.red6

        greyDiceIds[1] = R.drawable.grey1
        greyDiceIds[2] = R.drawable.grey2
        greyDiceIds[3] = R.drawable.grey3
        greyDiceIds[4] = R.drawable.grey4
        greyDiceIds[5] = R.drawable.grey5
        greyDiceIds[6] = R.drawable.grey6

        initialThrow()

        dice1.setOnClickListener {
            if(diceClicked[1] != 2) {
                if(diceClicked[1] == 0){ diceClicked[1] = 1}
                else diceClicked[1] = 0
                setDice(1,diceValues[1])
            }

        }

        dice2.setOnClickListener {
            if(diceClicked[2] != 2) {
                if(diceClicked[2] == 0){ diceClicked[2] = 1}
                else diceClicked[2] = 0
                setDice(2,diceValues[2])
            }

        }

        dice3.setOnClickListener {
            if(diceClicked[3] != 2) {
                if(diceClicked[3] == 0){ diceClicked[3] = 1}
                else diceClicked[3] = 0
                setDice(3,diceValues[3])
            }

        }

        dice4.setOnClickListener {
           if(diceClicked[4] !=2) {
               if (diceClicked[4] == 0) {
                   diceClicked[4] = 1
               } else diceClicked[4] = 0
               setDice(4, diceValues[4])
           }

        }

        dice5.setOnClickListener {
            if(diceClicked[5] != 2) {
                if(diceClicked[5] == 0){ diceClicked[5] = 1}
                else diceClicked[5] = 0
                setDice(5,diceValues[5])
            }

        }

        dice6.setOnClickListener {
            if(diceClicked[6] != 2) {
                if(diceClicked[6] == 0){ diceClicked[6] = 1}
                else diceClicked[6] = 0
                setDice(6,diceValues[6])
            }


        }

        throwButton.setOnClickListener {
            reThrow()
        }

        okButton.setOnClickListener {
            var diceSum = 0
            if(nothingSelected()) {
                Toast.makeText(this, "Select some dice to sum according to the rule",Toast.LENGTH_LONG).show()
            }
            else if (choices[currentChoiceIndex] == "Low") {
                for (i in 1..6) {
                    if(diceClicked[i] == 1 && diceValues[i] < 4) {
                        diceSum += diceValues[i]
                        diceClicked[i] = 2
                        diceUsed(i,diceValues[i])
                        result[0].partialSums.add(diceValues[i])
                    }
                    else if(diceClicked[i] == 1 && diceValues[i] > 3) {
                        diceSum = 0
                        Toast.makeText(this,"Dice sum includes dice > 3, try again!",Toast.LENGTH_LONG).show()
                        unselectDice()
                    }
                }
            }

            else {

                for (i in 1..6) {
                    if(diceClicked[i] == 1) {
                        diceSum += diceValues[i]
                    }
                }
                if(diceSum != choices[currentChoiceIndex].toInt()) {
                    var currentChoice = choices[currentChoiceIndex].toInt()
                    diceSum = 0
                    Toast.makeText(this,"Dice sum is not equal to $currentChoice try again!",Toast.LENGTH_LONG).show()
                    unselectDice()
                }

                else {
                    for (i in 1..6) {
                        if(diceClicked[i] == 1) {
                            diceClicked[i] = 2
                            diceUsed(i,diceValues[i])
                            result[submitButtonChoiceIndex].partialSums.add(diceValues[i])
                        }
                    }
                }


            }

        }

        submitButton.setOnClickListener {
            if(choices.size == 0) {

              val intent = Intent(this, Result::class.java)

                intent.putExtra("Low", result[0].totalSum)
                intent.putExtra("4", result[1].totalSum)
                intent.putExtra("5", result[2].totalSum)
                intent.putExtra("7", result[4].totalSum)
                intent.putExtra("8", result[5].totalSum)
                intent.putExtra("6", result[3].totalSum)
                intent.putExtra("9", result[6].totalSum)
                intent.putExtra("10", result[7].totalSum)
                intent.putExtra("11", result[8].totalSum)
                intent.putExtra("12", result[9].totalSum)
                startActivity(intent)
            }
            else {
                result[submitButtonChoiceIndex].sumPartial()
                adapter.remove(currentChoice)
                adapter.notifyDataSetChanged()
                if(choices.size > 0) {
                    currentChoice = choices[currentChoiceIndex]
                    if(submitButtonChoiceIndex < 9) {
                        submitButtonChoiceIndex++
                    }
                    resetDice()
                    initialThrow()
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "test")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "test")

    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "test")

    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "test")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "test")
    }

    private fun setTotalSum() {
        result.forEach {
            it.sumPartial()

        }
    }


    private fun getPartialSum(pointChoice: Int): Int {
        result[pointChoice].sumPartial()
        var sum : Int = result[pointChoice].totalSum
        return sum
    }

    private fun resetDice() {
        for (i in 1..6) {
            diceClicked[i] = 0
        }
    }

    private fun nothingSelected(): Boolean {
        var bool = true
        for (i in 1..6) {
            if(diceClicked[i] == 1) {
                bool =false
            }

        }
        return bool
    }

    private fun unselectDice(){
        for (i in 1..6){
            if(diceClicked[i] != 2) {
                diceClicked[i] = 0
                setDice(i,diceValues[i])
            }
        }
    }

    private fun setUpResult() {
        for (i in 0..9) {

            var scoreInit = Score(choices[i], mutableListOf<Int>(0))
            result.add(scoreInit)
        }
    }





/**
 * This function handles the randomisation of the dices and can be used twice tops
 * */
    private fun reThrow(){
        var newDiceVal: Int
        if (numberOfReThrows < 2 && !nothingSelected()) {
            for (i in 1..6){
                if(diceClicked[i] == 1) {
                    diceClicked[i] = 0
                    newDiceVal = Random.nextInt(1,7)
                    setDice(i,newDiceVal)
                    diceValues[i] = newDiceVal

                }
            }
            numberOfReThrows++
        }
    }


    /**
     * randomizes the dices for the first throw
     *
     * */
    private fun initialThrow() {
       var newDiceVal: Int
        for (i in 1..6) {
            newDiceVal = Random.nextInt(1,7)
            setDice(i,newDiceVal)
            diceValues[i] = newDiceVal
        }
    }


    /**
     * This method is used to mark a dice as used or spent in a score pairing
     * by setting it to grey. Grey dice cant be interacted with by the user.
     * */

    private fun diceUsed(dice: Int, value: Int) {
        val greyValueId = greyDiceIds[value]
        when(dice) {
            1 -> if(diceClicked[1] == 2) {
                    dice1.setImageResource(greyValueId)
            }
            2 -> if(diceClicked[2] == 2) {
                dice2.setImageResource(greyValueId)
            }

            3 -> if(diceClicked[3] == 2) {
                dice3.setImageResource(greyValueId)
            }

            4 -> if(diceClicked[4] == 2) {
                dice4.setImageResource(greyValueId)
            }

            5 -> if(diceClicked[5] == 2) {
                dice5.setImageResource(greyValueId)
            }

            6 -> if(diceClicked[6] == 2) {
                dice6.setImageResource(greyValueId)
            }
        }
    }


    /**
     * This method is used to swap between white and red dices. Red dices are the dices selected by the user
     * */
    private fun setDice(dice: Int, value: Int){
       val whiteValueId = whiteDiceIds[value]
       val redValueId = redDiceIds[value]
        when(dice) {
            1 -> {
                if(diceClicked[1] == 0){
                    dice1.setImageResource(whiteValueId)

                }
                else {
                    dice1.setImageResource(redValueId)
                }
            }
            2 -> {
                if(diceClicked[2] == 0){
                    dice2.setImageResource(whiteValueId)

                }
                else {
                    dice2.setImageResource(redValueId)
                }
            }

            3 -> {
                if(diceClicked[3] == 0){
                    dice3.setImageResource(whiteValueId)
                }
                else {
                    dice3.setImageResource(redValueId)
                }
            }
            4 -> {
                if(diceClicked[4] == 0){
                    dice4.setImageResource(whiteValueId)
                }
                else {
                    dice4.setImageResource(redValueId)
                }

            }
            5 -> {
                if(diceClicked[5] == 0){
                    dice5.setImageResource(whiteValueId)

                }
                else {
                    dice5.setImageResource(redValueId)
                }

            }
            6 -> {
                if(diceClicked[6] == 0){
                    dice6.setImageResource(whiteValueId)
                }
                else {
                    dice6.setImageResource(redValueId)
                }
            }
       }
    }
}