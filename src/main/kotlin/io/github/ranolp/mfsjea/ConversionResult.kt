package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.keyboard.InputKeyboard
import io.github.ranolp.mfsjea.keyboard.OutputKeyboard

/**
 * The result of sentence conversion via two keyboard set
 *
 * @property source the source keyboard
 * @property target the target keyboard
 * @property sentence the converted sentence
 * @property convertedCount the count of converted characters
 * @property score the score of accuracy
 */
data class ConversionResult(
    val source: InputKeyboard,
    val target: OutputKeyboard,
    val sentence: String,
    val convertedCount: Int,
    val score: Int
)
