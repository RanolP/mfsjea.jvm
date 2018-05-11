package io.github.ranolp.mfsjea.escaper

interface SentenceEscaper {
    enum class EscapeMode {
        START,
        END,
        NONE
    }

    fun check(char: Char): EscapeMode
}
