package io.github.ranolp.mfsjea.classifier

class CompoundClassifier(val classifiers: List<Classifier>) : Classifier {
    override fun classify(source: String): Iterator<Classifier.Part> = iterator {
        if (classifiers.isEmpty()) {
            yield(Classifier.Part.Normal(source))
            return@iterator
        }
        yieldAll(classifiers.fold(listOf<Classifier.Part>(Classifier.Part.Normal(source)).iterator()) { before, classifier ->
            before.asSequence().flatMap {
                when (it) {
                    is Classifier.Part.Escaped -> sequenceOf(it)
                    is Classifier.Part.Normal -> classifier.classify(it.origin).asSequence()
                }
            }.iterator()
        })
    }
}

fun Classifier.with(other: Classifier): CompoundClassifier =
    if (this is CompoundClassifier) {
        CompoundClassifier(this.classifiers + other)
    } else {
        CompoundClassifier(listOf(this, other))
    }