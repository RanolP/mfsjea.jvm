package io.github.ranolp.mfsjea.escaper

class BracketEscaper(val start: Char, val end: Char) : SentenceEscaper {
    override fun check(char: Char): SentenceEscaper.EscapeMode = when (char) {
        start -> SentenceEscaper.EscapeMode.START
        end -> SentenceEscaper.EscapeMode.END
        else -> SentenceEscaper.EscapeMode.NONE
    }
}
