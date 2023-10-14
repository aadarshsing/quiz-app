package com.example.quiz.utils

import com.example.quiz.R


object IconPicker{
    val icons = arrayOf(
        R.drawable.icon1,
        R.drawable.icon2,
        R.drawable.icon3,
        R.drawable.icon5,
        R.drawable.icon6,
        R.drawable.icon7,
        R.drawable.icon8,
        R.drawable.icon9,
        R.drawable.icon10,
        R.drawable.icon11

    )
    var index = 0
    fun getIcon():Int{
        index = (index+1)% IconPicker.icons.size
        return IconPicker.icons[index]
    }
}