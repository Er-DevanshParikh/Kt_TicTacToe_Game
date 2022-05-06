package com.example.kt_tictactoe_game

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Game : AppCompatActivity() {
    //Declarations
    //3 x 3 dimension array
    var sign_buttons = Array(3) { arrayOfNulls<Button>(3) }

    // set Default Player 1 as True
    private var Player1 = true

    //round counter
    private var Count = 0

    //for player 1 and Player 2 point textview
    private var textView_Player1: TextView? = null
    private var textView_Player2: TextView? = null

    //point counter
    private var Player1Points = 0
    private var Player2Points = 0

    //OnCrate Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        textView_Player1 = findViewById(R.id.text_view_p1)
        textView_Player2 = findViewById(R.id.text_view_p2)
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "Button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                sign_buttons[i][j] = findViewById(resID)
                sign_buttons[i][j]?.setOnClickListener(View.OnClickListener { v: View -> onClick(v) })
            }
        }
        // For Reset The Button
        val buttonReset = findViewById<Button>(R.id.Button_reset)
        buttonReset.setOnClickListener { //go to reset function.
            resetGame()
        }
        val button_back = findViewById<Button>(R.id.Button_Back)
        button_back.setOnClickListener { //go to reset function.
            val i = Intent(this@Game, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    fun onClick(v: View) {
        if ((v as Button).text.toString() != "") {
            return
        }
        if (Player1) {
            v.text = "X"
        } else {
            v.text = "O"
        }
        Count++
        if (checkForWin()) {
            if (Player1) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (Count == 9) {
            draw()
        } else {
            Player1 = !Player1
        }
    }

    private fun checkForWin(): Boolean {
        val imgbox = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                imgbox[i][j] = sign_buttons[i][j]!!.text.toString()
            }
        }
        //1st condition
        if (imgbox[0][2] == imgbox[1][1] && imgbox[0][2] == imgbox[2][0] && imgbox[0][2] != "") {
            return true
        }

        //2nd condition
        for (i in 0..2) {
            if (imgbox[i][0] == imgbox[i][1] && imgbox[i][0] == imgbox[i][2] && imgbox[i][0] != "") {
                return true
            }
        }
        //3rd condition
        for (i in 0..2) {
            if (imgbox[0][i] == imgbox[1][i] && imgbox[0][i] == imgbox[2][i] && imgbox[0][i] != "") {
                return true
            }
        }

        //4th condition
        return imgbox[0][0] == imgbox[1][1] && imgbox[0][0] == imgbox[2][2] && imgbox[0][0] != ""


        // if condition false
    }

    // For Player 1 Win
    private fun player1Wins() {
        Player1Points++
        Toast.makeText(this, "Player is 1 Win The Game", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    // For Player 2 Win
    private fun player2Wins() {
        Player2Points++
        Toast.makeText(this, "Player 2 Win the Game!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    // Player 1 & Player 2 both are Win
    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    @SuppressLint("SetTextI18n")
    private fun updatePointsText() {
        textView_Player1!!.text = "Player 1: $Player1Points"
        textView_Player2!!.text = "Player 2: $Player2Points"
    }

    // reset all button values empty
    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                sign_buttons[i][j]!!.text = ""
            }
        }
        // Reset Counter
        Count = 0
        //By Default change Given to 1st Player
        Player1 = true
    }

    // Reset Points
    private fun resetGame() {
        Player1Points = 0
        Player2Points = 0
        updatePointsText()
        resetBoard()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("roundCount", Count)
        outState.putInt("Player 1:", Player1Points)
        outState.putInt("Player 2:", Player2Points)
        outState.putBoolean("Again Start With Player 1 Turn", Player1)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Count = savedInstanceState.getInt("Count")
        Player1Points = savedInstanceState.getInt("Player1Points")
        Player2Points = savedInstanceState.getInt("Player2Points")
        Player1 = savedInstanceState.getBoolean("Player1")
    }

}