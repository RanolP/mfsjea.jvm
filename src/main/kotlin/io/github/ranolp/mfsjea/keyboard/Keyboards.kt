package io.github.ranolp.mfsjea.keyboard

import io.github.ranolp.mfsjea.Combinator

sealed class Keyboard(val name: String, val textSet: TextSet) {
    enum class TextSet {
        ALPHABET,
        HANGUL_DUBEOL,
        HANGUL_SEBEOL
    }
}

abstract class InputKeyboard(name: String, textSet: TextSet) : Keyboard(name, textSet) {
    abstract fun getKeycode(char: Char): Int
}

abstract class OutputKeyboard(name: String, textSet: TextSet) : Keyboard(name, textSet) {
    open val combinator: Combinator = Combinator.NOP
    abstract fun getCharacter(code: Int): Char
}
