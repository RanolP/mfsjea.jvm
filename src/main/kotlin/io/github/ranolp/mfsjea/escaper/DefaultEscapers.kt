package io.github.ranolp.mfsjea.escaper

/**
 * Escape by bracket
 *
 * @property start the start char
 * @property end the end char
 */
class BracketEscaper(val start: Char, val end: Char) : SentenceEscaper {
    override fun check(char: Char): SentenceEscaper.EscapeMode = when (char) {
        start -> SentenceEscaper.EscapeMode.START
        end -> SentenceEscaper.EscapeMode.END
        else -> SentenceEscaper.EscapeMode.NONE
    }
}
