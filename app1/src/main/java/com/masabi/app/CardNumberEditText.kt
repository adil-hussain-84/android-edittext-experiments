package com.masabi.app

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.masabi.app.CursorPositionCalculator.calculateCursorPositionInFormattedText

/**
 * A custom view for users to enter payment card numbers into.
 *
 * This view looks like an [EditText] but formats the text entered into it by the user
 * such that a space is shown after every fourth digit
 * and the input is limited to a certain number of digits.
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

        // some card numbers can be up to 19 digits but let's gloss over that as an unimportant implementation detail
        editText.filters = arrayOf(LengthFilter(16))

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
        val textWithoutSpaces = text.replace(" ", "")

        val formattedText = formatCardNumber(textWithoutSpaces)

        if (text == formattedText) {
            // nothing do; keep the text as it is
            return
        }

        val cursorPositionBefore = editText.selectionStart

        editText.setText(formattedText)

        val originalTextUpToCursor = text.take(cursorPositionBefore)

        val cursorPositionAfter = calculateCursorPositionInFormattedText(formattedText, originalTextUpToCursor)

        editText.setSelection(cursorPositionAfter)
    }

    /**
     * Formats `cardNumber` such that a space is inserted after every fourth digit.
     *
     * Formatting a card number is actually more complicated than inserting a space after every fourth digit.
     * This function glosses over this detail because that's not the focus of this [CardNumberEditText] class.
     * The focus of this [CardNumberEditText] class is the [afterTextChanged] function
     * and how to adjust the cursor in the [EditText] whenever a space is added or removed by the format function.
     */
    private fun formatCardNumber(cardNumber: String): String {
        return cardNumber.replace(" ", "").chunked(4).joinToString(" ")
    }

    /**
     * This filter will constrain edits so that the length of the text in the [EditText] never exceeds the specified length.
     *
     * It excludes spaces from the length count such that "1234 5678" is treated as 8 characters and not 9 characters.
     *
     * @property maxLength The maximum number of characters to allow excluding spaces.
     */
    private class LengthFilter(private val maxLength: Int) : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val charactersToKeep = dest.substring(0, dstart) + dest.substring(dend, dest.length)
            val charactersToAdd = source.substring(start, end)

            val numberOfDigitsInCharactersToKeep = charactersToKeep.replace(" ", "").length
            val numberOfDigitsInCharactersToAdd = charactersToAdd.replace(" ", "").length

            val numberOfDigitsIfChangeIsAccepted = numberOfDigitsInCharactersToKeep + numberOfDigitsInCharactersToAdd

            return if (numberOfDigitsIfChangeIsAccepted <= maxLength) {
                null // accept the change
            } else {
                return dest.subSequence(dstart, dend) // reject the change; keep whatever characters were there already
            }
        }
    }
}