package com.masabi.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private val cardNumberEditText: CardNumberEditText
        get() = findViewById(R.id.cardNumberEditText)

    private val showCardNumberButton: Button
        get() = findViewById(R.id.showCardNumberButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showCardNumberButton.setOnClickListener { showCardNumber() }
    }

    private fun showCardNumber() {
        val cardNumber = cardNumberEditText.cardNumber

        val message = if (cardNumber.isNotEmpty()) {
            "Your card number without spaces is $cardNumber."
        } else {
            "You have not entered a card number."
        }

        AlertDialog.Builder(this)
            .setTitle("Card Number")
            .setMessage(message)
            .setNeutralButton("Dismiss", null)
            .show()
    }
}