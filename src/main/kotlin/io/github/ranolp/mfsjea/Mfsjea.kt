package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.grader.Hangul2350Grader
import io.github.ranolp.mfsjea.grader.NumberGrader
import io.github.ranolp.mfsjea.grader.ParenthesisGrader
import io.github.ranolp.mfsjea.keyboard.*

class Mfsjea(private val inputKeyboards: List<InputKeyboard>, private val outputKeyboards: List<OutputKeyboard>) {

    init {
        if (inputKeyboards.isEmpty() || outputKeyboards.isEmpty()) {
            // todo: throw own exception, we must have one or more in/output keyboards!
        }
    }

    fun jeamfsList(sentence: String): List<ConversionResult> {
        val results = mutableListOf<ConversionResult>()
        inputKeyboards.forEach { input ->
            outputKeyboards.forEach { output ->
                val result = output.combinator.combine(
                    sentence.map { input.getKeycode(it) }.map { output.getCharacter(it) }.joinToString(
                        ""
                    )
                )
                results += ConversionResult(
                    input,
                    output,
                    result,
                    Hangul2350Grader.computeScore(result),
                    Hangul2350Grader.computeScore(result) +
                            NumberGrader.computeScore(result) +
                            ParenthesisGrader.computeScore(result) * 10
                )
            }
        }
        return results.sortedByDescending { it.score }
    }

    fun jeamfsAuto(sentence: String): ConversionResult = jeamfsList(sentence).first()

    companion object {
        val DEFAULT: Mfsjea = Mfsjea(listOf(QwertyKeyboard, DvorakKeyboard, ColemakKeyboard), listOf(DubeolStandardKeyboard, Sebeol390Keyboard, SebeolFinalKeyboard))
    }
}
