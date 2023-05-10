package com.masabi.app

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener

class CardNumberEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val editText = EditText(context, attrs)

    val cardNumber: String
        get() = editText.text.toString().replace(" ", "")

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        editText.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.minLines = 1
        editText.maxLines = 1

        editText.addTextChangedListener { afterTextChanged(it) }

        addView(editText)
    }

    private fun afterTextChanged(editable: Editable?) {
        if (editable == null) {
            return
        }

        val text = editable.toString()

        val formattedText = text.replace(" ", "").chunked(4).joinToString(" ")

        if (text != formattedText) {
            editText.setText(formattedText)
            editText.setSelection(editText.length())
        }
    }
}