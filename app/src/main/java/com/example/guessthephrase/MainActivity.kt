package com.example.guessthephrase

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var clMain : ConstraintLayout
    lateinit var userText : EditText
    lateinit var guessButton : Button
    lateinit var messages : ArrayList<String>
    lateinit var tvPhrase : TextView
    lateinit var tvLetters : TextView

    private val answer = "no pain no gain"
    private val myAnswerDictionary = mutableMapOf<Int, Char>()
    private var myAnswer = ""
    private var guessedLetters = ""
    private var count = 0
    private var guessPhrase = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for(i in answer.indices){
            if(answer[i] == ' '){
                myAnswerDictionary[i] = ' '
                myAnswer += ' '
            }else{
                myAnswerDictionary[i] = '*'
                myAnswer += '*'
            }
        }

        clMain = findViewById(R.id.clMain)
        messages = ArrayList()

        rvMessages.adapter = MessagesAdapter(this, messages)
        rvMessages.layoutManager = LinearLayoutManager(this)

        userText = findViewById(R.id.Messages)
        guessButton = findViewById(R.id.Guess)
        guessButton.setOnClickListener { addMessage() }

        tvPhrase = findViewById(R.id.tvPhrase)
        tvLetters = findViewById(R.id.tvLetters)

        updateText()
    }


    private fun addMessage() {
        val msg = userText.text.toString()

        if (guessPhrase) {
            if (msg == answer) {
                disableEntry()
                showAlertDialog("You win!\n\nPlay again?")
            } else {
                messages.add("Wrong guess: $msg")
                guessPhrase = false
                updateText()
            }
        } else {
            if (msg.isNotEmpty() && msg.length == 1) {
                myAnswer = ""
                guessPhrase = true
                checkLetters(msg[0])
            } else {
                Snackbar.make(clMain, "Please enter one letter only", Snackbar.LENGTH_LONG).show()
            }
        }

        userText.text.clear()
        userText.clearFocus()
        rvMessages.adapter?.notifyDataSetChanged()

    }


         fun disableEntry(){
            guessButton.isEnabled = false
            guessButton.isClickable = false
            userText.isEnabled = false
            userText.isClickable = false
        }

         fun updateText(){
            tvPhrase.text = "Phrase:  " + myAnswer.toUpperCase()
            tvLetters.text = "Guessed Letters:  " + guessedLetters
            if(guessPhrase){
                userText.hint = "Guess the full phrase"
            }else{
                userText.hint = "Guess a letter"
            }
        }

         fun checkLetters(guessedLetter: Char){
            var found = 0
            for(i in answer.indices){
                if(answer[i] == guessedLetter){
                    myAnswerDictionary[i] = guessedLetter
                    found++
                }
            }
            for(i in myAnswerDictionary){myAnswer += myAnswerDictionary[i.key]}
            if(myAnswer==answer){
                disableEntry()
                showAlertDialog("You win!\n\nPlay again?")
            }
            if(guessedLetters.isEmpty()){guessedLetters+=guessedLetter}else{guessedLetters+=", "+guessedLetter}
            if(found>0){
                messages.add("Found $found ${guessedLetter.toUpperCase()}(s)")
            }else{
                messages.add("No ${guessedLetter.toUpperCase()}s found")
            }
            count++
            val guessesLeft = 10 - count
            if(count<10){messages.add("$guessesLeft guesses remaining")}
            updateText()
            rvMessages.scrollToPosition(messages.size - 1)
        }

         fun showAlertDialog(title: String) {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage(title)
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> this.recreate()
                })
                // negative button text and action
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Game Over")
            // show alert dialog
            alert.show()
        }
    }
