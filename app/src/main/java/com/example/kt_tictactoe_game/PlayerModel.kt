package com.example.kt_tictactoe_game

import java.util.*

data class PlayerModel(
    var pid: Int = getAutoId(),
    var name:String ="",
    var address:String=""
) {
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}