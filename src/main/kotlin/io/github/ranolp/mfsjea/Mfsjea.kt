package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.grader.Hangul2350Grader
import io.github.ranolp.mfsjea.grader.NumberGrader
import io.github.ranolp.mfsjea.grader.ParenthesisGrader
import io.github.ranolp.mfsjea.grader.SentenceGrader
import io.github.ranolp.mfsjea.keyboard.*

/**
 * Universal sentence converter. it converts sentences via keyboard set, and score it. then returns best conversion result.
 */
class Mfsjea(
    private val inputKeyboards: List<InputKeyboard>,
    private val outputKeyboards: List<OutputKeyboard>,
    private val graders: List<SentenceGrader>
) {

    init {
        if (inputKeyboards.isEmpty() || outputKeyboards.isEmpty()) {
            // todo: throw own exception, we must have one or more in/output keyboards!
        }
    }

    /**
     * Convert the sentences via keyboards of this instance.
     *
     * @param sentence the target sentence
     * @return the list of result. it sorted by descending score.
     */
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
                    graders.sumBy { it.computeScore(result) }
                )
            }
        }
        return results.sortedByDescending { it.score }
    }

    /**
     * Find the best converted sentence by score.
     *
     * @param sentence the target sentence
     * @return best result
     */
    fun jeamfsAuto(sentence: String): ConversionResult = jeamfsList(sentence).first()

    companion object {
        /**
         * Default mfsjea instance.
         */
        @JvmStatic
        val DEFAULT: Mfsjea = Mfsjea(
            listOf(QwertyKeyboard, DvorakKeyboard, ColemakKeyboard),
            listOf(DubeolStandardKeyboard, Sebeol390Keyboard, SebeolFinalKeyboard),
            listOf(Hangul2350Grader, NumberGrader, ParenthesisGrader)
        )
    }
}
