package io.github.ranolp.mfsjea.classifier

class BracketClassifier(val start: Char, val end: Char) : Classifier {
    override fun classify(source: String): Iterator<Classifier.Part> = iterator {
        var open = 0
        val buffer = StringBuffer()
        for (ch in source) {
            when (ch) {
                start -> {
                    if (open == 0 && buffer.isNotEmpty()) {
                        yield(Classifier.Part.Normal(buffer.toString()))
                        buffer.setLength(0)
                    }
                    open += 1
                }
                end -> {
                    if (open > 0) {
                        open -= 1
                        if (open == 0) {
                            yield(Classifier.Part.Escaped(buffer.toString()))
                            buffer.setLength(0)
                        }
                    } else {
                        buffer.append(end)
                    }
                }
                else -> {
                    buffer.append(ch)
                }
            }
        }
        if (buffer.isNotEmpty()) {
            yield(
                if (open > 0) {
                    Classifier.Part.Escaped(buffer.toString())
                } else {
                    Classifier.Part.Normal(buffer.toString())
                }
            )
        }
    }
}