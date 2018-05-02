package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.grader.*
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
                val sets = mutableListOf<CharacterSet>()
                val originals = StringBuilder()
                val changes = mutableListOf<Pair<Char, Char>>()
                // true if changed
                var mode = false

                fun updateOriginal() {
                    sets += CharacterSet(originals.toString())
                    originals.setLength(0)
                }

                fun updateChanged() {
                    val origin = changes.map { it.first }.joinToString("")
                    val change = output.combinator.combine(changes.map { it.second }.joinToString(""))
                    val score = graders.sumBy { it.computeScore(change) }
                    sets += if (score < 0) {
                        CharacterSet(origin)
                    } else {
                        CharacterSet(origin, change, score)
                    }
                    changes.clear()
                }

                for (ch in sentence) {
                    val got = input.getKeycode(ch)
                    if (got === null) {
                        if (mode) {
                            mode = false
                            updateChanged()
                        }
                        originals.append(ch)
                    } else {
                        if (!mode) {
                            mode = true
                            updateOriginal()
                        }
                        changes += ch to output.getCharacter(got)
                    }
                }

                if (mode) {
                    updateChanged()
                } else {
                    updateOriginal()
                }

                val result = sets.joinToString("") {
                    if (it.state == CharacterSet.State.CHANGED) {
                        output.combinator.combine(it.changed)
                    } else {
                        it.changed
                    }
                }
                results += ConversionResult(
                    input,
                    output,
                    result,
                    Hangul2350Grader.computeScore(result),
                    sets.sumBy { it.score }
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
        @JvmField
        val DEFAULT: Mfsjea = Mfsjea(
            listOf(QwertyKeyboard, DvorakKeyboard, ColemakKeyboard),
            listOf(DubeolStandardKeyboard, Sebeol390Keyboard, SebeolFinalKeyboard),
            listOf(Hangul2350Grader, NumberGrader, ParenthesisGrader, IncompleteWordGrader)
        )
    }
}
