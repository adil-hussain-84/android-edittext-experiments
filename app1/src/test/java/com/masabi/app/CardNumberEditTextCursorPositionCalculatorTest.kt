package com.masabi.app

import com.masabi.app.CardNumberEditTextCursorPositionCalculator.calculateCursorPositionInFormattedText
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CardNumberEditTextCursorPositionCalculatorTest {

    @ParameterizedTest(name = "formattedText=\"{0}\"; originalTextUpToCursor=\"{1}\"")
    @MethodSource("argumentsForTest")
    fun calculateCursorPositionInFormattedText(formattedText: String, originalTextUpToCursor: String, expected: Int) {
        // When.
        val actual = calculateCursorPositionInFormattedText(formattedText, originalTextUpToCursor)

        // Then.
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun argumentsForTest(): Stream<Arguments> {
            return Stream.of(
                arguments("", "", 0),
                arguments("1234", "1", 1),
                arguments("1234", "1234", 4),
                arguments("1234", "1234 ", 4),
                arguments("1234", "12 34", 4),
                arguments("1234", "12 34 ", 4),
                arguments("1234", "12  34", 4),
                arguments("1234", "12  34  ", 4),
                arguments("1234 5678", "", 0),
                arguments("1234 5678", "1", 1),
                arguments("1234 5678", "1234", 4),
                arguments("1234 5678", "1234 ", 5),
                arguments("1234 5678", "12 34", 4),
                arguments("1234 5678", "12 34 ", 5),
                arguments("1234 5678", "12  34", 4),
                arguments("1234 5678", "12  34  ", 5),
                arguments("1234  5678", "", 0),
                arguments("1234  5678", "1", 1),
                arguments("1234  5678", "1234", 4),
                arguments("1234  5678", "1234 ", 5),
                arguments("1234  5678", "1234  ", 6),
                arguments("1234  5678", "12 34", 4),
                arguments("1234  5678", "12 34 ", 5),
                arguments("1234  5678", "12  34  ", 6),
                arguments("1234  5678", "12345", 7),
                arguments("1234  5678", "12 34 5", 7),
                arguments("1234  5678", "12  34  5", 7),
            )
        }
    }
}
