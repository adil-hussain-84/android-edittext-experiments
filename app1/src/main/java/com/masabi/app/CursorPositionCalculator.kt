package com.masabi.app

object CursorPositionCalculator {

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