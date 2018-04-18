package io.github.ranolp.mfsjea.grader

interface SentenceGrader {
    fun computeScore(sentence: String): Int
}
