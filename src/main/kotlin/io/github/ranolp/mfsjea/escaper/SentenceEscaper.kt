package io.github.ranolp.mfsjea.escaper

/**
 * Escapes sentence by its rule
 */
interface SentenceEscaper {
    /**
     * The command of escape
     */
    enum class EscapeMode {
        /**
         * Start escape
         */
        START,
        /**
         * End escape
         */
        END,
        /**
         * Do nothing
         */
        NONE
    }

    /**
     * Test the char that be escapable
     */
    fun check(char: Char): EscapeMode
}
