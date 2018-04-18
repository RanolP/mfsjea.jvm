package io.github.ranolp.mfsjea

import io.github.ranolp.mfsjea.keyboard.InputKeyboard
import io.github.ranolp.mfsjea.keyboard.OutputKeyboard

data class ConversionResult(
    val source: InputKeyboard,
    val target: OutputKeyboard,
    val sentence: String,
    val convertedCount: Int,
    val score: Int
)
