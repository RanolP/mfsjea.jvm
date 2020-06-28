package io.github.ranolp.mfsjea.classifier

interface Classifier {
    sealed class Part {
        data class Escaped(val origin: String) : Classifier.Part()
        data class Normal(val origin: String) : Classifier.Part()
    }

    fun classify(source: String): Iterator<Part>
}