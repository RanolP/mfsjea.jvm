package io.github.ranolp.mfsjea.keyboard

import io.github.ranolp.mfsjea.Combinator

/**
 * The keyboard.
 *
 * @property name the name of keyboard
 * @property textSet the text set of keyboard
 */
sealed class Keyboard(val name: String, val textSet: TextSet) {
    /**
     * This enum contains supported character sets.
     */
    enum class TextSet {
        /**
         * alphabet character set
         */
        ALPHABET,

        /**
         * hangul dubeol character set
         */
        HANGUL_DUBEOL,

        /**
         * hangul sebeol character set
         */
        HANGUL_SEBEOL
    }
}

/**
 * The input keyboard.
 */
abstract class InputKeyboard(name: String, textSet: TextSet) : Keyboard(name, textSet) {
    /**
     * Get key code by the character.
     *
     * @param char the character
     * @return the key code of character, maybe it is null
     */
    abstract fun getKeycode(char: Char): Int?
}

/**
 * The output keyboard.
 *
 * @property combinator result sentence combinator
 */
abstract class OutputKeyboard(name: String, textSet: TextSet) : Keyboard(name, textSet) {
    open val combinator: Combinator = Combinator.NOP

    /**
     * Get character by the key code
     *
     * @param code the key code
     * @return characters obtained through code
     */
    abstract fun getCharacter(code: Int): Char
}
