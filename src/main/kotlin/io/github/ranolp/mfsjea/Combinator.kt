package io.github.ranolp.mfsjea

/**
 * This class combines(a.k.a. normalizes) characters.
 */
interface Combinator {
    companion object {
        /**
         * This combinator does not modify the characters.
         */
        val NOP = object : Combinator {
            override fun combine(sentence: String): String = sentence
        }
    }

    /**
     * Combines the sentence.
     *
     * @param sentence the sentence
     * @return combined result
     */
    fun combine(sentence: String): String
}
