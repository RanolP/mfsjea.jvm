package io.github.ranolp.mfsjea.classifier

object WordClassifier : Classifier {
    override fun classify(source: String): Iterator<Classifier.Part> = iterator {
        val buffer = StringBuffer()
        var isWhitespace = false
        for (ch in source) {
            when (ch) {
                ' ' -> {
                    if (!isWhitespace) {
                        if (buffer.isNotEmpty()) {
                            yield(Classifier.Part.Normal(buffer.toString()))
                            buffer.setLength(0)
                        }
                        isWhitespace = true
                    }
                }
                else -> {
                    if (isWhitespace) {
                        if (buffer.isNotEmpty()) {
                            yield(Classifier.Part.Normal(buffer.toString()))
                            buffer.setLength(0)
                        }
                        isWhitespace = false
                    }
                }
            }
            buffer.append(ch)
        }
        if (buffer.isNotEmpty()) {
            yield(Classifier.Part.Normal(buffer.toString()))
        }
    }
}