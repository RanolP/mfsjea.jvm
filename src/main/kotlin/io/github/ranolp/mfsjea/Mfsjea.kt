package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.escaper.SentenceEscaper
import io.github.ranolp.mfsjea.grader.*
import io.github.ranolp.mfsjea.keyboard.*

/**
 * Universal sentence converter. it converts sentences via keyboard set, and score it. then returns best conversion result.
 */
class Mfsjea(
    private val inputKeyboards: List<InputKeyboard>,
    private val outputKeyboards: List<OutputKeyboard>,
    private val graders: List<SentenceGrader>,
    private val escapers: List<SentenceEscaper>,
    private val byWord: Boolean
) {

    init {
        if (inputKeyboards.isEmpty() || outputKeyboards.isEmpty()) {
            // todo: throw own exception, we must have one or more in/output keyboards!
        }
    }

    fun convert(sentence: String, inputKeyboard: InputKeyboard, outputKeyboard: OutputKeyboard) : ConversionResult {
        val sets = mutableListOf<CharacterSet>()
        val originals = StringBuilder()
        val changes = mutableListOf<Pair<Char, Char>>()
        // true if changed
        var mode = false
        // true if started
        var escapeMode = false

        var escaper: SentenceEscaper? = null

        fun updateOriginal() {
            sets += CharacterSet(originals.toString())
            originals.setLength(0)
        }

        fun updateChanged() {
            val origin = changes.map { it.first }.joinToString("")
            val change = outputKeyboard.combinator.combine(changes.map { it.second }.joinToString(""))
            val score = graders.sumBy { it.computeScore(change) }
            sets += if (score < 0) {
                CharacterSet(origin)
            } else {
                CharacterSet(origin, change, score)
            }
            changes.clear()
        }

        fun update() {
            if (mode) {
                updateChanged()
            } else {
                updateOriginal()
            }
        }

        for (ch in sentence) {
            if (!escapeMode) {
                escaper = escapers.firstOrNull { it.check(ch) == SentenceEscaper.EscapeMode.START }
                if (escaper !== null) {
                    update()
                    escapeMode = true
                    mode = false
                    continue
                }
            } else if (escapeMode) {
                if (escaper?.check(ch) == SentenceEscaper.EscapeMode.END) {
                    escapeMode = false
                } else {
                    originals.append(ch)
                }
                continue
            }
            val got = inputKeyboard.getKeycode(ch)
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
                changes += ch to outputKeyboard.getCharacter(got)
            }
        }

        update()

        val result = sets.joinToString("") {
            if (it.state == CharacterSet.State.CHANGED) {
                outputKeyboard.combinator.combine(it.changed)
            } else {
                it.changed
            }
        }
        return ConversionResult(
                inputKeyboard,
                outputKeyboard,
                result,
                Hangul2350Grader.computeScore(result),
                sets.sumBy { it.score }
        )
    }

    /**
     * Convert the sentences via keyboards of this instance.
     *
     * @param sentence the target sentence
     * @return the list of result. it sorted by descending score.
     */
    fun jeamfsList(sentence: String): List<ConversionResult> {
        if(this.byWord) {
            val words = sentence.split(' ')
            val results = inputKeyboards.flatMap { inputKeyboard ->
                outputKeyboards.map { outputKeyboard ->
                    val result = words.map { word ->
                        val converted = convert(word, inputKeyboard, outputKeyboard)
                        if(converted.score <= 0) word else converted
                    }
                    val convertedWords = result.filterIsInstance<ConversionResult>()
                    val resultSentence = result.map { word -> if(word is ConversionResult) word.sentence else word }.joinToString(" ")
                    ConversionResult(
                            inputKeyboard,
                            outputKeyboard,
                            resultSentence,
                            convertedWords.sumBy { it.convertedCount },
                            convertedWords.sumBy { it.score }
                    )
                }
            }
            return results.sortedByDescending { it.score }
        } else {
            val results = inputKeyboards.flatMap { inputKeyboard ->
                outputKeyboards.map { outputKeyboard ->
                    convert(sentence, inputKeyboard, outputKeyboard)
                }
            }
            return results.sortedByDescending { it.score }
        }
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
        escapers: (List<SentenceEscaper>) -> List<SentenceEscaper> = { it },
        byWord: Boolean = false
    ): Mfsjea = Mfsjea(
        inputKeyboards(this.inputKeyboards),
        outputKeyboards(this.outputKeyboards),
        graders(this.graders),
        escapers(this.escapers),
        byWord
    )

    companion object {
        /**
         * Default mfsjea instance.
         */
        @JvmField
        val DEFAULT: Mfsjea = Mfsjea(
            listOf(QwertyKeyboard, DvorakKeyboard, ColemakKeyboard),
            listOf(DubeolStandardKeyboard, Sebeol390Keyboard, SebeolFinalKeyboard),
            listOf(Hangul2350Grader, HangulFrequencyGrader, NumberGrader, ParenthesisGrader, IncompleteWordGrader, AsciiGrader),
            emptyList(),
            false
        )
    }
}
