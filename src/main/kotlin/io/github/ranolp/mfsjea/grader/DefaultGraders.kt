package io.github.ranolp.mfsjea.grader


object AsciiGrader : SentenceGrader {
    override fun computeScore(sentence: String): Int =
        if (sentence.all { !it.isDigit() && it.toInt() < 128 }) -sentence.length * 10 else 0
}
