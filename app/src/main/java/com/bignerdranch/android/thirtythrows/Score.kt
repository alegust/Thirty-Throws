package com.bignerdranch.android.thirtythrows

 class Score(
    val sumType: String,
    var partialSums: MutableList<Int>,
    var totalSum: Int = 0
    ) {


    private  var sum: Int = 0

     private fun test() {
         var testArray = {sumType}
     }


    fun sumPartial() {
        partialSums.forEach {
            totalSum += it
        }
    }


}