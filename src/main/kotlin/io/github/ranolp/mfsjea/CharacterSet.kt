package io.github.ranolp.mfsjea

internal class CharacterSet private constructor(val original: String, val changed: String, val state: State, val score: Int) {
    constructor(original: String) : this(original, original, State.ORIGINAL, 0)
    constructor(original: String, changed: String, score: Int) : this(original, changed, State.CHANGED, score)

    enum class State {
        CHANGED,
        ORIGINAL
    }
}
