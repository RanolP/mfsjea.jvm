package io.github.ranolp.mfsjea.grader

/**
 * This class computes the score from sentence.
 */
interface SentenceGrader {
    /**
     * Compute the score from sentence.
     *
     * @param sentence the sentence
     * @return computed score of sentence
     */
    fun computeScore(sentence: String): Int
}
