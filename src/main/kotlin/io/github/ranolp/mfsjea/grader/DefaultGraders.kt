package io.github.ranolp.mfsjea.grader

/**
 * This class computes the sentence by the sentence's ascii character count.
 */
object AsciiGrader : SentenceGrader {
    override fun computeScore(sentence: String): Int =
        if (sentence.all { !it.isDigit() && it.toInt() < 128 }) -sentence.length * 10 else 0
}
