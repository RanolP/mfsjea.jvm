package io.github.ranolp.mfsjea.keyboard

/**
 * Qwerty keyboard
 */
object QwertyKeyboard : InputKeyboard("Qwerty", TextSet.ALPHABET) {
    private const val LAYOUT =
        " `~1!2@3#4$5%6^7&8*9(0)-_=+\\|qQwWeErRtTyYuUiIoOpP[{]}aAsSdDfFgGhHjJkKlL;:\'\"zZxXcCvVbBnNmM,<.>/?"

    override fun getKeycode(char: Char): Int = LAYOUT.indexOf(char)
}

/**
 * Dvorak keyboard
 */
object DvorakKeyboard : InputKeyboard("Dvorak", TextSet.ALPHABET) {
    private const val LAYOUT =
        " `~1!2@3#4$5%6^7&8*9(0)[{]}\\|\'\",<.>pPyYfFgGcCrRlL/?=+aAoOeEuUiIdDhHtTnNsS-_;:qQjJkKxXbBmMwWvVzZ"

    override fun getKeycode(char: Char): Int = LAYOUT.indexOf(char)
}

/**
 * Colemak keyboard
 */
object ColemakKeyboard : InputKeyboard("Colemak", TextSet.ALPHABET) {
    private const val LAYOUT =
        " `~1!2@3#4$5%6^7&8*9(0)-_=+\\|qQwWfFpPgGjJlLuUyY;:[{]}aArRsStTdDhHnNeEiIoO\'\"zZxXcCvVbBkKmM,<.>/?"

    override fun getKeycode(char: Char): Int = LAYOUT.indexOf(char)
}
