package com.masabi.app

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener

/**
 * A custom view for users to enter payment card numbers into.
 *
 * This view looks like an [EditText] but formats the text entered into it by the user
 * such that a space is shown after every fourth digit.
 */
class CardNumberEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val editText = EditText(context, attrs)

    /**
     * The digits entered into the view by the user
     * (i.e. without the spaces that the view may have added to itself automatically for display purposes).
     */
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

    /**
     * Formats the text in this view (represented by `editable`) so that a space is added after every fourth digit.
     */
    private fun afterTextChanged(editable: Editable?) {
        if (editable == null) {
            return
        }

        val text = editable.toString()

        val formattedText = text.replace(" ", "").chunked(4).joinToString(" ")
        val formattedTextLength = formattedText.length

        val selectionStart = editText.selectionStart

        if (text != formattedText) {
            editText.setText(formattedText)

            if (selectionStart > formattedTextLength) {
                // put the cursor at the end of the text to prevent an index out of bounds exception
                editText.setSelection(formattedTextLength)
            } else if (formattedText[selectionStart - 1] == ' ') {
                // move the cursor one position forward to accommodate for the space that's just been inserted by the 'editText.setText' call
                editText.setSelection(selectionStart + 1)
            } else {
                // keep the cursor where it was prior to the 'editText.setText' call
                editText.setSelection(selectionStart)
            }
        }
    }
}