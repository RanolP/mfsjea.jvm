package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.classifier.Classifier
import io.github.ranolp.mfsjea.classifier.NopClassifier
import io.github.ranolp.mfsjea.grader.*
import io.github.ranolp.mfsjea.keyboard.*
import java.lang.StringBuilder

/**
 * Universal sentence converter. it converts sentences via keyboard set, and score it. then returns best conversion result.
 */
class Mfsjea(
    private val inputKeyboards: List<InputKeyboard>,
    private val outputKeyboards: List<OutputKeyboard>,
    private val graders: List<SentenceGrader>,
    private val classifier: Classifier
) {

    init {
        if (inputKeyboards.isEmpty() || outputKeyboards.isEmpty()) {
            // todo: throw own exception, we must have one or more in/output keyboards!
        }
    }

    private fun convert(
        source: String,
        inputKeyboard: InputKeyboard,
        outputKeyboard: OutputKeyboard
    ): ConversionResult {
        val result = StringBuilder()
        var totalConverted = 0
        var totalScore = 0

        val originalBuffer = StringBuilder()
        val convertedBuffer = StringBuilder()

        fun flushWord() {
            if (convertedBuffer.isNotEmpty()) {
                val convertedPart = outputKeyboard.combinator.combine(convertedBuffer.toString())
                val score = graders.sumBy { it.computeScore(convertedPart) }
                if (score >= 0) {
                    totalScore += score
                    totalConverted += originalBuffer.length
                    result.append(convertedPart)
                } else {
                    result.append(originalBuffer)
                }
                originalBuffer.clear()
                convertedBuffer.clear()
            }
        }

        for (ch in source) {
            val code = inputKeyboard.getKeycode(ch)
            if (code != null) {
                originalBuffer.append(ch)
                convertedBuffer.append(outputKeyboard.getCharacter(code))
            } else {
                flushWord()
                result.append(ch)
            }
        }
        flushWord()

        return ConversionResult(inputKeyboard, outputKeyboard, result.toString(), totalConverted, totalScore)
    }

    /**
     * Convert the sentences via keyboards of this instance.
     *
     * @param sentence the target sentence
     * @return the list of result. it sorted by descending score.
     */
    fun jeamfsList(sentence: String): List<ConversionResult> {
        val results = inputKeyboards.flatMap { inputKeyboard ->
            outputKeyboards.map { outputKeyboard ->
                classifier.classify(sentence).asSequence().map { part ->
                    val origin = when (part) {
                        is Classifier.Part.Escaped -> part.origin
                        is Classifier.Part.Normal -> part.origin
                    }
                    listOfNotNull(
                        when (part) {
                            is Classifier.Part.Escaped -> ConversionResult(inputKeyboard, outputKeyboard, origin, 0, 0)
                            is Classifier.Part.Normal -> convert(part.origin, inputKeyboard, outputKeyboard)
                        }
                    ).maxBy { it.score }!!
                }.fold(ConversionResult(inputKeyboard, outputKeyboard, "", 0, 0)) { acc, curr ->
                    ConversionResult(
                        inputKeyboard,
                        outputKeyboard,
                        acc.sentence + curr.sentence,
                        acc.convertedCount + acc.convertedCount,
                        acc.score + curr.score
                    )
                }
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

    /**
     * Extends the mfsjea instance.
     */
    fun extend(
        inputKeyboards: (List<InputKeyboard>) -> List<InputKeyboard> = { it },
        outputKeyboards: (List<OutputKeyboard>) -> List<OutputKeyboard> = { it },
        graders: (List<SentenceGrader>) -> List<SentenceGrader> = { it },
        classifier: (Classifier) -> Classifier = { it }
    ): Mfsjea = Mfsjea(
        inputKeyboards(this.inputKeyboards),
        outputKeyboards(this.outputKeyboards),
        graders(this.graders),
        classifier(this.classifier)
    )

    companion object {
        /**
         * Default mfsjea instance.
         */
        @JvmField
        val DEFAULT: Mfsjea = Mfsjea(
            listOf(QwertyKeyboard, DvorakKeyboard, ColemakKeyboard),
            listOf(DubeolStandardKeyboard, Sebeol390Keyboard, SebeolFinalKeyboard),
            listOf(
                Hangul2350Grader,
                HangulFrequencyGrader,
                NumberGrader,
                ParenthesisGrader,
                IncompleteWordGrader,
                AsciiGrader
            ),
            NopClassifier
        )
    }
}
