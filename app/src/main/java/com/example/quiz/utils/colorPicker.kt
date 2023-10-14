package com.example.quiz.utils

object ColorPicker{
    val colors = arrayOf(
        "#c6aa63"
        ,"#b1c663",
        "#c67863",
        "#c663ac",
        "#c6637a",
        "#af63c6",
        "#c66365",
        "#c66396",
        "#baa3a4",
        "#d3a981",
        "#bad381",
        "#ff9933",
        "#ffcc33",
        "#d6995c"
    )
    var index = 0
    fun getColor():String{
        index = (index+1) % colors.size
        return colors[index]
    }
}