package io.github.ranolp.mfsjea.util

import java.text.Normalizer
import java.util.regex.Pattern

fun String.replace(pattern: Pattern, converter: (List<String>) -> String): String {
    val matcher = pattern.matcher(this)

    val result = StringBuilder()
    var last = 0

    while (matcher.find()) {
        if (last < matcher.start()) {
            result.append(this.substring(last, matcher.start()))
        }
        last = matcher.end()

        result.append(converter((0 until matcher.groupCount()).map { i -> matcher.group(i) }))
    }

    if (last < this.length) {
        result.append(this.substring(last))
    }

    return result.toString()
}

fun CharSequence.normalize(form: Normalizer.Form): String = Normalizer.normalize(this, form)
