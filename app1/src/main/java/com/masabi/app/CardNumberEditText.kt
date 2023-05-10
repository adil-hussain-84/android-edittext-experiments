package com.masabi.app

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener

class CardNumberEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        addTextChangedListener {
            if (it == null) {
                return@addTextChangedListener
            }

            val text = it.toString()
            val formattedText = text.replace(" ", "").chunked(4).joinToString(" ")

            if (text != formattedText) {
                setText(formattedText)
                setSelection(length())
            }
        }
    }

    val cardNumber: String
        get() = text.toString().replace(" ", "")
}