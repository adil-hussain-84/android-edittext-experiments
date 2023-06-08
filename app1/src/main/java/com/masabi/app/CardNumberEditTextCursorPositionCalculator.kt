package com.masabi.app

object CardNumberEditTextCursorPositionCalculator {

    /**
     * Calculates where the cursor should be placed within `formattedText` taking into account `originalTextUpToOldCursorPosition`.
     *
     * This function assumes that:
     * 1. The non-space characters in `originalTextUpToOldCursorPosition` are a subset of the non-space characters in `formattedText`.
     * 2. The non-space characters in `originalTextUpToOldCursorPosition` are in the same exact order as the non-space characters in `formattedText`.
     *
     * @param formattedText The formatted text which may have spaces in different places than the original (i.e. pre-formatted) text.
     * @param originalTextUpToOldCursorPosition The original (i.e. pre-formatted) text up to where the cursor was prior to formatting.
     */
    fun calculateCursorPositionInFormattedText(formattedText: String, originalTextUpToOldCursorPosition: String): Int {
        var indexInFormattedText = 0
        var indexInOriginalText = 0

        while (indexInFormattedText < formattedText.length && indexInOriginalText < originalTextUpToOldCursorPosition.length) {

            val characterInFormattedText = formattedText[indexInFormattedText]
            val characterInOriginalText = originalTextUpToOldCursorPosition[indexInOriginalText]

            if (characterInFormattedText == characterInOriginalText) {
                indexInFormattedText++
                indexInOriginalText++
            } else if (characterInFormattedText == ' ') {
                indexInFormattedText++
            } else if (characterInOriginalText == ' ') {
                indexInOriginalText++
            }
        }

        return indexInFormattedText;
    }
}