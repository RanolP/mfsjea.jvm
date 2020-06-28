package io.github.ranolp.mfsjea.classifier

object NopClassifier : Classifier {
    override fun classify(source: String): Iterator<Classifier.Part> = iterator {
        yield(Classifier.Part.Normal(source))
    }
}